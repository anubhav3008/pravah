package com.pravah.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pravah.web.service.MeetingService;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/meetings")
public class MeetingController {
	
	@Autowired MeetingService meetingService;
	@RequestMapping("")
	public @ResponseBody JsonNode getAllMeetings() {
		return meetingService.getAllMeetings();
	}
	
	@RequestMapping("/{id}")
	public @ResponseBody JsonNode getMeetingById(@PathVariable("id") int id) {
		return meetingService.getMeeting(id);
	}
	
	@RequestMapping(path="/addOrUpdate", method= {RequestMethod.POST})
	public @ResponseBody JsonNode addOrUpdate(@RequestBody JsonNode meeting) {
		return meetingService.addOrUpdate(meeting);
	}
}
