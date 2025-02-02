package model.pieces;

import model.position.Movement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class QueenTest {
    private static Piece queen;
    private static MovementsBuilder movementsBuilder;

    @BeforeAll
    public static void setUp() {
        queen = new Queen(Colors.WHITE);
        movementsBuilder = new MovementsBuilder();
    }

    @ParameterizedTest
    @MethodSource("provideMovement")
    public void testMovement(Movement movement, boolean expected) {
        assertEquals(expected, queen.isLegal(movement));
    }

    private static Stream<Arguments> provideMovement() {
        return Stream.of(
                Arguments.of(movementsBuilder.rightRowOneSquare(), true),
                Arguments.of(movementsBuilder.rightRow(), true),
                Arguments.of(movementsBuilder.leftRowOneSquare(), true),
                Arguments.of(movementsBuilder.leftRow(), true),

                Arguments.of(movementsBuilder.upColumn(), true),
                Arguments.of(movementsBuilder.upColumnsOneSquare(), true),
                Arguments.of(movementsBuilder.downColumn(), true),
                Arguments.of(movementsBuilder.downColumnOneSquare(), true),

                Arguments.of(movementsBuilder.upPrincipalDiagonal(), true),
                Arguments.of(movementsBuilder.upPrincipalDiagonalOneSquare(), true),
                Arguments.of(movementsBuilder.downPrincipalDiagonalOneSquare(), true),
                Arguments.of(movementsBuilder.downPrincipalDiagonal(), true),
                Arguments.of(movementsBuilder.upInverseDiagonal(), true),
                Arguments.of(movementsBuilder.upInverseDiagonalOneSquare(), true),
                Arguments.of(movementsBuilder.downInverseDiagonal(), true),
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