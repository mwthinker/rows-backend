package se.mwthinker.rows.protocol;

import java.util.UUID;

public record S2cJoinedGame(UUID gameId) implements Message {
}
