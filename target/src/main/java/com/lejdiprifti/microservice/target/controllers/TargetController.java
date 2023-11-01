package com.lejdiprifti.microservice.target.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/target")
public class TargetController {

	private static int NUMBER_OF_ATTEMPS = 0;

	@GetMapping("/readiness")
	public String isDataReady() throws Exception {
		if (NUMBER_OF_ATTEMPS < 5) {
			NUMBER_OF_ATTEMPS++;
			throw new Exception("data is not ready");
		}
		log.info("data is ready");
		NUMBER_OF_ATTEMPS = 0;
		return "OK";
	}

}
