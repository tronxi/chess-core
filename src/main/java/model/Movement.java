package model;

import model.position.Square;

public class Movement {
    private Square from;
    private Square to;

    public Movement(Square from, Square to) {
        this.from = from;
        this.to = to;
    }

    public Square getFrom() {
        return this.from;
    }

    public Square getTo() {
        return this.to;
    }

    public boolean isInRow() {
        return this.from.isInRow(to);
    }

    public boolean isInColumn() {
        return this.from.isInColumn(to);
    }
}
