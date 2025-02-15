package se.mwthinker.rows.game;

import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.slf4j.Logger;
import se.mwthinker.rows.protocol.Message;
import se.mwthinker.rows.protocol.S2cUser;

import java.util.HashMap;
import java.util.Map;

import static se.mwthinker.rows.game.MessageUtil.readMessage;

public class WebSocketHandler extends TextWebSocketHandler {
	private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);
	private final UserFactory userFactory;
	private final UserHandler userHandler;

	private static final Map<WebSocketSession, User> userBySession = new HashMap<>();

	public WebSocketHandler(UserFactory userFactory, UserHandler userHandler) {
		this.userFactory = userFactory;
		this.userHandler = userHandler;
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		var user = userFactory.createUser(session);
		userBySession.put(session, user);

		logger.info("User {} connected", user.getUuid());
		user.sendToClient(new S2cUser(user.getUuid()));
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) {
		User user = userBySession.get(session);
		userHandler.handleMessage(user, readMessage(user, message, Message.class));
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus){
		var user = userBySession.get(session);

		logger.info("User {} disconnected", user.getUuid());

		// TODO! Handle game session

		userBySession.remove(session);
	}
}
