package se.mwthinker.rows.protocol;

import java.util.UUID;

/**
 * Sent by the server to the client when a game has been created.
 * @param gameId
 */
public record S2cCreatedGame(UUID gameId) implements Message {
}
