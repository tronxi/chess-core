package model;

import builders.BlackBuilder;
import model.pieces.Colors;
import builders.WhiteBuilder;
import exceptions.InvalidMovementException;
import model.pieces.Piece;
import model.position.Movement;
import model.position.Square;

import java.util.*;

public class Board {
    private final Map<Square, Piece> pieces;
    private final Map<Colors, List<Piece>> wonPieces;
    private Colors turn;

    public Board() {
        turn = Colors.WHITE;

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
        if(!origin.isLegal(movement, pieces)) throw new InvalidMovementException();
        if(target.isPresent()) {
            if(target.get().isColor(this.turn.takeOther())) {
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
    }

    private Piece retrieveFromSquareWithColor(Square square, Colors color) throws InvalidMovementException {
        Piece piece = pieces.get(square);
        if(piece == null || !piece.isColor(color)) throw new InvalidMovementException();
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

}
