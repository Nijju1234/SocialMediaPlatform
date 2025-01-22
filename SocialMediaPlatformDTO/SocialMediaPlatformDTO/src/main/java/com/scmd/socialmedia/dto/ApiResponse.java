package com.scmd.socialmedia.dto;
 
import org.springframework.http.HttpStatus;
 
public class ApiResponse<T> { // <T> makes this class generic
    private String status;
    private String message;
    private T data; // The data can be any type, determined by T
    private HttpStatus httpStatus;
 
    public ApiResponse(String status, String message, T data, HttpStatus httpStatus) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.httpStatus = httpStatus;
    }
 
    // Getters and Setters
    public String getStatus() {
        return status;
    }
 
    public void setStatus(String status) {
        this.status = status;
    }
 
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }
 
    public T getData() {
        return data;
    }
 
    public void setData(T data) {
        this.data = data;
    }
 
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
 
    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}