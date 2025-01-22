package com.scmd.socialmedia.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

//import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.NotNull;

public class MessagesDTO {

//	@NotNull(message = "Sender ID cannot be null.")
//
//    private int messageID;
//
//    private int senderID;
//
//    private int receiverID;
//
//    @NotEmpty(message = "MessageText cannot be empty")
//
//    private String messageText;
//
//    private Timestamp timestamp;
//
//    private int id;
//
//
//    public int getId() {
//
//		return id;
//
//	}
//
//	public void setId(int id) {
//
//		this.id = id;
//
//	}
//
//	// Constructors
//
    public MessagesDTO(int i, String string, String string2, String string3, Timestamp timestamp2) {}
//
    public MessagesDTO(int messageID, int senderID, int receiverID, String messageText, Timestamp timestamp, boolean is_deleted) {

        this.messageID = messageID;

        this.senderID = senderID;

        this.receiverID = receiverID;

        this.messageText = messageText;

        this.timestamp = timestamp;

    }
	
	@NotNull(message = "Sender ID cannot be null.")
    private int messageID;

    private int senderID;
    private int receiverID;

    @NotEmpty(message = "MessageText cannot be empty")
    private String messageText;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp timestamp;

    private boolean is_deleted;

    // Constructors, Getters, Setters

    // Default constructor
    public MessagesDTO() {}

//    public MessagesDTO(int messageID, int senderID, int receiverID, String messageText, Timestamp timestamp, boolean is_deleted) {
//        this.messageID = messageID;
//        this.senderID = senderID;
//        this.receiverID = receiverID;
//        this.messageText = messageText;
//        this.timestamp = timestamp;
//        this.is_deleted = is_deleted;
//    }

    // Getters and Setters

    public int getMessageID() {

        return messageID;

    }

    public void setMessageID(int messageID) {

        this.messageID = messageID;

    }

    public int getSenderID() {

        return senderID;

    }

    public void setSenderID(int senderID) {

        this.senderID = senderID;

    }

    public int getReceiverID() {

        return receiverID;

    }

    public void setReceiverID(int receiverID) {

        this.receiverID = receiverID;

    }

    public String getMessageText() {

        return messageText;

    }

    public void setMessageText(String messageText) {

        this.messageText = messageText;

    }

    public Timestamp getTimestamp() {

        return timestamp;

    }

    public void setTimestamp(Timestamp timestamp) {

        this.timestamp = timestamp;

    }


}
 