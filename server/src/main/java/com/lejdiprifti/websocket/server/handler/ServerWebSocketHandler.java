package com.lejdiprifti.websocket.server.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class ServerWebSocketHandler implements WebSocketHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServerWebSocketHandler.class);

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		LOGGER.info("connection opened in session with id {}", session.getId());
		this.produce(session);
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		LOGGER.info("message arrived, no processing");
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		LOGGER.error("error in session with id {}", session.getId());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		LOGGER.info("connection closed in session with id {}", session.getId());
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	private void produce(WebSocketSession session) throws Exception {
		int counter = 0;
		while (session.isOpen()) {
			session.sendMessage(new TextMessage(String.format("Hello from server %s!", counter)));
			counter++;
			Thread.sleep(5000);
		}
	}

}
