package com.scmd.socialmedia.exception;

public class PostsNotFoundException extends RuntimeException {
    public PostsNotFoundException(String message) {
        super(message);
    }
}