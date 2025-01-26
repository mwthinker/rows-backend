package se.mwthinker.rows.game;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import se.mwthinker.rows.protocol.C2sCreateGame;
import se.mwthinker.rows.protocol.C2sGetRooms;
import se.mwthinker.rows.protocol.C2sJoinGame;
import se.mwthinker.rows.protocol.Error;
import se.mwthinker.rows.protocol.Message;
import se.mwthinker.rows.protocol.Room;
import se.mwthinker.rows.protocol.S2cCreatedGame;
import se.mwthinker.rows.protocol.S2cRooms;
import se.mwthinker.rows.protocol.S2cUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static se.mwthinker.rows.game.MessageUtil.readMessage;
import static se.mwthinker.rows.game.MessageUtil.sendMessage;

public class WebSocketHandler extends TextWebSocketHandler {
	private static final Map<WebSocketSession, User> userBySession = new HashMap<>();
	private static final Map<User, GameSession> gameSessionByUser = new HashMap<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		var user = new User(session);
		userBySession.put(session, user);
		sendMessage(user, new S2cUser(user.getUuid()));
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) {
		User user = userBySession.get(session);

		GameSession gameSession = gameSessionByUser.get(user);
		if (gameSession != null) {
			var gameSessionState = readMessage(user, message, Message.class);
			gameSession.receiveMessage(user, gameSessionState);
		} else {
			switch (readMessage(user, message, Message.class)) {
				case C2sCreateGame ignored -> handleCreateGame(user);
				case C2sJoinGame joinGame -> handleJoinGame(user, joinGame);
				case C2sGetRooms ignored -> handleGetRooms(user);
				default -> throw new IllegalStateException("Unexpected value: " + message.getPayload());
			}
		}
	}

	private void handleJoinGame(User user, C2sJoinGame joinGame) {
		gameSessionByUser.values().stream()
				.filter(gameSession -> gameSession.getGameId().equals(joinGame.gameId()))
				.findFirst().ifPresentOrElse(
						gameSession -> gameSession.tryToAddUser(user),
						() -> sendMessage(user, new Error("Game not found"))
				);
	}

	private void handleCreateGame(User user) {
		var gameSession = new GameSession(user, 5);
		sendMessage(user, new S2cCreatedGame(gameSession.getGameId()));
	}

	private void handleGetRooms(User user) {
		List<Room> rooms = gameSessionByUser.values().stream()
				.distinct()
				.map(GameSession::getRoom)
				.collect(Collectors.toList());

		user.sendToClient(new S2cRooms(rooms));
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus){
		var user = userBySession.get(session);
		// TODO! Handle game session

		userBySession.remove(session);
	}
}
