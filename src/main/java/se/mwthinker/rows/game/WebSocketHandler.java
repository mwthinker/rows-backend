package se.mwthinker.rows.game;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import se.mwthinker.rows.protocol.Message;
import se.mwthinker.rows.protocol.S2cUser;

import java.util.HashMap;
import java.util.Map;

import static se.mwthinker.rows.game.MessageUtil.readMessage;

public class WebSocketHandler extends TextWebSocketHandler {
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
		// TODO! Handle game session

		userBySession.remove(session);
	}
}
