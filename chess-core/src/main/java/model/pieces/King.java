package model.pieces;

import model.position.Movement;
import model.position.Square;

import java.util.Map;

public class King extends Piece {

    public King(Colors color) {
        super(color, Pieces.KING);
    }

    @Override
    public boolean isLegal(Movement movement, Map<Square, Piece> pieces) {
        return movement.isInColumnOneSquare() || movement.isInRowOneSquare() || movement.isInDiagonalOneSquare();
    }
}
