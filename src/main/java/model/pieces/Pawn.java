package model.pieces;

import model.Movement;
import model.colors.Colors;
import org.apache.commons.lang3.NotImplementedException;

public class Pawn extends Piece {

    public Pawn(Colors color) {
        super(color, Pieces.PAWN);
    }

    @Override
    public boolean isLegal(Movement movement) {
        throw new NotImplementedException();
    }
}
