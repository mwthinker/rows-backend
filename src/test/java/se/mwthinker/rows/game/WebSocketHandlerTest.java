package se.mwthinker.rows.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;
import se.mwthinker.rows.protocol.S2cUser;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WebSocketHandlerTest {
	private WebSocketHandler webSocketHandler;

	@Mock
	private WebSocketSession webSocketSession;
	@Mock
	private UserFactory userFactory;
	@Mock
	private UserHandler userHandler;

	@BeforeEach
	void setUp() {
		webSocketHandler = new WebSocketHandler(userFactory, userHandler);
	}

	@Test
	void handleNewConnection() {
		// Given
		var uuid = mock(UUID.class);
		User user = createUser(uuid);
		when(userFactory.createUser(webSocketSession)).thenReturn(user);

		// When
		webSocketHandler.afterConnectionEstablished(webSocketSession);

		// Then
		verify(user).sendToClient(new S2cUser(uuid));
	}

	private static User createUser(UUID uuid) {
		var user = mock(User.class);
		when(user.getUuid()).thenReturn(uuid);
		return user;
	}

}
