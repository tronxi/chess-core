package dev.tronxi.ui;

import exceptions.InvalidMovementException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Board;
import model.pieces.Colors;
import model.pieces.Piece;
import model.position.Column;
import model.position.Movement;
import model.position.Row;
import model.position.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BoardComponent {

    private final Color white = new Color(0.933f, 0.933f, 0.835f, 1f);
    private final Color black = new Color(0.486f, 0.584f, 0.365f, 1f);
    private final Color whiteSelected = new Color(0.961f, 0.961f, 0.604f, 1f);
    private final Color blackSelected = new Color(0.741f, 0.788f, 0.369f, 1f);
    private Stage stage;
    private final int squareSize = 80;
    private final int labelFontSize = 20;
    private int totalPieces = 1;
    private Board board;
    private Square from;
    private List<Square> legalMoves = new ArrayList<>();
    private Consumer<VBox> onMovement;

    public VBox create(Stage stage, Consumer<VBox> onMovement, Board board) {
        this.stage = stage;
        this.onMovement = onMovement;
        this.board = board;
        return drawBoard();
    }

    public VBox reset(Board board) {
        this.from = null;
        this.board = board;
        this.legalMoves.clear();
        return drawBoard();
    }

    public Colors getTurn() {
        return board.getTurn();
    }

    private VBox drawBoard() {
        VBox root = new VBox();

        for (int i = 8; i >= 1; i--) {
            HBox hbox = new HBox();
            hbox.getChildren().add(generateRectangleForLabel(String.valueOf(i)));
            for (char ch = 'a'; ch <= 'h'; ch++) {
                Row row = Row.fromInt(i);
                Column column = Column.fromString(String.valueOf(ch));
                Square square = new Square(column, row);
                boolean showAsLegal = legalMoves.contains(square);
                if (board.getPieces().containsKey(square)) {
                    Piece piece = board.getPieces().get(square);
                    hbox.getChildren().add(new SquareComponent(this::onClick, squareSize, getColor(square, totalPieces), column, showAsLegal, row, piece));
                } else {
                    hbox.getChildren().add(new SquareComponent(this::onClick, squareSize, getColor(square, totalPieces), column, showAsLegal, row));
                }
                totalPieces++;
            }
            hbox.getChildren().add(generateRectangleForLabel(String.valueOf(i)));
            totalPieces++;
            root.getChildren().add(hbox);
        }
        root.getChildren().add(notation());
        return root;
    }

    private StackPane generateRectangleForLabel(String label) {
        StackPane stackPane = new StackPane();
        Rectangle rectangleLabel = new Rectangle();
        rectangleLabel.setWidth(40);
        stackPane.setAlignment(Pos.CENTER);
        Label numberLabel = new Label(label);
        numberLabel.setFont(Font.font(labelFontSize));
        stackPane.getChildren().addAll(rectangleLabel, numberLabel);
        return stackPane;
    }

    public VBox drawWonPieces() {
        VBox root = new VBox();
        root.setPadding(new Insets(10, 0, 0, 40));
        HBox whitePieces = new HBox();
        HBox blackPieces = new HBox();
        List<Piece> whiteWonPieces = board.getWonPieces().get(Colors.WHITE);
        for (Piece piece : whiteWonPieces) {
            ImageView imageView = PieceRepresentation.loadPngFromResources(piece);
            imageView.setFitWidth(30);
            imageView.setFitHeight(30);
            whitePieces.getChildren().add(imageView);
        }
        List<Piece> blackWonPieces = board.getWonPieces().get(Colors.BLACK);
        for (Piece piece : blackWonPieces) {
            ImageView imageView = PieceRepresentation.loadPngFromResources(piece);
            imageView.setFitWidth(30);
            imageView.setFitHeight(30);
            blackPieces.getChildren().add(imageView);
        }
        root.getChildren().addAll(whitePieces, blackPieces);
        return root;
    }

    private void onClick(Square square) {
        if (from == null) {
            if (board.getPieces().containsKey(square)) {
                Piece piece = board.getPieces().get(square);
                if (piece.isColor(board.getTurn())) {
                    this.from = square;
                    legalMoves = board.calculateLegalMoves(square);
                    this.onMovement.accept(drawBoard());
                }
            }
        } else {
            if (board.getPieces().containsKey(square)) {
                Piece pieceFrom = board.getPieces().get(from);
                Piece piece = board.getPieces().get(square);
                if (pieceFrom.isColor(piece.getColor())) {
                    this.from = square;
                    legalMoves = board.calculateLegalMoves(square);
                    this.onMovement.accept(drawBoard());
                } else {
                    finishMove(square);
                }
            } else {
                finishMove(square);
            }
        }
    }

    private void finishMove(Square square) {
        try {
            legalMoves = new ArrayList<>();
            board.move(new Movement(from, square));
            this.from = null;
            boolean isInCheck = board.isInCheck(board.getTurn());
            boolean hasLegalMoves = board.hasLegalMoves(board.getTurn());

            if (isInCheck && hasLegalMoves) {
                Toast.show(stage, "Check!", 1000);
            } else if(isInCheck && !hasLegalMoves) {
                Toast.show(stage, "CheckMate!", 1000);
            } else if(!isInCheck && !hasLegalMoves) {
                Toast.show(stage, "Draw!", 1000);
            }
            this.onMovement.accept(drawBoard());
        } catch (InvalidMovementException e) {
            this.from = null;
            this.onMovement.accept(drawBoard());
        }
    }

    private HBox notation() {
        HBox hbox = new HBox();
        hbox.getChildren().add(generateRectangleForLabel(""));
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
        if (piece % 2 == 0) {
            if (square.equals(from)) {
                return blackSelected;
            }
            return black;
        }
        if (square.equals(from)) {
            return whiteSelected;
        }
        return white;
    }
}
