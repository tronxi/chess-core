package model.pieces;

import model.position.Movement;
import model.position.Square;

import java.util.Map;

public class Bishop extends Piece {

    public Bishop(Colors color) {
        super(color, Pieces.BISHOP);
    }

    @Override
    public boolean isLegal(Movement movement, Map<Square, Piece> pieces) {
        return movement.isInDiagonal(pieces);
    }
}
