package se.mwthinker.rows.game;

import se.mwthinker.rows.protocol.C2sMove;
import se.mwthinker.rows.protocol.Error;
import se.mwthinker.rows.protocol.Message;
import se.mwthinker.rows.protocol.ProtocolException;
import se.mwthinker.rows.protocol.S2cJoinedGame;
import se.mwthinker.rows.protocol.S2cRooms;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameSession {
	private final List<Player> players = new ArrayList<>();
	private Board board;
	private UUID gameId;

	public GameSession(UUID uuid, User user, int maxNumberInRow) {
		this.players.add(new Player(Piece.X, user));
		this.board = new Board(maxNumberInRow);
		gameId = uuid;
	}

	public UUID getGameId() {
		return gameId;
	}

	public void tryToAddUser(User user) {
		if (players.size() >= 2) {
			user.sendToClient(new Error("Game is full"));
			return;
		}
		Player player = new Player(Piece.O, user);
		players.add(player);
		sendToAllUsers(new S2cJoinedGame(player.getPiece()));
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
		players.forEach(u -> u.sendToClient(message));
	}

	public S2cRooms.Room getRoom() {
		return new S2cRooms.Room(gameId,
				players.stream()
						.map(player -> new S2cRooms.Room.Player(player.getPiece(), player.getUser().getUuid()))
						.toList()
		);
	}

	private static class Player {
		private final Piece piece;
		private final User user;

		public Player(Piece piece, User user) {
			this.piece = piece;
			this.user = user;
		}

		public Piece getPiece() {
			return piece;
		}

		public User getUser() {
			return user;
		}

		public void sendToClient(Message message) {
			user.sendToClient(message);
		}
	}

}
