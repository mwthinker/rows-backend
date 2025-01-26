package se.mwthinker.rows.protocol;

import java.util.List;
import java.util.UUID;

public record Room(UUID gameId, List<Player> players) {
}
