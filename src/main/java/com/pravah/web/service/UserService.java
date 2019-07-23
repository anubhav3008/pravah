package com.pravah.web.service;

import com.pravah.web.aggregator.ContributionAggregator;
import com.pravah.web.dao.MeetingDao;
import com.pravah.web.dao.SpeechDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pravah.web.dao.UsersDao;
import com.pravah.web.entity.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
@Service
public class UserService {

	@Autowired UsersDao usersDao;
	@Autowired
	MeetingDao meetingDao;
	@Autowired
	SpeechDao speechDao;

	@Autowired
    ContributionAggregator contributionAggregator;
	ObjectMapper mapper = new ObjectMapper();
	public JsonNode getAllUsers() {
		ObjectNode response = mapper.createObjectNode();
		try {
			response.putPOJO("data", usersDao.getAllUsers());
			response.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("success", false);
			response.put("error", e.getMessage());
		}
		return response;
	}
	public JsonNode addUser(User user) {
		ObjectNode response = mapper.createObjectNode();
		try {
			 response.put("data",  mapper.writeValueAsString(usersDao.addUser(user)));
			response.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("success", false);
			response.put("error", e.getMessage());
		}
		return response;
	}

	public JsonNode getUserContribution(String name){
		ObjectNode response = mapper.createObjectNode();
		try {
			response.putPOJO("data", contributionAggregator.getContribution(meetingDao.getMeetings(name), speechDao.getSpeechByName(name), name));
			response.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("success", false);
			response.put("error", e.getMessage());
		}
		return response;
	}




}
