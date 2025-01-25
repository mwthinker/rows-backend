package se.mwthinker.rows.protocol;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = Error.class, name = "ERROR"),

		@JsonSubTypes.Type(value = S2cCreatedGame.class, name = "S2C_CREATE_GAME"),
		@JsonSubTypes.Type(value = S2cJoinedGame.class, name = "S2C_JOIN_GAME"),
		@JsonSubTypes.Type(value = S2cUser.class, name = "S2C_USER"),

		@JsonSubTypes.Type(value = C2sMove.class, name = "C2S_GAME_MOVE")
})
public interface Message {
}
