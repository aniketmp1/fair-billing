package com.edstem.model;

public class User {
	private String userId;
	private int noOfSessions;
	private long timeInSecs;
	
	
	public User(String userId, int noOfSessions, long timeInSecs) {
		super();
		this.userId = userId;
		this.noOfSessions = noOfSessions;
		this.timeInSecs = timeInSecs;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getNoOfSessions() {
		return noOfSessions;
	}
	public void setNoOfSessions(int noOfSessions) {
		this.noOfSessions = noOfSessions;
	}
	public long getTimeInSecs() {
		return timeInSecs;
	}
	public void setTimeInSecs(long timeInSecs) {
		this.timeInSecs = timeInSecs;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", noOfSessions=" + noOfSessions + ", timeInSecs=" + timeInSecs + "]";
	}
	
	
}
