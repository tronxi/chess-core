package model.pieces;

import model.Movement;
import org.apache.commons.lang3.NotImplementedException;

public class Bishop extends Piece {

    public Bishop(Colors color) {
        super(color, Pieces.BISHOP);
    }

    @Override
    public boolean isLegal(Movement movement) {
        throw new NotImplementedException();
    }
}
