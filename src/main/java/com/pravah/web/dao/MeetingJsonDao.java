package com.pravah.web.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import com.pravah.web.entity.Goal;
import com.pravah.web.entity.Meeting;
import com.pravah.web.entity.Speech;
import org.jdbi.v3.core.extension.NoSuchExtensionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
@Service
public class  MeetingJsonDao {

	@Autowired SpeechDao speechDao;
	@Autowired MeetingDao meetingDao;
	@Autowired GoalDao goalDao;
	ObjectMapper mapper = new ObjectMapper();


	public JsonNode getMeetingJson(int meetingId) throws  Exception {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode meetingJson=mapper.createObjectNode();
		CompletableFuture<Void> meetingFuture=  CompletableFuture.runAsync(()->  {
			try {
				meetingJson.putPOJO("meeting", meetingDao.getMeeting(meetingId));
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		});
		CompletableFuture<Void> speechFuture=  CompletableFuture.runAsync(()->  {
			try {
				meetingJson.putPOJO("speech", speechDao.getSpeechByMeetingId(meetingId));
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		});
		CompletableFuture<Void> goalFuture=  CompletableFuture.runAsync(()->  {
			try {
				meetingJson.putPOJO("goal", goalDao.getGoalByMeetingId(meetingId));
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		});
		meetingFuture.get();
		speechFuture.get();
		goalFuture.get();
		return meetingJson;
	}

	public void addMeetingJSON(JsonNode meetingJson) throws NoSuchExtensionException, Exception {
		CompletableFuture<Void> meeting=  CompletableFuture.runAsync(()->{
			try {
				addMeeting(meetingJson);
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		});
		CompletableFuture<Void> speeches =  CompletableFuture.runAsync(()->{
			try {
				addOrupdateSpeeches(meetingJson);
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		});
		CompletableFuture<Void> goals=  CompletableFuture.runAsync(()->{
			try {
				addOrupdateGoals(meetingJson);
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		});

		meeting.get();
		speeches.get();
		goals.get();
	}
	private void addMeeting(JsonNode meetingJson) throws NoSuchExtensionException, Exception {

		JsonNode meeting = meetingJson.get("meeting");
		if(Objects.nonNull(meeting)) {
			Meeting meetingDO= mapper.treeToValue(meeting, Meeting.class);
			meetingDao.addOrUpdateMeeting(meetingDO);

		}
	}

	private void addOrupdateSpeeches(JsonNode meetingJson) throws NoSuchExtensionException, Exception {
		JsonNode speech = meetingJson.get("speech");
		List<Speech> speechDOs = new ArrayList<>();
		if(Objects.nonNull(speech)) {
			speechDOs= Arrays.asList(mapper.treeToValue(speech, Speech[].class));
			if(Objects.nonNull(speechDOs)) {
				speechDOs.stream().forEach(speech1 -> speech1.setMeetingId(meetingJson.get("meeting").get("id").asInt()));

			}
		}
		speechDao.addOrupdateSpeech(speechDOs, meetingJson.get("meeting").get("id").asInt());

	}

	private void addOrupdateGoals(JsonNode meetingJson) throws NoSuchExtensionException, Exception {
		JsonNode goal = meetingJson.get("goal");
		List<Goal> goalDOs =  new ArrayList<>();
		if(Objects.nonNull(goal)) {
			goalDOs= Arrays.asList( mapper.treeToValue(goal, Goal[].class));
			if(Objects.nonNull(goalDOs))
				goalDOs.stream().forEach(goal1 -> goal1.setMeetingId(meetingJson.get("meeting").get("id").asInt()));

		}
		goalDao.addorUpdateGoal(goalDOs,meetingJson.get("meeting").get("id").asInt());


	}
}
