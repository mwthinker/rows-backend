package se.mwthinker.rows.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import se.mwthinker.rows.protocol.Error;
import se.mwthinker.rows.protocol.Message;
import se.mwthinker.rows.protocol.ProtocolException;

import java.io.IOException;

abstract class MessageUtil {
	private MessageUtil() {
		// Utility class
	}

	public static <T extends Message> T readMessage(User user, TextMessage message, Class<T> clazz) {
		try {
			return new ObjectMapper().readValue(message.getPayload(), clazz);
		} catch (IOException e) {
			sendMessage(user, new Error("Invalid message: " + message.getPayload()));
			throw new ProtocolException(e);
		}
	}

	public static <T extends Message> void sendMessage(User user, T object) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			user.getSession().sendMessage(new TextMessage(mapper.writeValueAsString(object)));
		} catch (IOException e) {
			throw new ProtocolException(e);
		}
	}
}
