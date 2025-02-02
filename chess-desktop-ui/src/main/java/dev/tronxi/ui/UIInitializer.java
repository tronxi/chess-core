package dev.tronxi.ui;

import atlantafx.base.theme.PrimerDark;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Board;

public class UIInitializer extends Application {

    private VBox boardRepresentation;
    private VBox wonPiecesRepresentation;
    private Label turn;
    private Stage stage;
    private BoardComponent boardComponent;

    private final Color backgroundColor = new Color(0.188f, 0.180f, 0.169f, 1f);

    @Override
    public void start(Stage stage) {
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
        this.stage = stage;
        stage.setTitle("Chess");
        boardComponent = new BoardComponent();
        boardRepresentation = boardComponent.create(this::onError, this::onMove, new Board());
        wonPiecesRepresentation = boardComponent.drawWonPieces();

        HBox controls = new HBox();
        turn = new Label("Turn: " + boardComponent.getTurn());
        turn.setFont(Font.font(14));
        Button resetButton = new Button("Reset");
        resetButton.setOnMouseClicked(mouseEvent -> {
            boardRepresentation.getChildren().setAll(boardComponent.reset(new Board()));
            wonPiecesRepresentation.getChildren().setAll(boardComponent.drawWonPieces().getChildren());
            turn.setText("Turn: " + boardComponent.getTurn());
        });

        controls.getChildren().addAll(resetButton, turn);
        controls.setAlignment(Pos.CENTER_LEFT);
        controls.setSpacing(15);
        controls.setPadding(new Insets(0, 0, 0, 20));

        VBox hBox = new VBox(boardRepresentation, controls, wonPiecesRepresentation);
        hBox.setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));
        stage.setScene(new Scene(hBox, 670, 850));
        stage.show();
    }

    private void onMove(VBox updatedBoard) {
        boardRepresentation.getChildren().setAll(updatedBoard.getChildren());
        wonPiecesRepresentation.getChildren().setAll(boardComponent.drawWonPieces().getChildren());
        turn.setText("Turn: " + boardComponent.getTurn());
    }

    private void onError() {
        Toast.show(stage, "Invalid movement", 1000);
    }
}
