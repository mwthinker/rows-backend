package se.mwthinker.rows;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GameSession {
	private final List<User> users = new ArrayList<>();
	private Board board;

	public GameSession(User player1, int maxNumberInARow) {
		this.users.add(player1);
		this.board = new Board(maxNumberInARow);
	}

	int getNumberOfUsers() {
		return users.size();
	}

	void receiveMessage(User user, GameSessionState state) {
		// your code here
	}

}

class UserSession {
	private final User user;
	private final WebSocketSession session;

	public UserSession(User user, WebSocketSession session) {
		this.user = user;
		this.session = session;
	}

	public User getUser() {
		return user;
	}

	public WebSocketSession getSession() {
		return session;
	}

	public void receiveMessage(GameSessionState state) {
		// your code here
	}
}

// Enum for type
enum LoobyType {
	CREATE_GAME,
	JOIN_GAME
}

// Base class
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = CreateGame.class, name = "CREATE_GAME"),
		@JsonSubTypes.Type(value = JoinGame.class, name = "JOIN_GAME")
})
abstract class GameLobbyState {
	public LoobyType type;
}

// Subclass for STATE_A
class CreateGame extends GameLobbyState {
	public String propertyA;
}

// Subclass for STATE_B
class JoinGame extends GameLobbyState {
	public int propertyB;
}

enum GameSessionType {
	GAME_MOVE
}

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = CreateGame.class, name = "GAME_MOVE")
})
abstract class GameSessionState {
	public LoobyType type; // Use enum here
}

class GameMoveState extends GameSessionState {
	public GameSessionType type;
}

// Subclass for STATE_A
class GameMove extends GameLobbyState {
	public String propertyA;
}

public class WebSocketHandler extends TextWebSocketHandler {
	private static Map<WebSocketSession, User> userBySession = new HashMap<>();
	private static Map<User, GameSession> gameSessionByUser = new HashMap<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		userBySession.put(session, new User("Anonymous"));
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) {
		User user = userBySession.get(session);

		GameSession gameSession = gameSessionByUser.get(user);
		if (gameSession != null) {
			var gameSessionState = readMessage(session, message, GameSessionState.class);
			gameSession.receiveMessage(user, gameSessionState);
		} else {
			GameLobbyState state = readMessage(session, message, GameLobbyState.class);
			if (state instanceof CreateGame) {
				handleGameLobbyState(user, (CreateGame) state);
			} else if (state instanceof JoinGame) {
				handleGameLobbyState(user, (JoinGame) state);
			}
		}
	}

	public static <T> T readMessage(WebSocketSession webSocketSession, TextMessage message, Class<T> clazz) {
		try {
			return new ObjectMapper().readValue(message.getPayload(), clazz);
		} catch (IOException e) {
			sendMessage(webSocketSession, "Invalid message");
			throw new RuntimeException(e);
		}
	}

	public static void sendMessage(WebSocketSession user, String message) {
		try {
			user.sendMessage(new TextMessage(message));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void handleGameLobbyState(User user, JoinGame joinGame) {
		// TODO! Join an existing game session
	}

	private void handleGameLobbyState(User user, CreateGame createGame) {
		// TODO! Create a new game session
	}


	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus){
		userBySession.remove(session);
		// TODO! Handle game session
	}
}
