package com.scmd.socialmedia.exception;

public class UserNotFoundInGroupException extends RuntimeException {
	public UserNotFoundInGroupException(String message) {
		super(message);
	}
}