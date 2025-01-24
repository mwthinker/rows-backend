package se.mwthinker.rows.protocol;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = CreateGame.class, name = "CREATE_GAME"),
		@JsonSubTypes.Type(value = JoinGame.class, name = "JOIN_GAME")
})
public interface GameLobbyState {
}
