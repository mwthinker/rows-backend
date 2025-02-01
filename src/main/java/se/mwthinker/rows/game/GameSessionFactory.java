package se.mwthinker.rows.game;

import java.util.UUID;

public class GameSessionFactory {

	public GameSession createGameSession(User user, int maxNumberInRow) {
		return new GameSession(UUID.randomUUID(), user, maxNumberInRow);
	}
}
