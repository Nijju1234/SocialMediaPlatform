package com.scmd.socialmedia.exception;
 
public class CommentsByPostIdNotFoundException extends RuntimeException {
 
    public CommentsByPostIdNotFoundException(String message) {
        super(message);
    }
 
    public CommentsByPostIdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
 