package com.scmd.socialmedia.entity;

public enum FriendsStatus {
	accepted, 
	pending;
	private String message;
	private FriendsStatus(String message) {
		this.message = message;
	}
 
	public String getMessage() {
		return message;
	}
 
	public void setMessage(String message) {
		this.message = message;
	}
 
	private FriendsStatus() {
	}
 
}
