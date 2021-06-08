package model.position;

import org.apache.commons.lang3.Validate;

import java.util.Objects;

public class Square {
    private final Row row;
    private final Column column;

    public Square(Column column, Row row) {
        Validate.notNull(row);
        Validate.notNull(column);

        this.row = row;
        this.column = column;
    }

    public boolean isInRow(Square square) {
        return this.row == square.row;
    }

    public boolean isInColumn(Square square) {
        return this.column == square.column;
    }

    public boolean isInDiagonal(Square square) {
        int columnDifference = Math.abs(this.column.differenceBetween(square.column));
        int rowDifference = Math.abs(this.row.differenceBetween(square.row));
        return columnDifference == rowDifference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return row == square.row && column == square.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
