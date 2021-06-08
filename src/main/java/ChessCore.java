import model.Board;
import model.Movement;
import model.exceptions.InvalidMovementException;
import model.pieces.Colors;
import model.pieces.Piece;
import model.position.Column;
import model.position.Row;
import model.position.Square;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ChessCore {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public ChessCore() {
        Board board = new Board();
        List<Movement> movementList = Arrays.asList(
                new Movement(new Square(Column.E, Row.TWO), new Square(Column.E, Row.FOUR)),
                new Movement(new Square(Column.E, Row.SEVEN), new Square(Column.E, Row.FIVE)),
                new Movement(new Square(Column.B, Row.ONE), new Square(Column.C, Row.THREE)),
                new Movement(new Square(Column.G, Row.EIGHT), new Square(Column.F, Row.SIX)),
                new Movement(new Square(Column.A, Row.TWO), new Square(Column.A, Row.THREE)),
                new Movement(new Square(Column.F, Row.SIX), new Square(Column.E, Row.FOUR)),
                new Movement(new Square(Column.C, Row.THREE), new Square(Column.E, Row.FOUR))
                );
        try {
            print(board);
            for(Movement movement: movementList) {
                board.move(movement);
                board.changePlayer();
                print(board);
            }
        } catch (InvalidMovementException e) {
            System.out.println("invalid movement");
        }
    }

    private void print(Board board) {
        System.out.println("TABLERO");
        List<Row> reversedRow = Arrays.stream(Row.values()).collect(Collectors.toList());
        Collections.reverse(reversedRow);
        for(Row row : reversedRow) {
            for(Column column: Column.values()) {
                Piece piece = board.getPieces().get(new Square(column, row));
                if(piece != null) {
                    printBoard("|");
                    if(piece.isColor(Colors.WHITE))
                        printWhite(piece);
                    else printBlack(piece);
                    printBoard("|");
                } else {
                    printBoard("| |");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("Piezas ganadas por blanco: " + board.getWonPieces().get(Colors.WHITE));
        System.out.println("Piezas ganadas por negro: " + board.getWonPieces().get(Colors.BLACK));

    }

    private void printBoard(String string) {
        System.out.print(ANSI_GREEN + string + ANSI_RESET);
    }

    private void printWhite(Piece piece) {
        System.out.print(piece);
    }

    private void printBlack(Piece piece) {
        System.out.print(ANSI_BLACK + piece + ANSI_RESET);
    }

    public static void main(String[] args) {
        new ChessCore();
    }
}
