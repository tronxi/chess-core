package model.pieces;

import model.Movement;
import model.colors.Colors;
import org.apache.commons.lang3.NotImplementedException;

public class Knight extends Piece {

    public Knight(Colors color) {
        super(color, Pieces.KNIGHT);
    }

    @Override
    public boolean isLegal(Movement movement) {
        throw new NotImplementedException();
    }
}
