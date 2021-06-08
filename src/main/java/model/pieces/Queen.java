package model.pieces;

import model.Movement;

public class Queen extends Piece {
    public Queen(Colors color) {
        super(color, Pieces.QUEEN);
    }

    @Override
    public boolean isLegal(Movement movement) {
        return movement.isInColumn() || movement.isInRow() || movement.isInDiagonal();
    }
}
