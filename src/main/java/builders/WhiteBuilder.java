package builders;

import model.pieces.*;
import model.position.Column;
import model.position.Row;
import model.position.Square;

import java.util.HashMap;
import java.util.Map;

public class WhiteBuilder {

    public Map<Square, Piece> initialPosition() {
        Map<Square, Piece> pieces = new HashMap<>();
        Colors white = Colors.WHITE;
        pieces.put(new Square(Column.E, Row.ONE), new King(white));
        pieces.put(new Square(Column.D, Row.ONE), new Queen(white));

        pieces.put(new Square(Column.A, Row.ONE), new Rook(white));
        pieces.put(new Square(Column.H, Row.ONE), new Rook(white));

        pieces.put(new Square(Column.B, Row.ONE), new Knight(white));
        pieces.put(new Square(Column.G, Row.ONE), new Knight(white));

        pieces.put(new Square(Column.C, Row.ONE), new Bishop(white));
        pieces.put(new Square(Column.F, Row.ONE), new Bishop(white));

        pieces.put(new Square(Column.A, Row.TWO), new Pawn(white));
        pieces.put(new Square(Column.B, Row.TWO), new Pawn(white));
        pieces.put(new Square(Column.C, Row.TWO), new Pawn(white));
        pieces.put(new Square(Column.D, Row.TWO), new Pawn(white));
        pieces.put(new Square(Column.E, Row.TWO), new Pawn(white));
        pieces.put(new Square(Column.F, Row.TWO), new Pawn(white));
        pieces.put(new Square(Column.G, Row.TWO), new Pawn(white));
        pieces.put(new Square(Column.H, Row.TWO), new Pawn(white));

        return pieces;
    }
}
