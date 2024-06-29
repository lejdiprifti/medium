package com.lejdiprifti.tcpclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpOutboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractClientConnectionFactory;
import org.springframework.integration.ip.tcp.serializer.ByteArrayStxEtxSerializer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableIntegration
public class TcpClientConfig {

	private static final String MESSAGE_CHANNEL = "message-channel";

	private final TcpProperties tcpProperties;

	@Bean
	public AbstractClientConnectionFactory clientFactory() {
		AbstractClientConnectionFactory factory = new TcpNetClientRetryConnectionFactory(tcpProperties);
		factory.setSerializer(new ByteArrayStxEtxSerializer());
		factory.setDeserializer(new ByteArrayStxEtxSerializer());
		factory.setSingleUse(true);

		return factory;
	}

	@Bean
	@ServiceActivator(inputChannel = MESSAGE_CHANNEL)
	public TcpOutboundGateway outboundGateway(AbstractClientConnectionFactory clientFactory) {
		TcpOutboundGateway outboundGateway = new TcpOutboundGateway();
		outboundGateway.setConnectionFactory(clientFactory);
		outboundGateway.setLoggingEnabled(true);
		outboundGateway.setRequiresReply(true);

		return outboundGateway;
	}

	@MessagingGateway
	public interface TcpClientGateway {

		@Gateway(requestChannel = MESSAGE_CHANNEL)
		byte[] send(byte[] payload);
	}
}
