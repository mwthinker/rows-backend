package se.mwthinker.rows.game;

import org.springframework.web.socket.WebSocketSession;
import se.mwthinker.rows.protocol.Message;

import java.util.UUID;

public class User {
	private final WebSocketSession session;
	private final String username;
	private final UUID uuid;

	public User(WebSocketSession session) {
		this.session = session;
		this.username = "Anonymous";
		this.uuid = UUID.randomUUID();
	}

	public String getUsername() {
		return username;
	}

	public UUID getUuid() {
		return uuid;
	}

	public WebSocketSession getSession() {
		return session;
	}

	void sendToClient(Message message) {
		MessageUtil.sendMessage(this, message);
	}
}
