package com.scmd.socialmedia.exception;


public class NotificationNotFoundException extends RuntimeException{
	public NotificationNotFoundException(String message) {
		super(message);
	}
}