package se.mwthinker.rows.game;

import se.mwthinker.rows.protocol.C2sMove;
import se.mwthinker.rows.protocol.Error;
import se.mwthinker.rows.protocol.Message;
import se.mwthinker.rows.protocol.ProtocolException;
import se.mwthinker.rows.protocol.S2cJoinedGame;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class GameSession {
	private final List<User> users = new ArrayList<>();
	private Board board;
	private UUID gameId;

	public GameSession(User player1, int maxNumberInARow) {
		this.users.add(player1);
		this.board = new Board(maxNumberInARow);
		gameId = UUID.randomUUID();
	}

	public UUID getGameId() {
		return gameId;
	}

	public void tryToAddUser(User user) {
		if (users.size() >= 2) {
			user.sendToClient(new Error("Game is full"));
			return;
		}
		users.add(user);
		sendToAllUsers(new S2cJoinedGame(gameId));
	}

	public void receiveMessage(User user, Message message) {
		switch (message) {
			case C2sMove makeMove -> handleMove(user, makeMove);
			default -> throw new ProtocolException("Unexpected message: " + message);
		}
	}

	private void handleMove(User user, C2sMove makeMove) {
	}

	private void sendToAllUsers(Message message) {
		users.forEach(u -> u.sendToClient(message));
	}

}
