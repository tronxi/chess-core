package model;

import model.colors.BlackBuilder;
import model.colors.Colors;
import model.colors.WhiteBuilder;
import model.exceptions.InvalidMovementException;
import model.pieces.Piece;
import model.position.Square;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Board {
    private final Map<Square, Piece> pieces;
    private Colors turn;

    public Board() {
        turn = Colors.WHITE;
        pieces = new HashMap<>();
        pieces.putAll(new WhiteBuilder().build());
        pieces.putAll(new BlackBuilder().build());
    }

    public void changePlayer() {
        this.turn = this.turn.takeOther();
    }

    public void move(Movement movement) throws InvalidMovementException {
        Piece origin = retrieveFromSquareWithColor(movement.getFrom(), this.turn);
        Optional<Piece> target = retrieveFromSquare(movement.getTo());
        //if(!origin.isLegal(movement)) throw new InvalidMovementException();
        if(target.isPresent()) {
            if(target.get().isColor(this.turn.takeOther())) {
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
}
