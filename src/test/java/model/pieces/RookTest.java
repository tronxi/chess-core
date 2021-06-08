package model.pieces;

import model.Movement;
import model.colors.Colors;
import model.position.Column;
import model.position.Row;
import model.position.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RookTest {
    private Rook rook;

    @BeforeEach
    public void setUp() {
        rook = new Rook(Colors.WHITE);
    }

    @Test
    public void shouldAllowMoveInSameRow() {
        Movement movement = new Movement(new Square(Row.TWO, Column.A), new Square(Row.TWO, Column.H));
        assertTrue(rook.isLegal(movement));
    }

    @Test
    public void shouldAllowMoveInSameColumn() {
        Movement movement = new Movement(new Square(Row.ONE, Column.A), new Square(Row.EIGHT, Column.A));
        assertTrue(rook.isLegal(movement));
    }

    @Test
    public void shouldNotAllowMoveInDiagonal() {
        Movement movement = new Movement(new Square(Row.ONE, Column.B), new Square(Row.EIGHT, Column.A));
        assertFalse(rook.isLegal(movement));
    }

}