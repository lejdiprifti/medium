package com.lejdiprifti.tcpclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class TcpProperties {

	@Value("${tcp.server.host}")
	private String serverHost;

	@Value("${tcp.server.port}")
	private int serverPort;

	@Value("${connection.retry.interval}")
	private int connectionRetryInterval;

}
