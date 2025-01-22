package com.scmd.socialmedia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FriendNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleFriendNotFoundException(FriendNotFoundException ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "failure");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MessagesNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleMessagesNotFoundException(MessagesNotFoundException ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "failure");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFriendsException.class)
    public ResponseEntity<Map<String, Object>> handleNotFriendsException(NotFriendsException ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "failure");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "failure");
        response.put("message", "Invalid input: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "failure");
        response.put("message", "An unexpected error occurred: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
