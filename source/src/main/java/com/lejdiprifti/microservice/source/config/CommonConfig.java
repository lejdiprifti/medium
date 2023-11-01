package com.lejdiprifti.microservice.source.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

@EnableRetry
@Configuration
public class CommonConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
