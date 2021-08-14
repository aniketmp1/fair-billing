package com.edstem.model;

import java.time.LocalTime;

import com.edstem.enums.SessionState;

public class Session {
	private LocalTime sessionTime;
	private String userId;
	private SessionState sessionState;
	
	
	public Session(LocalTime sessionTime, String userId, SessionState sessionState) {
		super();
		this.sessionTime = sessionTime;
		this.userId = userId;
		this.sessionState = sessionState;
	}
	public LocalTime getSessionTime() {
		return sessionTime;
	}
	public void setSessionTime(LocalTime sessionTime) {
		this.sessionTime = sessionTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public SessionState getSessionState() {
		return sessionState;
	}
	public void setSessionState(SessionState sessionState) {
		this.sessionState = sessionState;
	}
	@Override
	public String toString() {
		return "Session [sessionTime=" + sessionTime + ", userId=" + userId + ", sessionState=" + sessionState + "]";
	}
	
}
