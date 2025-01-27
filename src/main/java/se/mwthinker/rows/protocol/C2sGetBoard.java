package se.mwthinker.rows.protocol;

import java.util.UUID;

public record C2sGetBoard(UUID gameId) implements Message {
}
