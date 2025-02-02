package model.pieces;

import model.position.Movement;
import model.position.Square;

import java.util.Map;

public class Pawn extends Piece {

    public Pawn(Colors color) {
        super(color, Pieces.PAWN);
    }

    @Override
    public boolean isLegal(Movement movement, Map<Square, Piece> pieces) {
        if (this.isColor(Colors.WHITE)) {
            return movement.isWhitePawn(pieces);
        } else {
            return movement.isBlackPawn(pieces);
        }
    }
}
