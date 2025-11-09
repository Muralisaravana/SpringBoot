package com.example.helloapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.helloapp.service.GreetingService;

@RestController
@RequestMapping("/hello")
public class HelloController {
	
	private final GreetingService greetingService;
	
	@Autowired
	public HelloController(GreetingService greetingService) {
		this.greetingService = greetingService;
	}
	
	@GetMapping("/{name}")
	public String sayHello(@PathVariable String name) {
		return greetingService.getGreeting(name);
	}

}
