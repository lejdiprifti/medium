package com.lejdiprifti.microservice.source.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExternalService {

	static final String URL = "http://localhost:8081/target/readiness";

	@Autowired
	private RestTemplate restTemplate;

	@Retryable(retryFor = Exception.class, maxAttempts = 10, backoff = @Backoff(delay = 1000))
	public String checkWithRetry() {
		HttpEntity<?> entity = new HttpEntity<Object>(null, null);
		ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
		return response.getBody();
	}

	public String checkWithDoWhile() {
		long timeToWait = 1000;
		int numRetry = 1;
		int maxAttempts = 10;
		boolean isDataReady = false;
		String readiness = null;
		do {
			log.info("tentative num: " + numRetry + " for getting the readiness of data from external service");
			try {
				HttpEntity<?> entity = new HttpEntity<Object>(null, null);
				ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
				readiness = response.getBody();
				isDataReady = true;
			} catch (Exception e) {
				try {
					Thread.sleep(timeToWait);
				} catch (InterruptedException exception) {
					log.error(e.getMessage());
				}
				numRetry++;
			}
		} while (!isDataReady && numRetry <= maxAttempts);
		return readiness;
	}
}
