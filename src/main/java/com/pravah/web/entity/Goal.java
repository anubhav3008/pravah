package com.pravah.web.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Goal {

	private Integer id;
	private String userId;
	private String userName;
	private String projectName;
	@JsonFormat
			(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date date;
	private int meetingId;
	public int getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public Goal(int id, String userId, String userName, String projectName, Date date, int meetingId) {
		super();
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.projectName = projectName;
		this.date = date;
		this.meetingId = meetingId;
	}
	public Goal() {
		
	}
	
}
