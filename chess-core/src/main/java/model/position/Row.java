package model.position;

public enum Row {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8);

    private final int position;

    Row(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public int differenceBetween(Row row) {
        return this.position - row.position;
    }

    public static Row fromInt(int value) {
        for (Row row : Row.values()) {
            if (row.position == value) {
                return row;
            }
        }
        throw new IllegalArgumentException("Invalid row number: " + value);
    }
}
