package dev.tronxi.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.pieces.*;

import java.util.List;
import java.util.Objects;

public class PieceRepresentation {
    public static ImageView loadPngFromResources(Piece piece) {
        String imageName;

        if (piece.isColor(Colors.BLACK)) {
            imageName = switch (piece.getPieces()) {
                case KING -> "kb";
                case QUEEN -> "qb";
                case BISHOP -> "bb";
                case KNIGHT -> "nb";
                case ROOK -> "rb";
                case PAWN -> "pb";
            };
        } else {
            imageName = switch (piece.getPieces()) {
                case KING -> "kw";
                case QUEEN -> "qw";
                case BISHOP -> "bw";
                case KNIGHT -> "nw";
                case ROOK -> "rw";
                case PAWN -> "pw";
            };
        }
        Image image = new Image(Objects.requireNonNull(PieceRepresentation.class.getResourceAsStream("/pieces/" + imageName + ".png")));
        return new ImageView(image);
    }

    public static List<Piece> getPromotePieces(Colors colors) {
        return switch (colors) {
            case WHITE ->
                    List.of(new Queen(Colors.WHITE), new Rook(Colors.WHITE), new Bishop(Colors.WHITE), new Knight(Colors.WHITE));
            case BLACK ->
                    List.of(new Queen(Colors.BLACK), new Rook(Colors.BLACK), new Bishop(Colors.BLACK), new Knight(Colors.BLACK));
        };
    }

    private static ImageView getImage(String representation) {
        Image image = new Image(Objects.requireNonNull(PieceRepresentation.class.getResourceAsStream("/pieces/" + representation + ".png")));
        return new ImageView(image);
    }
}
