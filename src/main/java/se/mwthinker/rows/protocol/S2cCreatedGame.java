package se.mwthinker.rows.protocol;

import java.util.UUID;

public record S2cCreatedGame(UUID gameId) implements Message {
}
