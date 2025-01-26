package se.mwthinker.rows.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class GameSessionTest {
	private static final int MAX_NUMBER_IN_ROW = 3;

	@Mock
	private User user;

	@BeforeEach
	void setUp() {
	}

	@Test
	void createNewGameSession() {
		// Given
		GameSession gameSession1 = new GameSession(user, MAX_NUMBER_IN_ROW);
		GameSession gameSession2 = new GameSession(user, MAX_NUMBER_IN_ROW);

		// When/Then
		assertThat(gameSession1.getGameId()).isNotEqualTo(gameSession2.getGameId());
		assertThat(gameSession1.getGameId()).isNotNull();
		assertThat(gameSession2.getGameId()).isNotNull();
	}
}
