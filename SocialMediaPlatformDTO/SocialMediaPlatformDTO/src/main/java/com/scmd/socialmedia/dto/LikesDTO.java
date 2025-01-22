package com.scmd.socialmedia.dto;

import jakarta.validation.constraints.NotNull;
import java.sql.Timestamp;

public class LikesDTO {

    private int likeID;

    @NotNull(message = "Post ID cannot be null.")
    private int postID;

    @NotNull(message = "User ID cannot be null.")
    private int userID;

    private Timestamp timestamp;

    public LikesDTO() {}

    public LikesDTO(int likeID, int postID, int userID, Timestamp timestamp) {
        this.likeID = likeID;
        this.postID = postID;
        this.userID = userID;
        this.timestamp = timestamp;
    }

    public int getLikeID() {
        return likeID;
    }

    public void setLikeID(int likeID) {
        this.likeID = likeID;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
