package model.position;

import model.position.Square;
import org.apache.commons.lang3.Validate;

public class Movement {
    private final Square from;
    private final Square to;

    public Movement(Square from, Square to) {
        Validate.notNull(from);
        Validate.notNull(to);

        this.from = from;
        this.to = to;
    }

    public Square getFrom() {
        return this.from;
    }

    public Square getTo() {
        return this.to;
    }

    public boolean isHorseMovement() {
        return isDifferent() && this.from.isInHorse(this.to);
    }

    public boolean isInRow() {
        return isDifferent() && this.from.isInRow(this.to);
    }

    public boolean isInColumn() {
        return isDifferent() && this.from.isInColumn(this.to);
    }

    public boolean isInDiagonal() {
        return isDifferent() && this.from.isInDiagonal(this.to);
    }

    public boolean isInRowOneSquare() {
        return isDifferent() && this.from.isInRowOneSquare(this.to);
    }

    public boolean isInColumnOneSquare() {
        return isDifferent() && this.from.isInColumnOneSquare(this.to);
    }

    public boolean isInDiagonalOneSquare() {
        return isDifferent() && this.from.isInDiagonalOneSquare(this.to);
    }

    private boolean isDifferent() {
        return !from.equals(this.to);
    }

}
