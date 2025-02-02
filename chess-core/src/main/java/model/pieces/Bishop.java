package model.pieces;

import model.position.Movement;

public class Bishop extends Piece {

    public Bishop(Colors color) {
        super(color, Pieces.BISHOP);
    }

    @Override
    public boolean isLegal(Movement movement) {
        return movement.isInDiagonal();
    }
}
