package com.pravah.web.entity;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Speech {
	private Integer id;
	private int meetingId;
	private String projectName;
	private String speakerName;
	private String speakerId;
	private String evaluatorName;
	private String evaluatorId;
	@JsonFormat
			(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date date;
	private double timeMin;
	private double timeMax;
		
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getSpeakerName() {
		return speakerName;
	}
	public void setSpeakerName(String speakerName) {
		this.speakerName = speakerName;
	}
	public String getSpeakerId() {
		return speakerId;
	}
	public void setSpeakerId(String speakerId) {
		this.speakerId = speakerId;
	}
	public String getEvaluatorName() {
		return evaluatorName;
	}
	public void setEvaluatorName(String evaluatorName) {
		this.evaluatorName = evaluatorName;
	}
	public String getEvaluatorId() {
		return evaluatorId;
	}
	public void setEvaluatorId(String evaluatorId) {
		this.evaluatorId = evaluatorId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getTimeMin() {
		return timeMin;
	}
	public void setTimeMin(double timeMin) {
		this.timeMin = timeMin;
	}
	public double getTimeMax() {
		return timeMax;
	}
	public void setTimeMax(double timeMax) {
		this.timeMax = timeMax;
	}
	public Speech(int id, int meetingId, String projectName, String speakerName, String speakerId, String evaluatorName,
			String evaluatorId, Date date, double timeMin, double timeMax) {
		super();
		this.id = id;
		this.meetingId = meetingId;
		this.projectName = projectName;
		this.speakerName = speakerName;
		this.speakerId = speakerId;
		this.evaluatorName = evaluatorName;
		this.evaluatorId = evaluatorId;
		this.date = date;
		this.timeMin = timeMin;
		this.timeMax = timeMax;
	}
	public Speech() {}

}
