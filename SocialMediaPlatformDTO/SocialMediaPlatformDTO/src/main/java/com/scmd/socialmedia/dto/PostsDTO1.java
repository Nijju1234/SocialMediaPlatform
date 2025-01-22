package com.scmd.socialmedia.dto;


import java.sql.Timestamp;

public class PostsDTO1 {
  private Long id; // Add this line
  private int userID;
  private String content;
  private Timestamp timestamp;

  // No-argument constructor
  public PostsDTO1() {
  }

  public PostsDTO1(Long id, int userID, String content, Timestamp timestamp) {
      this.id = id; // Initialize postID
      this.userID = userID;
      this.content = content;
      this.timestamp = timestamp;
  }

  public Long getPostID() { // Corrected getter name
      return id;
  }

  public void setPostID(Long id) { // Corrected setter name
      this.id = id;
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