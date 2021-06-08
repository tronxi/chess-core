package model.position;

import org.apache.commons.lang3.Validate;

import java.util.Objects;

public class Square {
    private final Row row;
    private final Column column;

    public Square(Row row, Column column) {
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
