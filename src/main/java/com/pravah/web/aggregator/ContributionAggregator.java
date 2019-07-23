package com.pravah.web.aggregator;

import com.pravah.web.dto.Contribution;
import com.pravah.web.entity.Meeting;
import com.pravah.web.entity.Speech;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContributionAggregator {


    public List<Contribution> getContribution(List<Meeting> meetings, List<Speech> speeches, String name) {
        List<Contribution> contributions = new ArrayList<>();
        contributions.addAll(getMeetingContributions(meetings,name));
        contributions.addAll(getSpeechContribution(speeches,name));
        List<Contribution> sortedContributions=contributions.stream().sorted(Comparator.comparing(Contribution::getDate).reversed()).
                collect(Collectors.toList());
        return sortedContributions;
    }

    private List<Contribution> getSpeechContribution(List<Speech> speeches, String name){
        List<Contribution> contributions = new ArrayList<>();
        for(Speech speech:  speeches){
            if(name.equals(speech.getSpeakerName())){
                Contribution contribution = new Contribution(speech.getDate(), "speaker", speech.getProjectName());
                contributions.add(contribution);
            }
            if(name.equals(speech.getEvaluatorName())){
                Contribution contribution = new Contribution(speech.getDate(), "evaluator", speech.getSpeakerName()+" - "+speech.getProjectName());
                contributions.add(contribution);
            }
        }
        return contributions;
    }

    private  List<Contribution> getMeetingContributions(List<Meeting> meetings, String name) {
        List<Contribution> contributions = new ArrayList<>();
        for (Meeting meeting : meetings) {
            if (name.equals(meeting.getTmodName())) {
                Contribution contribution = new Contribution(meeting.getDate(), "TMOD", meeting.getTheme());
                contributions.add(contribution);
            }
            if (name.equals(meeting.getAhCounterName())) {
                Contribution contribution = new Contribution(meeting.getDate(), "Ah-Counter", "");
                contributions.add(contribution);
            }
            if (name.equals(meeting.getGeName())) {
                Contribution contribution = new Contribution(meeting.getDate(), "General evaluator", "");
                contributions.add(contribution);
            }
            if (name.equals(meeting.getTimerName())) {
                Contribution contribution = new Contribution(meeting.getDate(), "timer", "");
                contributions.add(contribution);
            }
            if (name.equals(meeting.getGrammarianName())) {
                Contribution contribution = new Contribution(meeting.getDate(), "grammarian", "");
                contributions.add(contribution);
            }
            if (name.equals(meeting.getTtmName())) {
                Contribution contribution = new Contribution(meeting.getDate(), "TTM", "");
                contributions.add(contribution);
            }
        }
        return contributions;
    }
}