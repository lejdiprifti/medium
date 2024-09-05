package com.lejdiprifti.websocket.client.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import jakarta.annotation.PostConstruct;

@Component
public class WebSocketProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketProcessor.class);

	@Value("${server.websocket.connection.url}")
	private String url;

	@PostConstruct
	public void init() {
		WebSocketClient client = new StandardWebSocketClient();
		WebSocketHandler handler = new WebSocketHandler() {

			@Override
			public void afterConnectionEstablished(WebSocketSession session) throws Exception {
				LOGGER.info("connected to server session");
			}

			@Override
			public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
				LOGGER.info("handling message from server session: {}", message.getPayload());
			}

			@Override
			public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
				LOGGER.info("error on server session");
			}

			@Override
			public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
				LOGGER.info("closed on server session");
			}

			@Override
			public boolean supportsPartialMessages() {
				return true;
			}

		};
		client.execute(handler, url);
	}
}
