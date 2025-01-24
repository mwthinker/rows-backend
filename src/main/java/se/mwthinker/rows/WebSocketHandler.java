package se.mwthinker.rows;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import se.mwthinker.rows.protocol.CreateGame;
import se.mwthinker.rows.protocol.GameLobbyState;
import se.mwthinker.rows.protocol.GameSessionState;
import se.mwthinker.rows.protocol.JoinGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

	void addUser(User user) {
		users.add(user);
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

public class WebSocketHandler extends TextWebSocketHandler {
	private static Map<WebSocketSession, User> userBySession = new HashMap<>();
	private static Map<UUID, GameSession> gameSessionByGameId = new HashMap<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		userBySession.put(session, new User(session, "Anonymous"));
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) {
		User user = userBySession.get(session);

		GameSession gameSession = gameSessionByGameId.get(user);
		if (gameSession != null) {
			var gameSessionState = readMessage(session, message, GameSessionState.class);
			gameSession.receiveMessage(user, gameSessionState);
		} else {
			switch (readMessage(session, message, GameLobbyState.class)) {
				case CreateGame createGame -> handleGameLobbyState(user, createGame);
				case JoinGame joinGame -> handleGameLobbyState(user, joinGame);
				default -> throw new IllegalStateException("Unexpected value: " + message.getPayload());
			}
		}
	}

	public static <T> T readMessage(WebSocketSession webSocketSession, TextMessage message, Class<T> clazz) {
		try {
			return new ObjectMapper().readValue(message.getPayload(), clazz);
		} catch (IOException e) {
			sendMessage(webSocketSession, "Invalid message: " + message.getPayload());
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

	public static <T> void sendMessageJson(WebSocketSession user, T object) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			user.sendMessage(new TextMessage(mapper.writeValueAsString(object)));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void handleGameLobbyState(User user, JoinGame joinGame) {
		var gameSession = gameSessionByGameId.get(joinGame.gameId());

		if (gameSession == null) {
			return;
		}
		if (gameSession.getNumberOfUsers() >= 2) {
			sendMessage(user.getSession(), "Game is full");
			return;
		}
		gameSession.addUser(user);
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
