package dev.tronxi.ui;

import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Board;

public class UIInitializer extends Application {

    private VBox boardRepresentation;
    private VBox wonPiecesRepresentation;
    private Label turn;
    private Stage stage;
    private Board board;
    private BoardComponent boardComponent;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("Chess");
        board = new Board();
        boardComponent = new BoardComponent();
        boardRepresentation = boardComponent.create(this::onError, this::onMove, board);
        wonPiecesRepresentation = boardComponent.drawWonPieces();
        turn = new Label("Turn: " + board.getTurn());

        VBox hBox = new VBox(boardRepresentation, turn, wonPiecesRepresentation);
        stage.setScene(new Scene(hBox, 670, 850));
        stage.show();
    }

    private void onMove(VBox updatedBoard) {
        boardRepresentation.getChildren().setAll(updatedBoard.getChildren());
        wonPiecesRepresentation.getChildren().setAll(boardComponent.drawWonPieces().getChildren());
        turn.setText("Turn: " + board.getTurn());
    }

    private void onError() {
        Toast.show(stage, "Invalid movement", 1000);
    }
}
