package model.pieces;

import model.position.Movement;
import model.position.Square;
import org.apache.commons.lang3.Validate;

import java.util.Map;

public abstract class Piece {

    private final Colors color;
    private final Pieces pieces;

    public Piece(Colors color, Pieces pieces) {
        Validate.notNull(color);
        Validate.notNull(pieces);
        this.color = color;
        this.pieces = pieces;
    }

    public Colors getColor() {
        return color;
    }

    public Pieces getPieces() {
        return pieces;
    }

    public boolean isColor(Colors color) {
        return this.color == color;
    }

    public abstract boolean isLegal(Movement movement, Map<Square, Piece> pieces);

    @Override
    public String toString() {
        return pieces.getInitial();
    }
}
