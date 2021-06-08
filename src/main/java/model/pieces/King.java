package model.pieces;

import model.Movement;
import org.apache.commons.lang3.NotImplementedException;

public class King extends Piece {

    public King(Colors color) {
        super(color, Pieces.KING);
    }

    @Override
    public boolean isLegal(Movement movement) {
        throw new NotImplementedException();
    }
}
