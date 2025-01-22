package com.scmd.socialmedia.dto;

public enum FriendsStatusDTO {
    PENDING("Pending", "Friendship request is pending."),
    ACCEPTED("Accepted", "Friendship has been accepted."),
    REJECTED("Rejected", "Friendship request was rejected.");

    private final String status;
    private final String message;

    // Constructor
    FriendsStatusDTO(String status, String message) {
        this.status = status;
        this.message = message;
    }

    // Getter for status
    public String getStatus() {
        return status;
    }

    // Getter for message
    public String getMessage() {
        return message;
    }

    // Optional: Override toString method to return status
    @Override
    public String toString() {
        return status;
    }
}
