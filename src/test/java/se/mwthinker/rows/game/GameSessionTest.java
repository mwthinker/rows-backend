package se.mwthinker.rows.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.mwthinker.rows.protocol.S2cJoinedGame;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GameSessionTest {
	private static final int MAX_NUMBER_IN_ROW = 3;

	@Mock
	private User user1;
	private User user2;


	@BeforeEach
	void setUp() {
		user1 = createUser();
		user2 = createUser();
	}

	@Test
	void createNewGameSession() {
		// Given
		var gameSession1 = new GameSession(user1, MAX_NUMBER_IN_ROW);
		var gameSession2 = new GameSession(user1, MAX_NUMBER_IN_ROW);

		// When/Then
		assertThat(gameSession1.getGameId()).isNotEqualTo(gameSession2.getGameId());
		assertThat(gameSession1.getGameId()).isNotNull();
		assertThat(gameSession2.getGameId()).isNotNull();
	}

	@Test
	void joinExistingGame_withOnlyOnePlayer() {
		// Given
		var gameSession = new GameSession(user1, MAX_NUMBER_IN_ROW);

		// When
		gameSession.tryToAddUser(user2);

		// Then
		S2cJoinedGame expectedMessage = new S2cJoinedGame(gameSession.getGameId());
		verify(user1).sendToClient(expectedMessage);
		verify(user2).sendToClient(expectedMessage);
	}

	private User createUser() {
		var user = mock(User.class);
		lenient().when(user.getUuid()).thenReturn(UUID.randomUUID());
		lenient().when(user.getUsername()).thenReturn("Anonymous");

		return user;
	}
}
