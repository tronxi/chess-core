package model.pieces;

import model.position.Movement;
import model.position.Square;

import java.util.Map;

public class EnPassantPawn extends Piece {
    public EnPassantPawn(Colors color) {
        super(color, Pieces.EN_PASSANT_PAWN);
    }

    @Override
    public boolean isLegal(Movement movement, Map<Square, Piece> pieces) {
        return false;
    }
}
