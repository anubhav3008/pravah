package com.pravah.web.aggregator;

import com.pravah.web.dto.LeaderBoard;
import com.pravah.web.entity.Meeting;
import com.pravah.web.entity.Speech;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LeaderBoardAggregator {

    public List<LeaderBoard> aggregatorMeetingAndSpeech(List<Meeting> meetings, List<Speech> speeches) {
        Map<String, LeaderBoard> leaderBoardMap = new HashMap<>();
        for (Meeting meeting : meetings) {
            getAndIncrementTmod(leaderBoardMap, meeting);
            getAndIncrementTtm(leaderBoardMap, meeting);
            getAndIncrementGe(leaderBoardMap, meeting);
            getAndIncrementTimer(leaderBoardMap, meeting);
            getAndIncrementAhCounter(leaderBoardMap, meeting);
            getAndIncrementGrammarian(leaderBoardMap, meeting);
        }
        for(Speech speech :  speeches){
            getAndIncrementSpeaker(leaderBoardMap,speech);
            getAndIncrementEvaluator(leaderBoardMap,speech);
        }
        return leaderBoardMap.keySet().stream().filter(s -> s!=null && !s.equals("")).map(s -> leaderBoardMap.get(s)).
                sorted(Comparator.comparing(LeaderBoard::getTotal).reversed()).
                collect(Collectors.toList());
    }

    private void getAndIncrementSpeaker(Map<String, LeaderBoard> leaderBoardMap, Speech speech){
        if(Objects.nonNull(speech)){
            if(leaderBoardMap.containsKey(speech.getSpeakerName())){
                LeaderBoard leaderBoard =  leaderBoardMap.get(speech.getSpeakerName());
                leaderBoard.setSpeech(leaderBoard.getSpeech() + 1);
                leaderBoard.setTotal(leaderBoard.getTotal()+1);
            }else {
                LeaderBoard leaderBoard = new LeaderBoard();
                leaderBoard.setName(speech.getSpeakerName());
                leaderBoard.setSpeech(1);
                leaderBoard.setTotal(leaderBoard.getTotal()+1);
                leaderBoardMap.put(speech.getSpeakerName(), leaderBoard);
            }
        }
    }
    private void getAndIncrementEvaluator(Map<String, LeaderBoard> leaderBoardMap, Speech speech){
        if(Objects.nonNull(speech)){
            if(leaderBoardMap.containsKey(speech.getEvaluatorName())){
                LeaderBoard leaderBoard =  leaderBoardMap.get(speech.getEvaluatorName());
                leaderBoard.setEvaluator(leaderBoard.getEvaluator() + 1);
                leaderBoard.setTotal(leaderBoard.getTotal()+1);
            }else {
                LeaderBoard leaderBoard = new LeaderBoard();
                leaderBoard.setName(speech.getEvaluatorName());
                leaderBoard.setEvaluator(1);
                leaderBoard.setTotal(leaderBoard.getTotal()+1);
                leaderBoardMap.put(speech.getEvaluatorName(), leaderBoard);
            }
        }
    }


    private void getAndIncrementGrammarian(Map<String, LeaderBoard> leaderBoardMap, Meeting meeting) {
        if (Objects.nonNull(meeting.getGrammarianName())) {
            if (leaderBoardMap.containsKey(meeting.getGrammarianName())) {
                LeaderBoard leaderBoard = leaderBoardMap.get(meeting.getGrammarianName());
                leaderBoard.setGrammarian(leaderBoard.getGrammarian() + 1);
                leaderBoard.setTotal(leaderBoard.getTotal()+1);
            } else {
                LeaderBoard leaderBoard = new LeaderBoard();
                leaderBoard.setName(meeting.getGrammarianName());
                leaderBoard.setGrammarian(1);
                leaderBoard.setTotal(leaderBoard.getTotal()+1);
                leaderBoardMap.put(meeting.getGrammarianName(), leaderBoard);
            }
        }
    }

    private void getAndIncrementAhCounter(Map<String, LeaderBoard> leaderBoardMap, Meeting meeting) {
        if (Objects.nonNull(meeting.getAhCounterName())) {
            if (leaderBoardMap.containsKey(meeting.getAhCounterName())) {
                LeaderBoard leaderBoard = leaderBoardMap.get(meeting.getAhCounterName());
                leaderBoard.setAhCounter(leaderBoard.getAhCounter() + 1);
                leaderBoard.setTotal(leaderBoard.getTotal()+1);
            } else {
                LeaderBoard leaderBoard = new LeaderBoard();
                leaderBoard.setName(meeting.getAhCounterName());
                leaderBoard.setAhCounter(1);
                leaderBoard.setTotal(leaderBoard.getTotal()+1);
                leaderBoardMap.put(meeting.getAhCounterName(), leaderBoard);
            }
        }
    }

    private void getAndIncrementTimer(Map<String, LeaderBoard> leaderBoardMap, Meeting meeting) {
        if (Objects.nonNull(meeting.getTimerName())) {
            if (leaderBoardMap.containsKey(meeting.getTimerName())) {
                LeaderBoard leaderBoard = leaderBoardMap.get(meeting.getTimerName());
                leaderBoard.setTotal(leaderBoard.getTotal()+1);
                leaderBoard.setTimer(leaderBoard.getTimer() + 1);
            } else {
                LeaderBoard leaderBoard = new LeaderBoard();
                leaderBoard.setName(meeting.getTimerName());
                leaderBoard.setTimer(1);
                leaderBoard.setTotal(leaderBoard.getTotal()+1);
                leaderBoardMap.put(meeting.getTimerName(), leaderBoard);
            }
        }
    }

    private void getAndIncrementGe(Map<String, LeaderBoard> leaderBoardMap, Meeting meeting) {
        if (Objects.nonNull(meeting.getGeName())) {
            if (leaderBoardMap.containsKey(meeting.getGeName())) {
                LeaderBoard leaderBoard = leaderBoardMap.get(meeting.getGeName());
                leaderBoard.setTotal(leaderBoard.getTotal()+1);
                leaderBoard.setGeneralEvaluator(leaderBoard.getGeneralEvaluator() + 1);
            } else {
                LeaderBoard leaderBoard = new LeaderBoard();
                leaderBoard.setName(meeting.getGeName());
                leaderBoard.setGeneralEvaluator(1);
                leaderBoard.setTotal(leaderBoard.getTotal()+1);
                leaderBoardMap.put(meeting.getGeName(), leaderBoard);
            }
        }
    }

    private void getAndIncrementTtm(Map<String, LeaderBoard> leaderBoardMap, Meeting meeting) {
        if (Objects.nonNull(meeting.getTtmName())) {
            if (leaderBoardMap.containsKey(meeting.getTtmName())) {
                LeaderBoard leaderBoard = leaderBoardMap.get(meeting.getTtmName());
                leaderBoard.setTotal(leaderBoard.getTotal()+1);
                leaderBoard.setTtm(leaderBoard.getTtm() + 1);
            } else {
                LeaderBoard leaderBoard = new LeaderBoard();
                leaderBoard.setName(meeting.getTtmName());
                leaderBoard.setTotal(leaderBoard.getTotal()+1);
                leaderBoard.setTtm(1);
                leaderBoardMap.put(meeting.getTtmName(), leaderBoard);
            }
        }
    }

    private void getAndIncrementTmod(Map<String, LeaderBoard> leaderBoardMap, Meeting meeting) {
        if (Objects.nonNull(meeting.getTmodName())) {
            if (leaderBoardMap.containsKey(meeting.getTmodName())) {
                LeaderBoard leaderBoard = leaderBoardMap.get(meeting.getTmodName());
                leaderBoard.setTotal(leaderBoard.getTotal()+1);
                leaderBoard.setTmod(leaderBoard.getTmod() + 1);
            } else {
                LeaderBoard leaderBoard = new LeaderBoard();
                leaderBoard.setName(meeting.getTmodName());
                leaderBoard.setTmod(1);
                leaderBoard.setTotal(leaderBoard.getTotal()+1);
                leaderBoardMap.put(meeting.getTmodName(), leaderBoard);
            }
        }
    }
}
