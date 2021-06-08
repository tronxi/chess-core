package model.pieces;

import model.Movement;
import model.position.Column;
import model.position.Row;
import model.position.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BishopTest {
    private Bishop bishop;

    @BeforeEach
    public void setUp() {
        bishop = new Bishop(Colors.WHITE);
    }

    @Test
    public void shouldNotAllowMoveInSameRow() {
        Movement movement = new Movement(new Square(Column.A, Row.TWO), new Square(Column.H, Row.TWO));
        assertFalse(bishop.isLegal(movement));
    }

    @Test
    public void shouldNotAllowMoveInSameColumn() {
        Movement movement = new Movement(new Square(Column.A, Row.ONE), new Square(Column.A, Row.TWO));
        assertFalse(bishop.isLegal(movement));
    }

    @Test
    public void shouldAllowMoveInDiagonal() {
        Movement movement = new Movement(new Square(Column.C, Row.ONE), new Square(Column.E, Row.THREE));
        assertTrue(bishop.isLegal(movement));
    }

    @Test
    public void shouldAllowMoveInInverseDiagonal() {
        Movement movement = new Movement(new Square(Column.C, Row.ONE), new Square(Column.B, Row.TWO));
        assertTrue(bishop.isLegal(movement));
    }

    @Test
    public void shouldAllowMoveInDiagonalDown() {
        Movement movement = new Movement(new Square(Column.E, Row.SIX), new Square(Column.A, Row.TWO));
        assertTrue(bishop.isLegal(movement));
    }

    @Test
    public void shouldAllowMoveInInverseDiagonalDown() {
        Movement movement = new Movement(new Square(Column.C, Row.SIX), new Square(Column.F, Row.THREE));
        assertTrue(bishop.isLegal(movement));
    }

    @Test
    public void shouldNotAllowHorseMovement() {
        Movement movement = new Movement(new Square(Column.A, Row.ONE), new Square(Column.B, Row.THREE));
        assertFalse(bishop.isLegal(movement));
    }

}