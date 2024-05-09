package com.lejdiprifti.zipkindemo.services.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lejdiprifti.zipkindemo.services.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

	private final RestTemplate restTemplate;

	@Override
	public void addOrder() {
		try {
			log.info("starting to add order");
			Thread.sleep(10000L);
			this.makePayment();
			log.info("finished adding order");
		} catch (InterruptedException e) {
			log.error("error adding order");
		}
	}

	private void makePayment() {
		ResponseEntity<String> resultResponse = restTemplate.postForEntity("http://localhost:8081/payment", null,
				String.class);
		log.info("result of payment is {}", resultResponse.getBody());
	}

}
