package game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class TicTacToeTest {
    @Test
    void can_determine_a_winner_row() {
        var ticTacToe = new TicTacToe();
        ticTacToe.placeX(0, 0);
        ticTacToe.placeO(2, 2);
        ticTacToe.placeX(0, 1);
        ticTacToe.placeO(1, 2);
        ticTacToe.placeX(0, 2);
        Assertions.assertEquals(GameResult.XWon, ticTacToe.getResult());
    }

    @Test
    void can_determine_a_winner_column() {
        var ticTacToe = new TicTacToe();
        ticTacToe.placeO(0, 0);
        ticTacToe.placeX(0, 2);
        ticTacToe.placeO(1, 0);
        ticTacToe.placeX(1, 2);
        ticTacToe.placeO(2, 0);
        Assertions.assertEquals(GameResult.OWon, ticTacToe.getResult());
    }

    @Test
    void can_determine_a_winner_diagonal() {
        var ticTacToe = new TicTacToe();
        ticTacToe.placeO(0, 0);
        ticTacToe.placeX(1, 0);
        ticTacToe.placeO(1, 1);
        ticTacToe.placeX(2, 1);
        ticTacToe.placeO(2, 2);
        Assertions.assertEquals(GameResult.OWon, ticTacToe.getResult());
    }

    @Test
    void players_can_make_two_moves_in_a_row() {
        var ticTacToe = new TicTacToe();
        ticTacToe.placeX(0, 0);
        Assertions.assertThrows(IllegalStateException.class, () ->
                ticTacToe.placeX(0, 1)
        );
    }

    @Test
    void cannot_put_on_the_same_field() {
        var ticTacToe = new TicTacToe();
        ticTacToe.placeX(0, 0);
        Assertions.assertThrows(IllegalStateException.class, () ->
                ticTacToe.placeO(0, 0)
        );
        Assertions.assertThrows(IllegalStateException.class, () ->
                ticTacToe.placeX(0, 0)
        );
    }

    @Test
    void game_is_drawn_when_all_fields_are_taken() {
        var ticTacToe = new TicTacToe();
        ticTacToe.placeO(2, 0);
        ticTacToe.placeX(1, 0);
        ticTacToe.placeO(0, 0);
        ticTacToe.placeX(0, 1);
        ticTacToe.placeO(1, 1);
        ticTacToe.placeX(0, 2);
        ticTacToe.placeO(2, 1);
        ticTacToe.placeX(2, 2);
        ticTacToe.placeO(1, 2);
        Assertions.assertEquals(GameResult.Draw, ticTacToe.getResult());
    }

    @Test
    void cannot_make_moves_after_game_is_over() {
        var ticTacToe = new TicTacToe();
        ticTacToe.placeX(0, 0);
        ticTacToe.placeO(0, 1);
        ticTacToe.placeX(0, 2);
        ticTacToe.placeO(1, 0);
        ticTacToe.placeX(1, 1);
        ticTacToe.placeO(1, 2);
        ticTacToe.placeX(2, 0);
        ticTacToe.placeO(2, 1);
        ticTacToe.placeX(2, 2);
        Assertions.assertThrows(IllegalStateException.class, () ->
                ticTacToe.placeO(2, 2)
        );
    }

    static Stream<Arguments> outOfBoardCords() {
        return Stream.of(
                Arguments.of(-1, 0),
                Arguments.of(0, -1),
                Arguments.of(5, 0),
                Arguments.of(0, 5)
        );
    }

    @ParameterizedTest
    @MethodSource("outOfBoardCords")
    void cannot_place_out_of_board(int x, int y) {
        var ticTacToe = new TicTacToe();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ticTacToe.placeX(x, y);
        });
    }
}
