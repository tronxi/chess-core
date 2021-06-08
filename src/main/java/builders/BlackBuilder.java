package builders;

import model.pieces.*;
import model.position.Column;
import model.position.Row;
import model.position.Square;

import java.util.HashMap;
import java.util.Map;

public class BlackBuilder {

    public Map<Square, Piece> initialPosition() {
        Map<Square, Piece> pieces = new HashMap<>();
        Colors black = Colors.BLACK;
        pieces.put(new Square(Column.E, Row.EIGHT), new King(black));
        pieces.put(new Square(Column.D, Row.EIGHT), new Queen(black));

        pieces.put(new Square(Column.A, Row.EIGHT), new Rook(black));
        pieces.put(new Square(Column.H, Row.EIGHT), new Rook(black));

        pieces.put(new Square(Column.B, Row.EIGHT), new Knight(black));
        pieces.put(new Square(Column.G, Row.EIGHT), new Knight(black));

        pieces.put(new Square(Column.C, Row.EIGHT), new Bishop(black));
        pieces.put(new Square(Column.F, Row.EIGHT), new Bishop(black));

        pieces.put(new Square(Column.A, Row.SEVEN), new Pawn(black));
        pieces.put(new Square(Column.B, Row.SEVEN), new Pawn(black));
        pieces.put(new Square(Column.C, Row.SEVEN), new Pawn(black));
        pieces.put(new Square(Column.D, Row.SEVEN), new Pawn(black));
        pieces.put(new Square(Column.E, Row.SEVEN), new Pawn(black));
        pieces.put(new Square(Column.F, Row.SEVEN), new Pawn(black));
        pieces.put(new Square(Column.G, Row.SEVEN), new Pawn(black));
        pieces.put(new Square(Column.H, Row.SEVEN), new Pawn(black));

        return pieces;
    }
}
