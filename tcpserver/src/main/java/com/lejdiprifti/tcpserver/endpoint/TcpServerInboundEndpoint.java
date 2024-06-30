package com.lejdiprifti.tcpserver.endpoint;

import java.nio.charset.StandardCharsets;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.Message;

import com.lejdiprifti.tcpserver.constants.TcpServerConstants;
import com.lejdiprifti.tcpserver.enums.ResultEnum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@MessageEndpoint
@Slf4j
@RequiredArgsConstructor
public class TcpServerInboundEndpoint {

	@ServiceActivator(inputChannel = TcpServerConstants.MESSAGE_CHANNEL, requiresReply = "true")
	public byte[] onMessage(Message<byte[]> message) {
		log.info("received message with connection id {}",
				message.getHeaders().get(IpHeaders.CONNECTION_ID));

		byte[] bytePayload = message.getPayload();

		String payloadString = new String(bytePayload, StandardCharsets.UTF_8);

		if (Integer.valueOf(payloadString) % 2 == 0) {
			return ResultEnum.ERROR.name().getBytes();
		}

		return ResultEnum.SUCCESS.name().getBytes();
	}
}
