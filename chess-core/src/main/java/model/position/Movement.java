package model.position;

import model.pieces.Colors;
import model.pieces.Piece;
import org.apache.commons.lang3.Validate;

import java.util.HashMap;
import java.util.Map;

public class Movement {
    private final Square from;
    private final Square to;

    public Movement(Square from, Square to) {
        Validate.notNull(from);
        Validate.notNull(to);

        this.from = from;
        this.to = to;
    }

    public Square getFrom() {
        return this.from;
    }

    public Square getTo() {
        return this.to;
    }

    public boolean isHorseMovement() {
        return isDifferent() && this.from.isInHorse(this.to);
    }

    public boolean isWhitePawn(Map<Square, Piece> pieces) {
        if (!isDifferent()) {
            return false;
        }
        if (from.isInInitialWhitePawn()) {
            if (this.from.isUpOneSquare(this.to)) {
                return !pieces.containsKey(this.to);
            } else if (this.from.isUpTwoSquare(this.to)) {
                return !pieces.containsKey(this.to) && !pieces.containsKey(new Square(this.to.getColumn(), Row.THREE));
            }
            if (this.from.isUpInDiagonalOneSquare(this.to)) {
                if (pieces.containsKey(this.to)) {
                    Piece piece = pieces.get(this.to);
                    return piece.isColor(Colors.BLACK);
                }
            }
        } else {
            if (this.from.isUpOneSquare(this.to)) {
                return !pieces.containsKey(this.to);
            }
            if (this.from.isUpInDiagonalOneSquare(this.to)) {
                if (pieces.containsKey(this.to)) {
                    Piece piece = pieces.get(this.to);
                    return piece.isColor(Colors.BLACK);
                }
            }
        }
        return false;
    }

    public boolean isBlackPawn(Map<Square, Piece> pieces) {
        if (!isDifferent()) {
            return false;
        }
        if (from.isInInitialBlackPawn()) {
            if (this.from.isDownOneSquare(this.to)) {
                return !pieces.containsKey(this.to);
            } else if (this.from.isDownTwoSquare(this.to)) {
                return !pieces.containsKey(this.to) && !pieces.containsKey(new Square(this.to.getColumn(), Row.SIX));
            }
            if (this.from.isDownInDiagonalOneSquare(this.to)) {
                if (pieces.containsKey(this.to)) {
                    Piece piece = pieces.get(this.to);
                    return piece.isColor(Colors.WHITE);
                }
            }
        } else {
            if (this.from.isDownOneSquare(this.to)) {
                return !pieces.containsKey(this.to);
            }
            if (this.from.isDownInDiagonalOneSquare(this.to)) {
                if (pieces.containsKey(this.to)) {
                    Piece piece = pieces.get(this.to);
                    return piece.isColor(Colors.WHITE);
                }
            }
        }
        return false;
    }

    public boolean isInRow(Map<Square, Piece> pieces) {
        Map<Square, Piece> piecesWithoutEnPassant = piecesWithoutEnPassant(pieces);
        for (int i = getMinorColumn() + 1; i < getMaxColumn(); i++) {
            Square intermediate = new Square(Column.fromInt(i), from.getRow());
            if (piecesWithoutEnPassant.containsKey(intermediate)) {
                return false;
            }
        }
        return isDifferent() && this.from.isInRow(this.to);
    }

    public boolean isInColumn(Map<Square, Piece> pieces) {
        Map<Square, Piece> piecesWithoutEnPassant = piecesWithoutEnPassant(pieces);
        for (int i = getMinorRow() + 1; i < getMaxRow(); i++) {
            Square intermediate = new Square(from.getColumn(), Row.fromInt(i));
            if (piecesWithoutEnPassant.containsKey(intermediate)) {
                return false;
            }
        }
        return isDifferent() && this.from.isInColumn(this.to);
    }

    public boolean isInDiagonal(Map<Square, Piece> pieces) {
        if (!isDifferent() || !this.from.isInDiagonal(this.to)) {
            return false;
        }
        Map<Square, Piece> piecesWithoutEnPassant = piecesWithoutEnPassant(pieces);

        int fromCol = from.getColumn().getPosition();
        int toCol = to.getColumn().getPosition();
        int fromRow = from.getRow().getPosition();
        int toRow = to.getRow().getPosition();

        int colStep = (toCol > fromCol) ? 1 : -1;
        int rowStep = (toRow > fromRow) ? 1 : -1;

        int currentCol = fromCol + colStep;
        int currentRow = fromRow + rowStep;

        while (currentCol != toCol && currentRow != toRow) {
            Square intermediate = new Square(Column.fromInt(currentCol), Row.fromInt(currentRow));

            if (piecesWithoutEnPassant.containsKey(intermediate)) {
                return false;
            }

            currentCol += colStep;
            currentRow += rowStep;
        }

        return true;
    }

    private Map<Square, Piece> piecesWithoutEnPassant(Map<Square, Piece> pieces) {
        Map<Square, Piece> piecesWithoutEnPassant = new HashMap<>();
        for (Square square : pieces.keySet()) {
            Piece piece = pieces.get(square);
            if (!piece.isEnPassantPawn()) {
                piecesWithoutEnPassant.put(square, piece);
            }
        }
        return piecesWithoutEnPassant;
    }

    public boolean isInRowOneSquare() {
        return isDifferent() && this.from.isInRowOneSquare(this.to);
    }

    public boolean isInColumnOneSquare() {
        return isDifferent() && this.from.isInColumnOneSquare(this.to);
    }

    public boolean isInDiagonalOneSquare() {
        return isDifferent() && this.from.isInDiagonalOneSquare(this.to);
    }

    private boolean isDifferent() {
        return !from.equals(this.to);
    }

    private int getMinorRow() {
        return Math.min(from.getRow().getPosition(), to.getRow().getPosition());
    }

    private int getMaxRow() {
        return Math.max(from.getRow().getPosition(), to.getRow().getPosition());
    }

    private int getMinorColumn() {
        return Math.min(from.getColumn().getPosition(), to.getColumn().getPosition());
    }

    private int getMaxColumn() {
        return Math.max(from.getColumn().getPosition(), to.getColumn().getPosition());
    }

    public boolean isWhiteKingSideCastle() {
        return from.equals(new Square(Column.E, Row.ONE)) &&
                to.equals(new Square(Column.G, Row.ONE));
    }

    public boolean isWhiteQueenSideCastle() {
        return from.equals(new Square(Column.E, Row.ONE)) &&
                to.equals(new Square(Column.C, Row.ONE));
    }

    public boolean isBlackKingSideCastle() {
        return from.equals(new Square(Column.E, Row.EIGHT)) &&
                to.equals(new Square(Column.G, Row.EIGHT));
    }

    public boolean isBlackQueenSideCastle() {
        return from.equals(new Square(Column.E, Row.EIGHT)) &&
                to.equals(new Square(Column.C, Row.EIGHT));
    }

    public boolean isWhiteDoublePawn() {
        return from.getRow().equals(Row.TWO) &&
                to.getRow().equals(Row.FOUR);
    }

    public boolean isBlackDoublePawn() {
        return from.getRow().equals(Row.SEVEN) &&
                to.getRow().equals(Row.FIVE);
    }

}
