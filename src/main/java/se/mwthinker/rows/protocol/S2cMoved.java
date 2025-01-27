package se.mwthinker.rows.protocol;

import se.mwthinker.rows.game.Piece;

import java.util.UUID;

public record S2cMoved(UUID gameId, Piece piece, int x, int y, String hash) implements Message {
}
