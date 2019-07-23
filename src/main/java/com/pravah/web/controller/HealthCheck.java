package com.pravah.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HealthCheck {
	
	@RequestMapping("healthCheck")
	public @ResponseBody String healthCheck() {
		return "hello";
	}
	
	

}
