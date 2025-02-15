package se.mwthinker.rows.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import se.mwthinker.rows.protocol.Error;
import se.mwthinker.rows.protocol.Message;
import se.mwthinker.rows.protocol.ProtocolException;

import java.io.IOException;

abstract class MessageUtil {

	private static final Logger logger = LoggerFactory.getLogger(MessageUtil.class);
	private MessageUtil() {
		// Utility class
	}

	public static <T extends Message> T readMessage(User user, TextMessage message, Class<T> clazz) {
		try {
			logger.info("User {} received message: {}", user.getUuid(), message.getPayload());
			return new ObjectMapper().readValue(message.getPayload(), clazz);
		} catch (IOException e) {
			sendMessage(user, new Error("Invalid message: " + message.getPayload()));
			throw new ProtocolException(e);
		}
	}

	public static <T extends Message> void sendMessage(User user, T object) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			var payload = mapper.writeValueAsString(object);
			logger.info("User {} sent message: {}", user.getUuid(), payload);
			user.getSession().sendMessage(new TextMessage(payload));
		} catch (IOException e) {
			throw new ProtocolException(e);
		}
	}
}
