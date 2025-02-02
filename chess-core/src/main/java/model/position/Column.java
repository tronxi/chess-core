package model.position;

public enum Column {
    A(1),
    B(2),
    C(3),
    D(4),
    E(5),
    F(6),
    G(7),
    H(8);

    private final int position;
    Column(int position) {
        this.position = position;
    }

    public int differenceBetween(Column column) {
        return this.position - column.position;
    }

    public static Column fromString(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty.");
        }
        try {
            return Column.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid column: " + value);
        }
    }
}
