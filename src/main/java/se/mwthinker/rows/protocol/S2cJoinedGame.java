package se.mwthinker.rows.protocol;

import se.mwthinker.rows.game.Piece;

import java.util.UUID;

public record S2cJoinedGame(Piece player) implements Message {
}
