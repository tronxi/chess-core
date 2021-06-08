package model.pieces;

import model.Movement;
import model.colors.Colors;

public class Rook extends Piece {

    public Rook(Colors color) {
        super(color, Pieces.ROOK);
    }

    @Override
    public boolean isLegal(Movement movement) {
        return movement.isInColumn() || movement.isInRow();
    }
}
