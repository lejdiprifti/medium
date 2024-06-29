package com.lejdiprifti.tcpclient.ready;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.lejdiprifti.tcpclient.config.TcpClientConfig.TcpClientGateway;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class TcpClientReadyListener {

	private static final String SUCCESS = "SUCCESS";
	private final TcpClientGateway tcpClientGateway;

	@EventListener(ApplicationReadyEvent.class)
	public void start() {
		for (int i = 0; i < 10; i++) {
			byte[] response = tcpClientGateway.send(String.valueOf(i).getBytes());
			if (SUCCESS.equals(new String(response, StandardCharsets.UTF_8))) {
				log.info("message processed successfully");
			} else {
				log.error("message processing failed");
			}
		}
	}

}
