package se.mwthinker.rows.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import se.mwthinker.rows.game.GameSessionFactory;
import se.mwthinker.rows.game.UserFactory;
import se.mwthinker.rows.game.UserHandler;
import se.mwthinker.rows.game.WebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new WebSocketHandler(
				new UserFactory(),
				new UserHandler(new GameSessionFactory())
		), "/ws").setAllowedOrigins("*");
	}
}
