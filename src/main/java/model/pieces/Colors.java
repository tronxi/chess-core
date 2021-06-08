package model.pieces;

public enum Colors {
    WHITE(0),
    BLACK(1);

    private final int turn;

    Colors(int turn) {
        this.turn = turn;
    }

    public int turn() {
        return this.turn;
    }

    public Colors takeOther() {
        if(this == WHITE) return BLACK;
        else return WHITE;
    }
}
