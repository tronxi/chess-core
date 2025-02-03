package model;

import builders.BlackBuilder;
import model.pieces.Colors;
import builders.WhiteBuilder;
import exceptions.InvalidMovementException;
import model.pieces.Piece;
import model.position.Column;
import model.position.Movement;
import model.position.Row;
import model.position.Square;

import java.util.*;
import java.util.stream.Collectors;

public class Board {
    private final Map<Square, Piece> pieces;
    private final Map<Colors, List<Piece>> wonPieces;
    private final Map<Colors, Boolean> checks;
    private Colors turn;

    public Board() {
        turn = Colors.WHITE;

        checks = new HashMap<>();
        checks.put(Colors.WHITE, false);
        checks.put(Colors.BLACK, false);

        pieces = new HashMap<>();
        pieces.putAll(new WhiteBuilder().initialPosition());
        pieces.putAll(new BlackBuilder().initialPosition());

        wonPieces = new HashMap<>();
        wonPieces.put(Colors.WHITE, new ArrayList<>());
        wonPieces.put(Colors.BLACK, new ArrayList<>());
    }

    public void changePlayer() {
        this.turn = this.turn.takeOther();
    }

    public void move(Movement movement) throws InvalidMovementException {
        Piece origin = retrieveFromSquareWithColor(movement.getFrom(), this.turn);
        Optional<Piece> target = retrieveFromSquare(movement.getTo());
        if (!isLegalMovement(movement)) throw new InvalidMovementException();
        if (target.isPresent()) {
            if (target.get().isColor(this.turn.takeOther())) {
                wonPieces.get(turn).add(pieces.get(movement.getTo()));
                pieces.remove(movement.getFrom());
                pieces.put(movement.getTo(), origin);
            } else {
                throw new InvalidMovementException();
            }
        } else {
            pieces.remove(movement.getFrom());
            pieces.put(movement.getTo(), origin);
        }
        Colors other = this.turn.takeOther();
        checks.put(other, calculateIfIsInCheck(other));
    }

    private boolean isLegalMovement(Movement movement) {
        List<Square> legal = calculateLegalMoves(movement.getFrom());
        return legal.contains(movement.getTo());
    }

    public List<Square> calculateLegalMoves(Square origin) {
        List<Square> legalMoves = new ArrayList<>();
        if (!pieces.containsKey(origin)) return legalMoves;
        Piece piece = pieces.get(origin);
        for (Column column : Column.values()) {
            for (Row row : Row.values()) {
                Square target = new Square(column, row);
                Movement movement = new Movement(origin, target);
                if (piece.isLegal(movement, pieces)) {
                    if (pieces.containsKey(target)) {
                        if (!pieces.get(target).isColor(piece.getColor())) {
                            legalMoves.add(target);
                        }
                    } else {
                        legalMoves.add(target);
                    }
                }
            }
        }
        return legalMoves.stream().filter(square -> {
            Movement temporalMove = new Movement(origin, square);
            Map<Square, Piece> temporalPieces = calculateTemporalMove(temporalMove);
            return !calculateIfItsInCheckForTemporalMove(temporalPieces, turn);
        }).collect(Collectors.toList());
    }

    private Map<Square, Piece> calculateTemporalMove(Movement movement) {
        Map<Square, Piece> temporalMoves = new HashMap<>(pieces);
        Piece origin = temporalMoves.get(movement.getFrom());
        Optional<Piece> target = Optional.ofNullable(temporalMoves.get(movement.getTo()));
        if (target.isPresent()) {
            if (target.get().isColor(this.turn.takeOther())) {
                temporalMoves.remove(movement.getFrom());
                temporalMoves.put(movement.getTo(), origin);
            }
        } else {
            temporalMoves.remove(movement.getFrom());
            temporalMoves.put(movement.getTo(), origin);
        }
        return temporalMoves;
    }

    private boolean calculateIfItsInCheckForTemporalMove(Map<Square, Piece> temporalPieces, Colors color) {
        for (Square square : temporalPieces.keySet()) {
            Piece piece = temporalPieces.get(square);
            if (!piece.isColor(color)) {
                List<Square> legalMoves = calculateLegalMovesForTemporalMove(temporalPieces, square);
                for (Square legalMove : legalMoves) {
                    if (temporalPieces.containsKey(legalMove)) {
                        Piece pieceInLegalMove = temporalPieces.get(legalMove);
                        if (pieceInLegalMove.isColor(color) && pieceInLegalMove.isKing()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private List<Square> calculateLegalMovesForTemporalMove(Map<Square, Piece> temporalPieces, Square origin) {
        List<Square> legalMoves = new ArrayList<>();
        if (!temporalPieces.containsKey(origin)) return legalMoves;
        Piece piece = temporalPieces.get(origin);
        for (Column column : Column.values()) {
            for (Row row : Row.values()) {
                Square target = new Square(column, row);
                Movement movement = new Movement(origin, target);
                if (piece.isLegal(movement, temporalPieces)) {
                    if (temporalPieces.containsKey(target)) {
                        if (!temporalPieces.get(target).isColor(piece.getColor())) {
                            legalMoves.add(target);
                        }
                    } else {
                        legalMoves.add(target);
                    }
                }
            }
        }
        return legalMoves;
    }


    private boolean calculateIfIsInCheck(Colors color) {
        for (Square square : pieces.keySet()) {
            Piece piece = pieces.get(square);
            if (!piece.isColor(color)) {
                List<Square> legalMoves = calculateLegalMoves(square);
                for (Square legalMove : legalMoves) {
                    if (pieces.containsKey(legalMove)) {
                        Piece pieceInLegalMove = pieces.get(legalMove);
                        if (pieceInLegalMove.isColor(color) && pieceInLegalMove.isKing()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private Piece retrieveFromSquareWithColor(Square square, Colors color) throws InvalidMovementException {
        Piece piece = pieces.get(square);
        if (piece == null || !piece.isColor(color)) throw new InvalidMovementException();
        return piece;
    }

    private Optional<Piece> retrieveFromSquare(Square square) {
        return Optional.ofNullable(pieces.get(square));
    }

    public Map<Square, Piece> getPieces() {
        return pieces;
    }

    public Map<Colors, List<Piece>> getWonPieces() {
        return wonPieces;
    }

    public Colors getTurn() {
        return turn;
    }

    public Boolean isInCheck(Colors color) {
        return checks.get(color);
    }

}
