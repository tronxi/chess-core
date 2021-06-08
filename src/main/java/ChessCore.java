import model.Board;
import model.Movement;
import model.exceptions.InvalidMovementException;
import model.pieces.Piece;
import model.position.Column;
import model.position.Row;
import model.position.Square;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ChessCore {

    public ChessCore() {
        Board board = new Board();
        List<Movement> movementList = Arrays.asList(
                new Movement(new Square(Column.E, Row.TWO), new Square(Column.E, Row.FOUR)),
                new Movement(new Square(Column.E, Row.SEVEN), new Square(Column.E, Row.FIVE))
        );
        try {
            print(board.getPieces());
            for(Movement movement: movementList) {
                board.move(movement);
                board.changePlayer();
                print(board.getPieces());
            }
        } catch (InvalidMovementException e) {
            System.out.println("invalid movement");
        }
    }

    private void print(Map<Square, Piece> pieces) {
        System.out.println("TABLERO");
        for(Row row : Row.values()) {
            for(Column column: Column.values()) {
                Piece piece = pieces.get(new Square(column, row));
                if(piece != null) {
                    System.out.print("|" + piece + "|");
                } else {
                    System.out.print("| |");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static void main(String ...args ) {
        new ChessCore();
    }
}
