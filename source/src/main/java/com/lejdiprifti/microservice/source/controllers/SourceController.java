package com.lejdiprifti.microservice.source.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lejdiprifti.microservice.source.service.ExternalService;

@RestController
@RequestMapping("/source")
public class SourceController {

	@Autowired
	private ExternalService externalService;

	@PostMapping("/process-with-retry")
	public String processWithRetry() {
		return externalService.checkWithRetry();
	}

	@PostMapping("/process-with-while")
	public String processWithDoWhile() {
		return externalService.checkWithDoWhile();
	}
}
