package se.mwthinker.rows.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.mwthinker.rows.protocol.C2sCreateGame;
import se.mwthinker.rows.protocol.C2sGetRooms;
import se.mwthinker.rows.protocol.S2cCreatedGame;
import se.mwthinker.rows.protocol.S2cRooms;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserHandlerTest {
	private UserHandler userHandler;

	@Mock
	private GameSessionFactory gameSessionFactory;

	@BeforeEach
	void setUp() {
		userHandler = new UserHandler(gameSessionFactory);
	}

	@Test
	void handleCreateGame() {
		// Given
		User user = mock(User.class);
		UUID gameId = UUID.randomUUID();
		var gameSession = mockGameSession(gameId);
		when(gameSessionFactory.createGameSession(user, 5)).thenReturn(gameSession);

		// When
		userHandler.handleMessage(user, new C2sCreateGame());

		// Then
		verify(user).sendToClient(new S2cCreatedGame(gameId));
	}

	@Test
	void handleGetRooms_whenNoRoomsAvailable() {
		// Given
		User user = mock(User.class);

		// When
		userHandler.handleMessage(user, new C2sGetRooms());

		// Then
		verify(user).sendToClient(new S2cRooms(List.of()));
	}

	private static GameSession mockGameSession(UUID gameId) {
		var gameSession = mock(GameSession.class);
		lenient().when(gameSession.getGameId()).thenReturn(gameId);
		return gameSession;
	}

}
