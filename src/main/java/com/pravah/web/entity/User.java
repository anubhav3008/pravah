package com.pravah.web.entity;

public class User {
	public User() {}
	public User(String id, String name, boolean isActive, String emailId) {
		this.id = id;
		this.name = name;
		this.isActive = isActive;
		this.emailId=emailId;
	}
	
	private String id;
	private String name;
	private boolean isActive;
	private String emailId;

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

}
