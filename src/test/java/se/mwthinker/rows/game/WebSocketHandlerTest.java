package se.mwthinker.rows.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WebSocketHandlerTest {
	private WebSocketHandler webSocketHandler;

	@Mock
	private WebSocketSession webSocketSession;
	@Mock
	private MockedStatic<UUID> uuidMockedStatic;
	@Mock
	private UUID uuid;

	@BeforeEach
	void setUp() {
		webSocketHandler = new WebSocketHandler();
	}

	void handleNewConnection() throws IOException {
		// Given
		var uuid = createUUID("UUID_1");
		uuidMockedStatic.when(UUID::randomUUID).thenReturn(uuid);

		// When
		webSocketHandler.afterConnectionEstablished(webSocketSession);

		// Then
		verify(webSocketSession).sendMessage(argThat(TextMessageMatcher.of(
				"""
						{"type":"connected", "id":"UUID_1"}
					"""
		)));
	}

	private static UUID createUUID(String uuid) {
		var mockUuid = mock(UUID.class);
		when(mockUuid.toString()).thenReturn(uuid);
		return mockUuid;
	}


	private static class TextMessageMatcher implements ArgumentMatcher<TextMessage> {
		private final String data;

		public TextMessageMatcher(String data) {
			this.data = data.trim();
		}

		public static TextMessageMatcher of(String data) {
			return new TextMessageMatcher(data);
		}

		@Override
		public boolean matches(TextMessage other) {
			String otherData = new String(other.getPayload());
			return data.equals(otherData);
		}

		@Override
		public String toString() {
			return data;
		}
	}

}