package model.pieces;

import model.position.Movement;
import model.position.Square;

import java.util.Map;

public class Queen extends Piece {
    public Queen(Colors color) {
        super(color, Pieces.QUEEN);
    }

    @Override
    public boolean isLegal(Movement movement, Map<Square, Piece> pieces) {
        return movement.isInColumn(pieces) || movement.isInRow(pieces) || movement.isInDiagonal(pieces);
    }
}
