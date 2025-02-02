package dev.tronxi.ui;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.pieces.Piece;
import model.position.Column;
import model.position.Row;
import model.position.Square;

import java.util.function.Consumer;

public class SquareComponent extends StackPane {

    public SquareComponent(Consumer<Square> onClick, int size, Color color, Column column, Row row) {
        this(onClick, size, color, column, row, null);
    }

    public SquareComponent(Consumer<Square> onClick, int size, Color color, Column column, Row row, Piece piece) {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(size);
        rectangle.setHeight(size);
        rectangle.setFill(color);
        getChildren().add(rectangle);

        setOnMouseClicked(event -> onClick.accept(new Square(column, row)));

        if (piece != null) {
            ImageView pieceImage = PieceRepresentation.loadPngFromResources(piece);
            getChildren().add(pieceImage);
        }
    }
}
