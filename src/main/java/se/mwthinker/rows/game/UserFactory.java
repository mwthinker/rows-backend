package se.mwthinker.rows.game;

import org.springframework.web.socket.WebSocketSession;

import java.util.UUID;

public class UserFactory {
	public User createUser(WebSocketSession handler) {
		return new User(handler, UUID.randomUUID());
	}
}
