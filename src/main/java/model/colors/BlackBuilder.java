package model.colors;

import model.pieces.*;
import model.position.Column;
import model.position.Row;
import model.position.Square;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlackBuilder {

    public Map<Square, Piece> build() {
        Map<Square, Piece> pieces = new HashMap<>();
        Colors black = Colors.BLACK;
        pieces.put(new Square(Row.EIGHT, Column.E), new King(black));
        pieces.put(new Square(Row.EIGHT, Column.D), new Queen(black));

        pieces.put(new Square(Row.EIGHT, Column.A), new Rook(black));
        pieces.put(new Square(Row.EIGHT, Column.H), new Rook(black));

        pieces.put(new Square(Row.EIGHT, Column.B), new Knight(black));
        pieces.put(new Square(Row.EIGHT, Column.G), new Knight(black));

        pieces.put(new Square(Row.EIGHT, Column.C), new Bishop(black));
        pieces.put(new Square(Row.EIGHT, Column.F), new Bishop(black));

        pieces.put(new Square(Row.SEVEN, Column.A), new Pawn(black));
        pieces.put(new Square(Row.SEVEN, Column.B), new Pawn(black));
        pieces.put(new Square(Row.SEVEN, Column.C), new Pawn(black));
        pieces.put(new Square(Row.SEVEN, Column.D), new Pawn(black));
        pieces.put(new Square(Row.SEVEN, Column.E), new Pawn(black));
        pieces.put(new Square(Row.SEVEN, Column.F), new Pawn(black));
        pieces.put(new Square(Row.SEVEN, Column.G), new Pawn(black));
        pieces.put(new Square(Row.SEVEN, Column.H), new Pawn(black));

        return pieces;
    }
}
