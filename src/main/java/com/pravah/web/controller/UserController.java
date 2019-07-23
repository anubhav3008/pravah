package com.pravah.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.pravah.web.entity.User;
import com.pravah.web.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import javax.ws.rs.PathParam;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired UserService UserService;
	@RequestMapping("")
	public @ResponseBody JsonNode getUsers() throws JsonProcessingException {
		return UserService.getAllUsers();
	}
	
	@RequestMapping(path="/add", method= {RequestMethod.POST})
	public @ResponseBody JsonNode addUser(@RequestBody User user  ) throws JsonProcessingException {
		return UserService.addUser(user);
	}

	@RequestMapping(path = "/contribution/byName/{name}")
	public @ResponseBody JsonNode getUserContribution(@PathVariable(value = "name") String name){
		return UserService.getUserContribution(name);
	}
}
