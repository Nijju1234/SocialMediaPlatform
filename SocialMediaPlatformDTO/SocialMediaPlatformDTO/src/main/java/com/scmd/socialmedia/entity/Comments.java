package com.scmd.socialmedia.entity;
 
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
 
import com.fasterxml.jackson.annotation.JsonProperty;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
 
@Entity
@Table(name = "Comments")
public class Comments 
{
	@Id
    @Column(name="commentID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentID;
    @ManyToOne
    @JoinColumn(name = "postID")
    private Posts post;
 
    @ManyToOne
    @JoinColumn(name = "userID")
    private Users user;
    @Column(name="comment_text")
    private String commentText;
    @CreationTimestamp
    @Column(name="timestamp", updatable = false)
    private LocalDateTime timestamp;

 
    @Column(name = "is_deleted") 
    private boolean isDeleted = false;
 
    public Comments() {}
    public Comments(int commentID, String commentText, LocalDateTime timestamp) 
    {
		this.commentID = commentID;
		this.commentText = commentText;
		this.timestamp = timestamp;
	}
	public int getCommentID() 
	{
		return commentID;
	}
	public void setCommentID(int commentID) 
	{
		this.commentID = commentID;
	}
	public Posts getPost() 
	{
		return post;
	}
	public void setPost(Posts post) 
	{
		this.post = post;
	}
	public Users getUser() 
	{
		return user;
	}
	public void setUser(Users user) 
	{
		this.user = user;
	}
	public String getCommentText() 
	{
		return commentText;
	}
	public void setCommentText(String commentText) 
	{
		this.commentText = commentText;
	}
	public LocalDateTime getTimestamp() 
	{
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) 
	{
		this.timestamp = timestamp;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
