package se.mwthinker.rows.protocol;

import java.util.UUID;

public record C2sGetGame(UUID gameId) implements Message {
}
