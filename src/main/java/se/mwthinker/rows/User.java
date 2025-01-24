package se.mwthinker.rows;

import org.springframework.web.socket.WebSocketSession;

import java.util.UUID;

public class User {
	private final WebSocketSession session;
	private final String username;
	private final UUID uuid;

	public User(WebSocketSession session, String username) {
		this.session = session;
		this.username = username;
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
}
