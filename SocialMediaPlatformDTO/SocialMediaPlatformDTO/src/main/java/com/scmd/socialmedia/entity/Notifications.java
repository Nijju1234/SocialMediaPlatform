package com.scmd.socialmedia.entity;

import java.sql.Timestamp;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.PrePersist;

@Entity
@Table(name = "Notifications")
public class Notifications {

    @Id
    @Column(name="notificationID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int notificationID;

    @ManyToOne
    @JoinColumn(name = "userID")
    private Users user;
    
    @Column(name="content")
    private String content;
    
    @CreationTimestamp
    @Column(name="timestamp", columnDefinition = "TIMESTAMP")
    private Timestamp timestamp;
    
    // Soft delete flag
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    // Default constructor
    public Notifications() {}

    // Constructor
    public Notifications(int notificationID, String content, Timestamp timestamp) {
        this.notificationID = notificationID;
        this.content = content;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
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

    // Getter and setter for isDeleted
    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    // Optional: Add a method to mark as deleted (can be called from service layer)
    public void markAsDeleted() {
        this.isDeleted = true;
    }
}
