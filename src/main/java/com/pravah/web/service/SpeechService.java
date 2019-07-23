package com.pravah.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pravah.web.dao.SpeechDao;
import com.pravah.web.entity.Speech;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
@Service
public class SpeechService {
	
	@Autowired SpeechDao speechDao;
	ObjectMapper mapper = new ObjectMapper();

	public JsonNode addOrupdateSpeech(List<Speech> speeches) {
		ObjectNode response = mapper.createObjectNode();
		try {
			speechDao.addOrupdateSpeech(speeches, null);
			response.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("success", false);
			response.put("error", e.getMessage());
		}
		return response;
	}

	public JsonNode getSpeechByMeetingId(int id) {
		ObjectNode response = mapper.createObjectNode();
		try {
			response.putPOJO("data", speechDao.getSpeechByMeetingId(id));
			response.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("success", false);
			response.put("error", e.getMessage());
		}
		return response;
	}

}
