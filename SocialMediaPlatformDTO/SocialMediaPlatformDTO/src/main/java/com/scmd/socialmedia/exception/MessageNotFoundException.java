package com.scmd.socialmedia.exception;

public class MessageNotFoundException extends RuntimeException{
	public MessageNotFoundException(String message) {
		super(message);
	}
}
