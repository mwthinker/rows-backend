package se.mwthinker.rows.protocol;

import se.mwthinker.rows.game.Piece;

import java.util.List;
import java.util.UUID;

public record S2cGames(List<Game> games) implements Message {

	public record Game(UUID gameId, List<Player> players) {

		public record Player(Piece piece, UUID id) {
		}
	}

}
