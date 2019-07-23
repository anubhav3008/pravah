package com.pravah.web.service;

import com.pravah.web.aggregator.LeaderBoardAggregator;
import com.pravah.web.dao.MeetingDao;
import com.pravah.web.dao.SpeechDao;
import com.pravah.web.entity.Meeting;
import com.pravah.web.entity.Speech;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaderBoardService {
    ObjectMapper mapper = new ObjectMapper();
    @Autowired  MeetingDao meetingDao;
    @Autowired  SpeechDao speechDao;

    @Autowired
    LeaderBoardAggregator leaderBoardAggregator;
    public JsonNode getLeaderBoardByName()  {

        ObjectNode response = mapper.createObjectNode();
        try {
            List<Meeting> meetings =  meetingDao.getAllMeetings();
            List<Speech> speeches =  speechDao.getAllSpeeches();
            response.putPOJO("data",leaderBoardAggregator.aggregatorMeetingAndSpeech(meetings,speeches) );
            response.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }
}
