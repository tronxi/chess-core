package model.pieces;

import model.position.Movement;
import model.position.Square;

import java.util.Map;

public class Rook extends Piece {

    public Rook(Colors color) {
        super(color, Pieces.ROOK);
    }

    @Override
    public boolean isLegal(Movement movement, Map<Square, Piece> pieces) {
        return movement.isInColumn(pieces) || movement.isInRow(pieces);
    }
}
