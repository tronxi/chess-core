package dev.tronxi.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.pieces.Colors;
import model.pieces.Piece;

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
}
