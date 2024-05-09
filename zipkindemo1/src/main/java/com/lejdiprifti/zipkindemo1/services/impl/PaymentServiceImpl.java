package com.lejdiprifti.zipkindemo1.services.impl;

import org.springframework.stereotype.Service;

import com.lejdiprifti.zipkindemo1.services.PaymentService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

	@Override
	public String makePayment() {
		log.info("payment made successfully");
		return "OK";
	}

}
