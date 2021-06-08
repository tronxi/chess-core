package model.pieces;

import model.Movement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class KingTest {
    private static Piece king;
    private static MovementsBuilder movementsBuilder;

    @BeforeAll
    public static void setUp() {
        king = new King(Colors.WHITE);
        movementsBuilder = new MovementsBuilder();
    }

    @ParameterizedTest
    @MethodSource("provideMovement")
    public void testMovement(Movement movement, boolean expected) {
        assertEquals(king.isLegal(movement), expected);
    }

    private static Stream<Arguments> provideMovement() {
        return Stream.of(
                Arguments.of(movementsBuilder.rightRowOneSquare(), true),
                Arguments.of(movementsBuilder.rightRow(), false),
                Arguments.of(movementsBuilder.leftRowOneSquare(), true),
                Arguments.of(movementsBuilder.leftRow(), false),

                Arguments.of(movementsBuilder.upColumn(), false),
                Arguments.of(movementsBuilder.upColumnsOneSquare(), true),
                Arguments.of(movementsBuilder.downColumn(), false),
                Arguments.of(movementsBuilder.downColumnOneSquare(), true),

                Arguments.of(movementsBuilder.upPrincipalDiagonal(), false),
                Arguments.of(movementsBuilder.upPrincipalDiagonalOneSquare(), true),
                Arguments.of(movementsBuilder.downPrincipalDiagonalOneSquare(), true),
                Arguments.of(movementsBuilder.downPrincipalDiagonal(), false),
                Arguments.of(movementsBuilder.upInverseDiagonal(), false),
                Arguments.of(movementsBuilder.upInverseDiagonalOneSquare(), true),
                Arguments.of(movementsBuilder.downInverseDiagonal(), false),
                Arguments.of(movementsBuilder.downInverseDiagonalOneSquare(), true),

                Arguments.of(movementsBuilder.horseMovement1(), false),
                Arguments.of(movementsBuilder.horseMovement2(), false),
                Arguments.of(movementsBuilder.horseMovement3(), false),
                Arguments.of(movementsBuilder.horseMovement4(), false),
                Arguments.of(movementsBuilder.horseMovement5(), false),
                Arguments.of(movementsBuilder.horseMovement6(), false),
                Arguments.of(movementsBuilder.horseMovement7(), false),
                Arguments.of(movementsBuilder.horseMovement8(), false),

                Arguments.of(movementsBuilder.noMovement(), false)
        );
    }
}