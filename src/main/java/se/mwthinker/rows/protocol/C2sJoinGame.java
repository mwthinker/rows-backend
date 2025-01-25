package se.mwthinker.rows.protocol;

import java.util.UUID;

public record C2sJoinGame(UUID gameId) implements Message {
}
