package com.scmd.socialmedia.dto;
 
import java.sql.Timestamp;
 
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Positive;
 
public class NotificationsDTO {
 
    @Positive(message = "Notification ID must be a positive number")

    private int notificationID;
 
    @Positive(message = "User ID must be a positive number")

    private int userID; // User reference as ID
 
    @NotBlank(message = "Content cannot be empty")

    private String content;
 
    @NotNull(message = "Timestamp cannot be null")

    private Timestamp timestamp;
 
    // Constructors

    public NotificationsDTO() {}
 
    public NotificationsDTO(int notificationID, int userID, String content, Timestamp timestamp) {

        this.notificationID = notificationID;

        this.userID = userID;

        this.content = content;

        this.timestamp = timestamp;

    }
 
    // Getters and Setters

    public int getNotificationID() {

        return notificationID;

    }
 
    public void setNotificationID(int notificationID) {

        this.notificationID = notificationID;

    }
 
    public int getUserID() {

        return userID;

    }
 
    public void setUserID(int userID) {

        this.userID = userID;

    }
 
    public String getContent() {

        return content;

    }
 
    public void setContent(String content) {

        this.content = content;

    }
 
    public Timestamp getTimestamp() {

        return timestamp;

    }
 
    public void setTimestamp(Timestamp timestamp) {

        this.timestamp = timestamp;

    }

}
 
 