package se.mwthinker.rows_backend;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class BoardTest {
	private Board board;

	@BeforeEach
	void setUp() {
		board = new Board(3);
	}

	@AfterEach
	void tearDown() {
	}

	private static Board getCellFromStrings(String strings, int maxNumberInARow) {
		Board board = new Board(maxNumberInARow);

		int x = 0;
		int y = 0;
		for (char token : strings.toCharArray()) {
			if (token == 'X' || token == 'O') {
				board.put(x, y, token == 'X' ? CellState.X : CellState.O);
				++x;
			} else if (token == '.') {
				++x;
			} else if (token == '\n') {
				++y;
				x = 0;
			} else {
				throw new IllegalArgumentException("Invalid character: " + token);
			}
		}
		return board;
	}

	private static Stream<Arguments> getBoardForTest() {
		return Stream.of(
				Arguments.of(getCellFromStrings("""
						.XXO.
						.XO..
						.....
						""", 3),
						false),
				Arguments.of(getCellFromStrings("""
						.XXO..
						.XOO..
						...O..
						...O..
						""", 4),
						true)
		);
	}

	@ParameterizedTest
	@MethodSource("getBoardForTest")
	void testIsGameOver(Board board, boolean gameOver) {
		boolean isGameOver = board.isGameOver();

		assertThat(isGameOver).isEqualTo(gameOver);
	}
}
