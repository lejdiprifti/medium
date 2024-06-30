package com.lejdiprifti.tcpserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.integration.ip.tcp.serializer.ByteArrayStxEtxSerializer;

import com.lejdiprifti.tcpserver.constants.TcpServerConstants;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableIntegration
public class TcpServerConfig {

	@Value("${tcp.server.port}")
	private int serverPort;

	@Bean
	public AbstractServerConnectionFactory serverFactory() {
		AbstractServerConnectionFactory factory = new TcpNetServerConnectionFactory(serverPort);
		factory.setSerializer(new ByteArrayStxEtxSerializer());
		factory.setDeserializer(new ByteArrayStxEtxSerializer());

		return factory;
	}

	@Bean
	public TcpInboundGateway inboundGateway(AbstractServerConnectionFactory serverFactory) {
		TcpInboundGateway inbound = new TcpInboundGateway();
		inbound.setConnectionFactory(serverFactory);
		inbound.setRequestChannelName(TcpServerConstants.MESSAGE_CHANNEL);
		inbound.setLoggingEnabled(true);

		return inbound;
	}

}