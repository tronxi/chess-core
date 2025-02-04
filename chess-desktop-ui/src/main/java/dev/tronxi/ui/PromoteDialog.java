package dev.tronxi.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import model.Board;
import model.pieces.Piece;
import model.position.Square;

import static dev.tronxi.ui.UIInitializer.backgroundColor;

public class PromoteDialog {
    public void createWorkspaceDialog(Square square, Board board) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Promote Pawn");

        ButtonType closeButtonType = new ButtonType("Close");
        alert.getButtonTypes().add(closeButtonType);

        VBox content = new VBox(15);
        content.setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));
        content.setPadding(new Insets(15));
        content.setAlignment(Pos.CENTER);

        HBox pieceContainer = new HBox(10);
        pieceContainer.setAlignment(Pos.CENTER);

        Piece piece = board.getPieces().get(square);
        for (Piece representation : PieceRepresentation.getPromotePieces(piece.getColor())) {
            ImageView imageView = PieceRepresentation.loadPngFromResources(representation);
            imageView.setOnMouseClicked(event -> {
                board.getPieces().put(square, representation);
                alert.close();
            });
            pieceContainer.getChildren().add(imageView);
        }

        content.getChildren().add(pieceContainer);
        alert.getDialogPane().setContent(content);
        alert.showAndWait();
    }
}
