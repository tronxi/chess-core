package model.pieces;

public enum Pieces {

    KING("K"),
    QUEEN("Q"),
    KNIGHT("N"),
    PAWN("P"),
    BISHOP("B"),
    ROOK("R");

    private String initial;

    Pieces(String initial) {
        this.initial = initial;
    }

    public String getInitial() {
        return this.initial;
    }
}
