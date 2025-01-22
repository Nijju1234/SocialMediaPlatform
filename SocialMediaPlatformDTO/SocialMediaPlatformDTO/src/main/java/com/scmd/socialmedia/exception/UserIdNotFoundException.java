package com.scmd.socialmedia.exception;

public class UserIdNotFoundException extends RuntimeException {
	public UserIdNotFoundException(String message) {
		super(message);
	}

}