package se.mwthinker.rows.protocol;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = Error.class, name = "ERROR"),

		@JsonSubTypes.Type(value = S2cCreatedGame.class, name = "S2C_CREATE_GAME"),
		@JsonSubTypes.Type(value = S2cJoinedGame.class, name = "S2C_JOIN_GAME"),
		@JsonSubTypes.Type(value = S2cUser.class, name = "S2C_USER"),
		@JsonSubTypes.Type(value = S2cMoved.class, name = "S2C_GAME_MOVE"),
		@JsonSubTypes.Type(value = S2cGames.class, name = "S2C_GAMES"),
		@JsonSubTypes.Type(value = S2cGame.class, name = "S2C_GAME"),

		@JsonSubTypes.Type(value = C2sMove.class, name = "C2S_GAME_MOVE"),
		@JsonSubTypes.Type(value = C2sGetGames.class, name = "C2S_GET_GAMES"),
		@JsonSubTypes.Type(value = C2sCreateGame.class, name = "C2S_CREATE_GAME"),
		@JsonSubTypes.Type(value = C2sJoinGame.class, name = "C2S_JOIN_GAME"),
		@JsonSubTypes.Type(value = C2sGetGame.class, name = "C2S_GET_GAME"),
})
public interface Message {
}
