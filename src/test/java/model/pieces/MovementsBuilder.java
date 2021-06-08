package model.pieces;

import model.Movement;
import model.position.Column;
import model.position.Row;
import model.position.Square;

public class MovementsBuilder {

    //ROWS MOVEMENTS
    public Movement rightRowOneSquare() {
        return buildMovement(Column.A, Row.ONE, Column.B, Row.ONE);
    }
    public Movement rightRow() {
        return buildMovement(Column.A, Row.ONE, Column.H, Row.ONE);
    }
    public Movement leftRowOneSquare() {
        return buildMovement(Column.B, Row.ONE, Column.A, Row.ONE);
    }
    public Movement leftRow() {
        return buildMovement(Column.H, Row.ONE, Column.A, Row.ONE);
    }

    //COLUMNS MOVEMENTS
    public Movement upColumnsOneSquare() {
        return buildMovement(Column.A, Row.ONE, Column.A, Row.TWO);
    }
    public Movement upColumn() {
        return buildMovement(Column.A, Row.ONE, Column.A, Row.EIGHT);
    }
    public Movement downColumnOneSquare() {
        return buildMovement(Column.D, Row.EIGHT, Column.D, Row.SEVEN);
    }
    public Movement downColumn() {
        return buildMovement(Column.D, Row.EIGHT, Column.D, Row.ONE);
    }

    //DIAGONAL MOVEMENTS
    public Movement upPrincipalDiagonalOneSquare() {
        return buildMovement(Column.D, Row.FOUR, Column.E, Row.FIVE);
    }
    public Movement upPrincipalDiagonal() {
        return buildMovement(Column.A, Row.ONE, Column.H, Row.EIGHT);
    }
    public Movement downPrincipalDiagonalOneSquare() {
        return buildMovement(Column.D, Row.FOUR, Column.C, Row.THREE);
    }
    public Movement downPrincipalDiagonal() {
        return buildMovement(Column.H, Row.EIGHT, Column.A, Row.ONE);
    }
    public Movement upInverseDiagonalOneSquare() {
        return buildMovement(Column.D, Row.FOUR, Column.C, Row.FIVE);
    }
    public Movement upInverseDiagonal() {
        return buildMovement(Column.H, Row.ONE, Column.A, Row.EIGHT);
    }
    public Movement downInverseDiagonalOneSquare() {
        return buildMovement(Column.D, Row.FOUR, Column.E, Row.THREE);
    }
    public Movement downInverseDiagonal() {
        return buildMovement(Column.A, Row.EIGHT, Column.H, Row.ONE);
    }

    //HORSE MOVEMENTS
    public Movement horseMovement1() {
        return buildMovement(Column.F, Row.THREE, Column.G, Row.FIVE);
    }
    public Movement horseMovement2() {
        return buildMovement(Column.F, Row.THREE, Column.H, Row.FOUR);
    }
    public Movement horseMovement3() {
        return buildMovement(Column.F, Row.THREE, Column.H, Row.TWO);
    }
    public Movement horseMovement4() {
        return buildMovement(Column.F, Row.THREE, Column.G, Row.ONE);
    }
    public Movement horseMovement5() {
        return buildMovement(Column.F, Row.THREE, Column.E, Row.ONE);
    }
    public Movement horseMovement6() {
        return buildMovement(Column.F, Row.THREE, Column.D, Row.TWO);
    }
    public Movement horseMovement7() {
        return buildMovement(Column.F, Row.THREE, Column.D, Row.FOUR);
    }
    public Movement horseMovement8() {
        return buildMovement(Column.F, Row.THREE, Column.E, Row.FIVE);
    }

    //NO MOVEMENT
    public Movement noMovement() {
        return buildMovement(Column.A, Row.ONE, Column.A, Row.ONE);
    }

    private Movement buildMovement(Column columnOrigin, Row rowOrigin, Column columnTarget, Row rowTarget) {
        return new Movement(new Square(columnOrigin, rowOrigin), new Square(columnTarget, rowTarget));
    }
}
