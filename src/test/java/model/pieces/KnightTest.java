package model.pieces;

import model.Movement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class KnightTest {
    private static Piece knight ;
    private static MovementsBuilder movementsBuilder;

    @BeforeAll
    public static void setUp() {
        knight = new Knight(Colors.WHITE);
        movementsBuilder = new MovementsBuilder();
    }

    @ParameterizedTest
    @MethodSource("provideMovement")
    public void testMovement(Movement movement, boolean expected) {
        assertEquals(knight.isLegal(movement), expected);
    }

    private static Stream<Arguments> provideMovement() {
        return Stream.of(
                Arguments.of(movementsBuilder.rightRowOneSquare(), false),
                Arguments.of(movementsBuilder.rightRow(), false),
                Arguments.of(movementsBuilder.leftRowOneSquare(), false),
                Arguments.of(movementsBuilder.leftRow(), false),

                Arguments.of(movementsBuilder.upColumn(), false),
                Arguments.of(movementsBuilder.upColumnsOneSquare(), false),
                Arguments.of(movementsBuilder.downColumn(), false),
                Arguments.of(movementsBuilder.downColumnOneSquare(), false),

                Arguments.of(movementsBuilder.upPrincipalDiagonal(), false),
                Arguments.of(movementsBuilder.upPrincipalDiagonalOneSquare(), false),
                Arguments.of(movementsBuilder.downPrincipalDiagonalOneSquare(), false),
                Arguments.of(movementsBuilder.downPrincipalDiagonal(), false),
                Arguments.of(movementsBuilder.upInverseDiagonal(), false),
                Arguments.of(movementsBuilder.upInverseDiagonalOneSquare(), false),
                Arguments.of(movementsBuilder.downInverseDiagonal(), false),
                Arguments.of(movementsBuilder.downInverseDiagonalOneSquare(), false),

                Arguments.of(movementsBuilder.horseMovement1(), true),
                Arguments.of(movementsBuilder.horseMovement2(), true),
                Arguments.of(movementsBuilder.horseMovement3(), true),
                Arguments.of(movementsBuilder.horseMovement4(), true),
                Arguments.of(movementsBuilder.horseMovement5(), true),
                Arguments.of(movementsBuilder.horseMovement6(), true),
                Arguments.of(movementsBuilder.horseMovement7(), true),
                Arguments.of(movementsBuilder.horseMovement8(), true),

                Arguments.of(movementsBuilder.noMovement(), false)
        );
    }
}