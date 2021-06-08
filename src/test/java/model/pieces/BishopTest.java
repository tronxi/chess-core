package model.pieces;

import model.Movement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BishopTest {
    private static Piece bishop ;
    private static MovementsBuilder movementsBuilder;

    @BeforeAll
    public static void setUp() {
        bishop = new Bishop(Colors.WHITE);
        movementsBuilder = new MovementsBuilder();
    }

    @ParameterizedTest
    @MethodSource("provideMovement")
    public void testMovement(Movement movement, boolean expected) {
        assertEquals(bishop.isLegal(movement), expected);
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