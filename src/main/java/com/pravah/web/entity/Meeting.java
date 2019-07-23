package com.pravah.web.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;


public class Meeting {
	public String getTime() {
		return time;
	}


	public Meeting(Integer id, String time, Date date, String ttmName, String ttmId, String grammarianName, String grammarianId, String ahCounterName, String ahCounterId, String tmodName, String tmodId, String timerName, String timerId, String geName, String geId, String theme, String venue, String clubName, String saaId, String saaName, String clubId, String presidentId, String presidentName, String vpeId, String vpeName) {
		this.id = id;
		this.time = time;
		this.date = date;
		this.ttmName = ttmName;
		this.ttmId = ttmId;
		this.grammarianName = grammarianName;
		this.grammarianId = grammarianId;
		this.ahCounterName = ahCounterName;
		this.ahCounterId = ahCounterId;
		this.tmodName = tmodName;
		this.tmodId = tmodId;
		this.timerName = timerName;
		this.timerId = timerId;
		this.geName = geName;
		this.geId = geId;
		this.theme = theme;
		this.venue = venue;
		this.clubName = clubName;
		this.saaId = saaId;
		this.saaName = saaName;
		this.clubId = clubId;
		this.presidentId = presidentId;
		this.presidentName = presidentName;
		this.vpeId = vpeId;
		this.vpeName = vpeName;
	}

	public void setTime(String time) {
		this.time = time;

	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	private Integer id;
	private String time;
	@JsonFormat
			(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date date;
	private String ttmName;
	private String ttmId;
	private String grammarianName;
	private String grammarianId;
	private String ahCounterName;
	private String ahCounterId;
	private String tmodName;
	private String tmodId;
	private String timerName;
	private String timerId;
	private String geName;
	private String geId;
	private String theme;
	private String venue;
	private String clubName;
	private String saaId;
	private String saaName;
	private String clubId;
	private String presidentId;
	private String presidentName;
	private String vpeId;
	private String vpeName;

	public String getClubName() {
		return clubName;
	}

	public void setClubName(String clubName) {
		this.clubName = clubName;
	}

	public String getSaaId() {
		return saaId;
	}

	public void setSaaId(String saaId) {
		this.saaId = saaId;
	}

	public String getSaaName() {
		return saaName;
	}

	public void setSaaName(String saaName) {
		this.saaName = saaName;
	}

	public String getClubId() {
		return clubId;
	}

	public void setClubId(String clubId) {
		this.clubId = clubId;
	}

	public String getPresidentId() {
		return presidentId;
	}

	public void setPresidentId(String presidentId) {
		this.presidentId = presidentId;
	}

	public String getPresidentName() {
		return presidentName;
	}

	public void setPresidentName(String presidentName) {
		this.presidentName = presidentName;
	}

	public String getVpeId() {
		return vpeId;
	}

	public void setVpeId(String vpeId) {
		this.vpeId = vpeId;
	}

	public String getVpeName() {
		return vpeName;
	}

	public void setVpeName(String vpeName) {
		this.vpeName = vpeName;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getTtmName() {
		return ttmName;
	}
	public void setTtmName(String ttmName) {
		this.ttmName = ttmName;
	}
	public String getTtmId() {
		return ttmId;
	}
	public void setTtmId(String ttmId) {
		this.ttmId = ttmId;
	}
	public String getGrammarianName() {
		return grammarianName;
	}
	public void setGrammarianName(String grammarianName) {
		this.grammarianName = grammarianName;
	}
	public String getGrammarianId() {
		return grammarianId;
	}
	public void setGrammarianId(String grammarianId) {
		this.grammarianId = grammarianId;
	}
	public String getAhCounterName() {
		return ahCounterName;
	}
	public void setAhCounterName(String ahCounterName) {
		this.ahCounterName = ahCounterName;
	}
	public String getAhCounterId() {
		return ahCounterId;
	}
	public void setAhCounterId(String ahCounterId) {
		this.ahCounterId = ahCounterId;
	}
	public String getTmodName() {
		return tmodName;
	}
	public void setTmodName(String tmodName) {
		this.tmodName = tmodName;
	}
	public String getTmodId() {
		return tmodId;
	}
	public void setTmodId(String tmodId) {
		this.tmodId = tmodId;
	}
	public String getTimerName() {
		return timerName;
	}
	public void setTimerName(String timerName) {
		this.timerName = timerName;
	}
	public String getTimerId() {
		return timerId;
	}
	public void setTimerId(String timerId) {
		this.timerId = timerId;
	}
	public String getGeName() {
		return geName;
	}
	public void setGeName(String geName) {
		this.geName = geName;
	}
	public String getGeId() {
		return geId;
	}
	public void setGeId(String geId) {
		this.geId = geId;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}

	public Meeting() {}
	 
}
