package model.pieces;

import model.Movement;
import model.colors.Colors;
import org.apache.commons.lang3.NotImplementedException;

public class Queen extends Piece {
    public Queen(Colors color) {
        super(color, Pieces.QUEEN);
    }

    @Override
    public boolean isLegal(Movement movement) {
        throw new NotImplementedException();
    }
}
