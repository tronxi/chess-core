package model.pieces;

public enum Pieces {

    KING("K"),
    QUEEN("Q"),
    KNIGHT("N"),
    PAWN("P"),
    EN_PASSANT_PAWN("EPP"),
    BISHOP("B"),
    ROOK("R");

    private final String initial;

    Pieces(String initial) {
        this.initial = initial;
    }

    public String getInitial() {
        return this.initial;
    }
}
