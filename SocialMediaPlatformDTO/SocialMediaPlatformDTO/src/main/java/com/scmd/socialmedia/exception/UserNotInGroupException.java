package com.scmd.socialmedia.exception;

public class UserNotInGroupException extends RuntimeException {
	public UserNotInGroupException(String message) {
		super(message);
	}
}