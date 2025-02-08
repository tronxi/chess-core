package model;

import builders.BlackBuilder;
import model.pieces.Colors;
import builders.WhiteBuilder;
import exceptions.InvalidMovementException;
import model.pieces.EnPassantPawn;
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
    private final Map<Colors, Boolean> legalMoves;
    private final Map<Colors, Square> promotes;
    private final Map<Colors, Boolean> hasKingMoved;
    private final Map<Colors, Boolean> hasARookMoved;
    private final Map<Colors, Boolean> hasHRookMoved;

    private Colors turn;

    public Board() {
        turn = Colors.WHITE;

        checks = new HashMap<>();
        checks.put(Colors.WHITE, false);
        checks.put(Colors.BLACK, false);

        legalMoves = new HashMap<>();
        legalMoves.put(Colors.WHITE, false);
        legalMoves.put(Colors.BLACK, false);

        hasKingMoved = new HashMap<>();
        hasKingMoved.put(Colors.WHITE, false);
        hasKingMoved.put(Colors.BLACK, false);

        hasARookMoved = new HashMap<>();
        hasARookMoved.put(Colors.WHITE, false);
        hasARookMoved.put(Colors.BLACK, false);

        hasHRookMoved = new HashMap<>();
        hasHRookMoved.put(Colors.WHITE, false);
        hasHRookMoved.put(Colors.BLACK, false);

        promotes = new HashMap<>();

        pieces = new HashMap<>();
        pieces.putAll(new WhiteBuilder().initialPosition());
        pieces.putAll(new BlackBuilder().initialPosition());

        wonPieces = new HashMap<>();
        wonPieces.put(Colors.WHITE, new ArrayList<>());
        wonPieces.put(Colors.BLACK, new ArrayList<>());
    }

    private void changePlayer() {
        this.turn = this.turn.takeOther();
    }

    public void move(Movement movement) throws InvalidMovementException {
        promotes.clear();
        Piece origin = retrieveFromSquareWithColor(movement.getFrom(), this.turn);
        Optional<Piece> target = retrieveFromSquare(movement.getTo());
        if (!isLegalMovement(movement)) throw new InvalidMovementException();
        if (target.isPresent()) {
            if (target.get().isColor(this.turn.takeOther())) {
                Piece targetPiece = target.get();
                if (targetPiece.isEnPassantPawn()) {
                    if (origin.isPawn()) {
                        if (targetPiece.isColor(Colors.WHITE)) {
                            Square originPawnSquare = new Square(movement.getTo().getColumn(), Row.FOUR);
                            wonPieces.get(turn).add(pieces.get(originPawnSquare));
                            pieces.remove(originPawnSquare);
                        } else if (targetPiece.isColor(Colors.BLACK)) {
                            Square originPawnSquare = new Square(movement.getTo().getColumn(), Row.FIVE);
                            wonPieces.get(turn).add(pieces.get(originPawnSquare));
                            pieces.remove(originPawnSquare);
                        }
                    }
                } else {
                    wonPieces.get(turn).add(pieces.get(movement.getTo()));
                }
                performMove(movement, origin);
            } else {
                throw new InvalidMovementException();
            }
        } else {
            performMove(movement, origin);
        }
        Colors other = this.turn.takeOther();
        checks.put(other, calculateIfIsInCheck(other));
        changePlayer();
        legalMoves.put(turn, calculateIfHasLegalMoves(turn));
    }

    private void performMove(Movement movement, Piece origin) {
        Piece fromPiece = pieces.get(movement.getFrom());
        pieces.remove(movement.getFrom());
        pieces.put(movement.getTo(), origin);
        if (canPromote(origin, movement)) {
            promotes.put(turn, movement.getTo());
        }
        if (pieces.get(movement.getTo()).isKing()) {
            hasKingMoved.put(turn, true);
        }
        if (fromPiece.isRook()) {
            if (movement.getFrom().getColumn().equals(Column.A)) {
                hasARookMoved.put(turn, true);
            } else if (movement.getFrom().getColumn().equals(Column.H)) {
                hasHRookMoved.put(turn, true);
            }
        }
        performCastleMoves(movement);
        allowEnPassant(movement);
        removeEnPassant(turn.takeOther());
    }

    private void allowEnPassant(Movement movement) {
        if (movement.isWhiteDoublePawn()) {
            Piece pawn = pieces.get(movement.getTo());
            pieces.put(new Square(movement.getFrom().getColumn(), Row.THREE), new EnPassantPawn(pawn.getColor()));
        } else if (movement.isBlackDoublePawn()) {
            Piece pawn = pieces.get(movement.getTo());
            pieces.put(new Square(movement.getFrom().getColumn(), Row.SIX), new EnPassantPawn(pawn.getColor()));
        }
    }

    private void removeEnPassant(Colors color) {
        List<Square> toRemove = new ArrayList<>();
        for (Square square : pieces.keySet()) {
            Piece piece = pieces.get(square);
            if (piece.isEnPassantPawn() && piece.getColor().equals(color)) {
                toRemove.add(square);
            }
        }
        toRemove.forEach(pieces::remove);
    }

    private void performCastleMoves(Movement movement) {
        if (movement.isWhiteKingSideCastle()) {
            Square rookSquare = new Square(Column.H, Row.ONE);
            Piece rookPiece = pieces.get(rookSquare);
            pieces.remove(rookSquare);
            pieces.put(new Square(Column.F, Row.ONE), rookPiece);
        } else if (movement.isWhiteQueenSideCastle()) {
            Square rookSquare = new Square(Column.A, Row.ONE);
            Piece rookPiece = pieces.get(rookSquare);
            pieces.remove(rookSquare);
            pieces.put(new Square(Column.D, Row.ONE), rookPiece);
        } else if (movement.isBlackKingSideCastle()) {
            Square rookSquare = new Square(Column.H, Row.EIGHT);
            Piece rookPiece = pieces.get(rookSquare);
            pieces.remove(rookSquare);
            pieces.put(new Square(Column.F, Row.EIGHT), rookPiece);
        } else if (movement.isBlackQueenSideCastle()) {
            Square rookSquare = new Square(Column.A, Row.EIGHT);
            Piece rookPiece = pieces.get(rookSquare);
            pieces.remove(rookSquare);
            pieces.put(new Square(Column.D, Row.EIGHT), rookPiece);
        }
    }

    private boolean canPromote(Piece origin, Movement movement) {
        if (origin.isPawn()) {
            if (origin.isColor(Colors.BLACK)) {
                return movement.getTo().getRow().equals(Row.ONE);
            } else {
                return movement.getTo().getRow().equals(Row.EIGHT);
            }
        }
        return false;
    }

    public void promote(Square square, Piece promotedPiece) {
        pieces.remove(square);
        pieces.put(square, promotedPiece);
        changePlayer();
        Colors other = this.turn.takeOther();
        checks.put(other, calculateIfIsInCheck(other));
        changePlayer();
        legalMoves.put(turn, calculateIfHasLegalMoves(turn));
    }

    private boolean isLegalMovement(Movement movement) {
        List<Square> legal = calculateLegalMoves(movement.getFrom());
        return legal.contains(movement.getTo());
    }

    private boolean calculateIfHasLegalMoves(Colors color) {
        for (Square square : pieces.keySet()) {
            if (pieces.get(square).isColor(color)) {
                List<Square> legalMoves = calculateLegalMoves(square);
                if (!legalMoves.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
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
        if (canWhiteCastleKingSide(origin)) {
            legalMoves.add(new Square(Column.G, Row.ONE));
        }
        if (canWhiteCastleQueenSide(origin)) {
            legalMoves.add(new Square(Column.C, Row.ONE));
        }
        if (canBlackCastleKingSide(origin)) {
            legalMoves.add(new Square(Column.G, Row.EIGHT));
        }
        if (canBlackCastleQueenSide(origin)) {
            legalMoves.add(new Square(Column.C, Row.EIGHT));
        }
        return legalMoves.stream().filter(square -> {
            Movement temporalMove = new Movement(origin, square);
            Map<Square, Piece> temporalPieces = calculateTemporalMove(temporalMove);
            return !calculateIfItsInCheckForTemporalMove(temporalPieces, turn);
        }).collect(Collectors.toList());
    }

    private boolean canWhiteCastleKingSide(Square square) {
        if (!canWhiteCastle(square, hasHRookMoved)) return false;
        if (pieces.containsKey(new Square(Column.F, Row.ONE))) return false;
        if (pieces.containsKey(new Square(Column.G, Row.ONE))) return false;
        Movement temporalMove = new Movement(square, new Square(Column.F, Row.ONE));
        Map<Square, Piece> temporalPieces = calculateTemporalMove(temporalMove);
        return !calculateIfItsInCheckForTemporalMove(temporalPieces, turn);
    }

    private boolean canWhiteCastleQueenSide(Square square) {
        if (!canWhiteCastle(square, hasARookMoved)) return false;
        if (pieces.containsKey(new Square(Column.D, Row.ONE))) return false;
        if (pieces.containsKey(new Square(Column.C, Row.ONE))) return false;
        Movement temporalMove = new Movement(square, new Square(Column.D, Row.ONE));
        Map<Square, Piece> temporalPieces = calculateTemporalMove(temporalMove);
        if (calculateIfItsInCheckForTemporalMove(temporalPieces, turn)) return false;

        temporalMove = new Movement(square, new Square(Column.C, Row.ONE));
        temporalPieces = calculateTemporalMove(temporalMove);
        return !calculateIfItsInCheckForTemporalMove(temporalPieces, turn);
    }

    private boolean canWhiteCastle(Square square, Map<Colors, Boolean> hasRookMoved) {
        if (!pieces.containsKey(square)) return false;
        Piece piece = pieces.get(square);
        if (!piece.isKing()) return false;
        if (hasKingMoved.get(Colors.WHITE)) return false;
        if (hasRookMoved.get(Colors.WHITE)) return false;
        return !calculateIfItsInCheckForTemporalMove(pieces, Colors.WHITE);
    }

    private boolean canBlackCastleKingSide(Square square) {
        if (!canBlackCastle(square, hasHRookMoved)) return false;
        if (pieces.containsKey(new Square(Column.F, Row.EIGHT))) return false;
        if (pieces.containsKey(new Square(Column.G, Row.EIGHT))) return false;
        Movement temporalMove = new Movement(square, new Square(Column.F, Row.EIGHT));
        Map<Square, Piece> temporalPieces = calculateTemporalMove(temporalMove);
        return !calculateIfItsInCheckForTemporalMove(temporalPieces, turn);
    }

    private boolean canBlackCastleQueenSide(Square square) {
        if (!canBlackCastle(square, hasARookMoved)) return false;
        if (pieces.containsKey(new Square(Column.D, Row.EIGHT))) return false;
        if (pieces.containsKey(new Square(Column.C, Row.EIGHT))) return false;
        Movement temporalMove = new Movement(square, new Square(Column.D, Row.EIGHT));
        Map<Square, Piece> temporalPieces = calculateTemporalMove(temporalMove);
        if (calculateIfItsInCheckForTemporalMove(temporalPieces, turn)) return false;

        temporalMove = new Movement(square, new Square(Column.C, Row.EIGHT));
        temporalPieces = calculateTemporalMove(temporalMove);
        return !calculateIfItsInCheckForTemporalMove(temporalPieces, turn);
    }

    private boolean canBlackCastle(Square square, Map<Colors, Boolean> hasRookMoved) {
        if (!pieces.containsKey(square)) return false;
        Piece piece = pieces.get(square);
        if (!piece.isKing()) return false;
        if (hasKingMoved.get(Colors.BLACK)) return false;
        if (hasRookMoved.get(Colors.BLACK)) return false;
        return !calculateIfItsInCheckForTemporalMove(pieces, Colors.BLACK);
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

    public Boolean hasLegalMoves(Colors color) {
        return legalMoves.get(color);
    }

    public Optional<Square> canPromote(Colors color) {
        if (promotes.containsKey(color)) {
            return Optional.of(promotes.get(color));
        }
        return Optional.empty();
    }

}
