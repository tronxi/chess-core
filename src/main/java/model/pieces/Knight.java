package model.pieces;

import model.position.Movement;

public class Knight extends Piece {

    public Knight(Colors color) {
        super(color, Pieces.KNIGHT);
    }

    @Override
    public boolean isLegal(Movement movement) {
        return movement.isHorseMovement();
    }
}
