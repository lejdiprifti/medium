package com.medium.websocket.handlers;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatMessageHandler implements WebSocketHandler {

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("connection established");

	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		log.info("message arrived {}", message);
		session.sendMessage(new TextMessage("hello from plain websocket"));
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		log.error("transport error");

	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		log.info("connection clossed");

	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

}
