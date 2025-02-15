package se.mwthinker.rows.protocol;

import se.mwthinker.rows.game.Piece;

public record S2cJoinedGame(Piece player) implements Message {
}
