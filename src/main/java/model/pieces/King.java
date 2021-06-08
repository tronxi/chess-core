package model.pieces;

import model.Movement;

public class King extends Piece {

    public King(Colors color) {
        super(color, Pieces.KING);
    }

    @Override
    public boolean isLegal(Movement movement) {
        return movement.isInColumnOneSquare() || movement.isInRowOneSquare() || movement.isInDiagonalOneSquare();
    }
}
