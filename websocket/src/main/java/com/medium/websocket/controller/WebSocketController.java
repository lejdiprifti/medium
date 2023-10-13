package com.medium.websocket.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.medium.websocket.beans.UserText;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class WebSocketController {

	private final Map<String, List<UserText>> chats = new HashMap<>();

	@MessageMapping("/chat/{chatId}")
	@SendTo("/topic/chat/{chatId}")
	public List<UserText> sendMessageWithWebsocket(@DestinationVariable String chatId,
			@Payload Message<UserText> message) {
		log.info("new message arrived in chat with id {}", chatId);
		List<UserText> messages = this.chats.getOrDefault(chatId, new ArrayList<UserText>());
		messages.add(message.getPayload());
		chats.put(chatId, messages);
		return messages;
	}
}
