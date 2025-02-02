package dev.tronxi.ui;

import exceptions.InvalidMovementException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import model.Board;
import model.pieces.Piece;
import model.position.Column;
import model.position.Movement;
import model.position.Row;
import model.position.Square;

import java.util.function.Consumer;

public class BoardComponent {

    private final Color white = Color.WHITE;
    private final Color black = Color.GREEN;
    private final Color selected = Color.YELLOW;
    private final int squareSize = 80;
    private final int labelFontSize = 20;
    private int totalPieces = 1;
    private Board board;
    private Square from;
    private Consumer<VBox> onMovement;
    private Runnable onError;

    public VBox create(Runnable onError, Consumer<VBox> onMovement, Board board) {
        this.onMovement = onMovement;
        this.onError = onError;
        this.board = board;
        return drawBoard();
    }

    private VBox drawBoard() {
        VBox root = new VBox();

        for (int i = 8; i >= 1; i--) {
            HBox hbox = new HBox();
            VBox labelContainer = new VBox();
            labelContainer.setPadding(new Insets(10));
            labelContainer.setAlignment(Pos.CENTER);
            Label numberLabel = new Label(i + "");
            numberLabel.setFont(Font.font(labelFontSize));
            labelContainer.getChildren().add(numberLabel);
            hbox.getChildren().add(labelContainer);
            for (char ch = 'a'; ch <= 'h'; ch++) {
                Row row = Row.fromInt(i);
                Column column = Column.fromString(String.valueOf(ch));
                Square square = new Square(column, row);
                if (board.getPieces().containsKey(square)) {
                    Piece piece = board.getPieces().get(square);
                    hbox.getChildren().add(new SquareComponent(this::onClick, squareSize, getColor(square, totalPieces), column, row, piece));
                } else {
                    hbox.getChildren().add(new SquareComponent(this::onClick, squareSize, getColor(square, totalPieces), column, row));
                }
                totalPieces++;
            }
            totalPieces++;
            root.getChildren().add(hbox);
        }
        root.getChildren().add(notation());
        return root;
    }

    private void onClick(Square square) {
        if (from == null) {
            this.from = square;
            this.onMovement.accept(drawBoard());
        } else {
            try {
                board.move(new Movement(from, square));
                this.from = null;
                board.changePlayer();
                this.onMovement.accept(drawBoard());
            } catch (InvalidMovementException e) {
                this.from = null;
                this.onMovement.accept(drawBoard());
                onError.run();
            }
        }
    }

    private HBox notation() {
        HBox hbox = new HBox();
        hbox.getChildren().add(new Label(" "));
        for (char ch = 'A'; ch <= 'H'; ch++) {
            Label label = new Label(ch + "");
            label.setFont(Font.font(labelFontSize));
            StackPane pane = new StackPane();
            Rectangle rect = new Rectangle();
            rect.setWidth(squareSize);
            pane.getChildren().addAll(rect, label);
            hbox.getChildren().add(pane);
        }
        return hbox;
    }

    private Color getColor(Square square, int piece) {
        if (square.equals(from)) {
            return selected;
        }
        if (piece % 2 == 0) {
            return black;
        }
        return white;
    }
}
