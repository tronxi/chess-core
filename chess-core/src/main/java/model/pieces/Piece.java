package model.pieces;

import model.position.Movement;
import org.apache.commons.lang3.Validate;

public abstract class Piece {

    private final Colors color;
    private final Pieces pieces;

    public Piece(Colors color, Pieces pieces) {
        Validate.notNull(color);
        Validate.notNull(pieces);
        this.color = color;
        this.pieces = pieces;
    }

    public Pieces getPieces() {
        return pieces;
    }

    public boolean isColor(Colors color) {
        return this.color == color;
    }

    public abstract boolean isLegal(Movement movement);

    @Override
    public String toString() {
        return pieces.getInitial();
    }
}
