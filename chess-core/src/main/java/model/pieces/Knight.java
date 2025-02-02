package model.pieces;

import model.position.Movement;
import model.position.Square;

import java.util.Map;

public class Knight extends Piece {

    public Knight(Colors color) {
        super(color, Pieces.KNIGHT);
    }

    @Override
    public boolean isLegal(Movement movement, Map<Square, Piece> pieces) {
        return movement.isHorseMovement();
    }
}
