package com.dokkaebi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		// '/sub'으로 시작하는 경로는 브로커로 향하도록 설정.
		config.enableSimpleBroker("/sub");
		// '/pub'으로 시작하는 경로는 MessageMapping 어노테이션으로 향하도록 설정.
		config.setApplicationDestinationPrefixes("/pub");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// 커넥션을 맺는 경로 설정. 엔드포인트
		// ws://localhost:8080/dokkaebi/websocket-endpoint
		registry.addEndpoint("/websocket-endpoint")
//				.setAllowedOrigins("http://70.12.246.156:3000","http://localhost:3000", "*")
				.setAllowedOriginPatterns("*")
				.withSockJS()
				.setClientLibraryUrl("https://cdn.jsdelivr.net/sockjs/1.6.1/sockjs.min.js");
	}

}
