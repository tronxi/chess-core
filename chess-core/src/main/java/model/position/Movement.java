package model.position;

import model.pieces.Colors;
import model.pieces.Piece;
import org.apache.commons.lang3.Validate;

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
            if (this.from.isUpOneSquare(this.to) || this.from.isUpTwoSquare(this.to)) {
                return !pieces.containsKey(this.to);
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
            if (this.from.isDownOneSquare(this.to) || this.from.isDownTwoSquare(this.to)) {
                return !pieces.containsKey(this.to);
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
        for (int i = getMinorColumn() + 1; i < getMaxColumn(); i++) {
            Square intermediate = new Square(Column.fromInt(i), from.getRow());
            if (pieces.containsKey(intermediate)) {
                return false;
            }
        }
        return isDifferent() && this.from.isInRow(this.to);
    }

    public boolean isInColumn(Map<Square, Piece> pieces) {
        for (int i = getMinorRow() + 1; i < getMaxRow(); i++) {
            Square intermediate = new Square(from.getColumn(), Row.fromInt(i));
            if (pieces.containsKey(intermediate)) {
                return false;
            }
        }
        return isDifferent() && this.from.isInColumn(this.to);
    }

    public boolean isInDiagonal(Map<Square, Piece> pieces) {
        int tot = 1;
        if (from.getRow().getPosition() < to.getRow().getPosition()) {
            if (from.getColumn().getPosition() < to.getColumn().getPosition()) {
                for (int i = getMinorColumn() + 1; i < getMaxColumn(); i++) {
                    Square intermediate = new Square(Column.fromInt(i), Row.fromInt(getMinorRow() + tot));
                    tot++;
                    if (pieces.containsKey(intermediate)) {
                        return false;
                    }
                }
            } else {
                for (int i = getMaxRow() - 1; i >= getMinorRow() + 1; i--) {
                    Square intermediate = new Square(Column.fromInt(i), Row.fromInt(getMinorRow() + tot));
                    tot++;
                    if (pieces.containsKey(intermediate)) {
                        return false;
                    }
                }
            }
        } else {
            if (from.getColumn().getPosition() < to.getColumn().getPosition()) {
                for (int i = getMaxRow() - 1; i >= getMinorRow() + 1; i--) {
                    Square intermediate = new Square(Column.fromInt(i), Row.fromInt(getMinorRow() + tot));
                    tot++;
                    if (pieces.containsKey(intermediate)) {
                        return false;
                    }
                }
            } else {
                for (int i = getMinorColumn() + 1; i < getMaxColumn(); i++) {
                    Square intermediate = new Square(Column.fromInt(i), Row.fromInt(getMinorRow() + tot));
                    tot++;
                    if (pieces.containsKey(intermediate)) {
                        return false;
                    }
                }
            }
        }

        return isDifferent() && this.from.isInDiagonal(this.to);
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

}
