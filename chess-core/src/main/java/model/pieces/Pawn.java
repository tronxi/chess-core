package model.pieces;

import model.position.Movement;

public class Pawn extends Piece {

    public Pawn(Colors color) {
        super(color, Pieces.PAWN);
    }

    @Override
    public boolean isLegal(Movement movement) {
        if(this.isColor(Colors.WHITE)) {
            return movement.isWhitePawn();
        } else {
            return movement.isBlackPawn();
        }
    }
}
