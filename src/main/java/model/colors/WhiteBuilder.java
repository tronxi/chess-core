package model.colors;

import model.pieces.*;
import model.position.Column;
import model.position.Row;
import model.position.Square;

import java.util.HashMap;
import java.util.Map;

public class WhiteBuilder {

    public Map<Square, Piece> build() {
        Map<Square, Piece> pieces = new HashMap<>();
        Colors white = Colors.WHITE;
        pieces.put(new Square(Row.ONE, Column.E), new King(white));
        pieces.put(new Square(Row.ONE, Column.D), new Queen(white));

        pieces.put(new Square(Row.ONE, Column.A), new Rook(white));
        pieces.put(new Square(Row.ONE, Column.H), new Rook(white));

        pieces.put(new Square(Row.ONE, Column.B), new Knight(white));
        pieces.put(new Square(Row.ONE, Column.G), new Knight(white));

        pieces.put(new Square(Row.ONE, Column.C), new Bishop(white));
        pieces.put(new Square(Row.ONE, Column.F), new Bishop(white));

        pieces.put(new Square(Row.TWO, Column.A), new Pawn(white));
        pieces.put(new Square(Row.TWO, Column.B), new Pawn(white));
        pieces.put(new Square(Row.TWO, Column.C), new Pawn(white));
        pieces.put(new Square(Row.TWO, Column.D), new Pawn(white));
        pieces.put(new Square(Row.TWO, Column.E), new Pawn(white));
        pieces.put(new Square(Row.TWO, Column.F), new Pawn(white));
        pieces.put(new Square(Row.TWO, Column.G), new Pawn(white));
        pieces.put(new Square(Row.TWO, Column.H), new Pawn(white));

        return pieces;
    }
}
