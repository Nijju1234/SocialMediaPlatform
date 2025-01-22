package com.scmd.socialmedia.dto;
 
 
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
 
import java.sql.Timestamp;
 
public class PostsDTO {
 
    private int postID;
    @NotNull(message = "User cannot be null")
    private UserDTOforPostDTO user;
 
    @NotBlank(message = "Content cannot be blank")
    @Size(max = 500, message = "Content must be less than or equal to 500 characters")
    private String content;
 
    @NotNull(message = "Timestamp cannot be null")
    @PastOrPresent(message = "Timestamp must be in the past or present")
    private Timestamp timestamp;
 
    public int getPostID() {
        return postID;
    }
 
    public void setPostID(int postID) {
        this.postID = postID;
    }
 
    public UserDTOforPostDTO getUser() {
        return user;
    }
 
    public void setUser(UserDTOforPostDTO user) {
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
}