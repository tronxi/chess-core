package model.pieces;

import model.position.Movement;
import model.position.Square;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RookTest {
    private static Piece rook ;
    private static MovementsBuilder movementsBuilder;

    @BeforeAll
    public static void setUp() {
        rook = new Rook(Colors.WHITE);
        movementsBuilder = new MovementsBuilder();
    }

    @ParameterizedTest
    @MethodSource("provideMovement")
    public void testMovement(Movement movement, boolean expected) {
        Map<Square, Piece> pieces = new HashMap<>();
        assertEquals(expected, rook.isLegal(movement, pieces));
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

                Arguments.of(movementsBuilder.upPrincipalDiagonal(), false),
                Arguments.of(movementsBuilder.upPrincipalDiagonalOneSquare(), false),
                Arguments.of(movementsBuilder.downPrincipalDiagonalOneSquare(), false),
                Arguments.of(movementsBuilder.downPrincipalDiagonal(), false),
                Arguments.of(movementsBuilder.upInverseDiagonal(), false),
                Arguments.of(movementsBuilder.upInverseDiagonalOneSquare(), false),
                Arguments.of(movementsBuilder.downInverseDiagonal(), false),
                Arguments.of(movementsBuilder.downInverseDiagonalOneSquare(), false),

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