package se.mwthinker.rows.protocol;

import se.mwthinker.rows.game.Piece;

import java.util.List;
import java.util.UUID;

public record S2cGame(UUID gameId, Board board, String gameHash) implements Message {

	record Board(int bestOf, Piece player, List<Cell> cells) {
		record Cell(int x, int y, Piece piece) {
		}
	}

}
