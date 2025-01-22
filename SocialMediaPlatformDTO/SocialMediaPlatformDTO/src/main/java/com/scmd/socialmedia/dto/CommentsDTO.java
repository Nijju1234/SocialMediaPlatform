package com.scmd.socialmedia.dto;
import java.time.LocalDateTime;
 
import com.fasterxml.jackson.annotation.JsonFormat;
 
public class CommentsDTO {
    private int commentID;
    private String commentText;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
 
    public CommentsDTO() {
		// TODO Auto-generated constructor stub
	}
 
	public CommentsDTO(int commentID, String commentText, LocalDateTime timestamp) {
	this.commentID=commentID;
	this.commentText=commentText;
	this.timestamp=timestamp;
	}
 
	// Getters and Setters
    public int getCommentID() {
        return commentID;
    }
 
    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }
 
    public String getCommentText() {
        return commentText;
    }
 
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
 
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
 
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}