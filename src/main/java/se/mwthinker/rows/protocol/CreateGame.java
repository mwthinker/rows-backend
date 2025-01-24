package se.mwthinker.rows.protocol;

import java.util.UUID;

public record CreateGame(UUID gameId, UUID playerId) implements GameLobbyState {
}
