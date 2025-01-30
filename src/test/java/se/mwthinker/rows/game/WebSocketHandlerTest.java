package se.mwthinker.rows.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import se.mwthinker.rows.protocol.C2sGetRooms;
import se.mwthinker.rows.protocol.ProtocolException;
import se.mwthinker.rows.protocol.S2cRooms;
import se.mwthinker.rows.protocol.S2cUser;

import java.io.IOException;
import java.util.List;
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

	@BeforeEach
	void setUp() {
		webSocketHandler = new WebSocketHandler(userFactory);
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

	@Test
	void handleGetRooms_whenNoRoomsAvailable() {
		// Given
		User user = createUser();
		addUsers(createUser(), user, createUser());

		// When
		webSocketHandler.handleTextMessage(user.getSession(), createTextMessage(new C2sGetRooms()));

		// Then
		verify(user).sendToClient(new S2cRooms(List.of()));
	}

	private void addUsers(User... users) {
		for (var user : users) {
			when(userFactory.createUser(user.getSession())).thenReturn(user);
			webSocketHandler.afterConnectionEstablished(user.getSession());
		}
	}

	private static User createUser(UUID uuid) {
		var user = mock(User.class);
		when(user.getUuid()).thenReturn(uuid);
		when(user.getSession()).thenReturn(mock(WebSocketSession.class));
		return user;
	}

	private static User createUser() {
		return createUser(UUID.randomUUID());
	}

	private static TextMessage createTextMessage(Object object) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return new TextMessage(mapper.writeValueAsString(object));
		} catch (IOException e) {
			throw new ProtocolException(e);
		}
	}

}
