package se.mwthinker.rows.protocol;

import se.mwthinker.rows.game.Piece;

import java.util.List;
import java.util.UUID;

public record S2cRooms(List<Room> rooms) implements Message {

	public record Room(UUID gameId, List<Player> players) {

		public record Player(Piece piece, UUID id) {
		}
	}

}
