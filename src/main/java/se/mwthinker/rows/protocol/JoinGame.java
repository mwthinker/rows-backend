package se.mwthinker.rows.protocol;

import java.util.UUID;

public record JoinGame(UUID gameId) implements GameLobbyState {
}
