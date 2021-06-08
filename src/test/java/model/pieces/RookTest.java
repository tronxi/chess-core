package model.pieces;

import model.Movement;
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
        Movement movement = new Movement(new Square(Column.A, Row.TWO), new Square(Column.H, Row.TWO));
        assertTrue(rook.isLegal(movement));
    }

    @Test
    public void shouldAllowMoveInSameColumn() {
        Movement movement = new Movement(new Square(Column.A, Row.ONE), new Square(Column.A, Row.EIGHT));
        assertTrue(rook.isLegal(movement));
    }

    @Test
    public void shouldNotAllowMoveInDiagonal() {
        Movement movement = new Movement(new Square(Column.B, Row.ONE), new Square(Column.A, Row.EIGHT));
        assertFalse(rook.isLegal(movement));
    }

}