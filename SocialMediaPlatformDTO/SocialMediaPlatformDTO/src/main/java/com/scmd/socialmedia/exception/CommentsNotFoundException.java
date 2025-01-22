package com.scmd.socialmedia.exception;

public class CommentsNotFoundException extends RuntimeException {
    public CommentsNotFoundException(String message) {
        super(message);
    }
}