package se.mwthinker.rows.game;

import se.mwthinker.rows.protocol.C2sCreateGame;
import se.mwthinker.rows.protocol.C2sGetGames;
import se.mwthinker.rows.protocol.C2sJoinGame;
import se.mwthinker.rows.protocol.Error;
import se.mwthinker.rows.protocol.Message;
import se.mwthinker.rows.protocol.ProtocolException;
import se.mwthinker.rows.protocol.S2cCreatedGame;
import se.mwthinker.rows.protocol.S2cGames;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserHandler {
	private final GameSessionFactory gameSessionFactory;

	private final Map<User, GameSession> gameSessionByUser = new HashMap<>();
	private final Map<UUID, GameSession> gameSessionByGameId = new HashMap<>();

	public UserHandler(GameSessionFactory gameSessionFactory) {
		this.gameSessionFactory = gameSessionFactory;
	}

	public void handleMessage(User user, Message message) {
		GameSession gameSession = gameSessionByUser.get(user);
		if (gameSession != null) {
			gameSession.receiveMessage(user, message);
		} else {
			switch (message) {
				case C2sCreateGame ignored -> handleCreateGame(user);
				case C2sJoinGame joinGame -> handleJoinGame(user, joinGame);
				case C2sGetGames ignored -> handleGetRooms(user);
				default -> throw new ProtocolException("Unexpected value: " + message);
			}
		}
	}

	private void handleJoinGame(User user, C2sJoinGame joinGame) {
		gameSessionByUser.values().stream()
				.filter(gameSession -> gameSession.getGameId().equals(joinGame.gameId()))
				.findFirst().ifPresentOrElse(
						gameSession -> gameSession.tryToAddUser(user),
						() -> user.sendToClient(new Error("Game not found"))
				);
	}

	private void handleCreateGame(User user) {
		if (gameSessionByUser.containsKey(user)) {
			user.sendToClient(new Error("Already in a game"));
			return;
		}
		var gameSession = gameSessionFactory.createGameSession(user, 5);
		gameSessionByUser.put(user, gameSession);
		gameSessionByGameId.put(gameSession.getGameId(), gameSession);
		user.sendToClient(new S2cCreatedGame(gameSession.getGameId()));
	}

	private void handleGetRooms(User user) {
		List<S2cGames.Game> games = gameSessionByUser.values().stream()
				.distinct()
				.map(GameSession::getRoom)
				.toList();

		user.sendToClient(new S2cGames(games));
	}
}
