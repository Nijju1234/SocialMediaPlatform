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

@Entity
@Table(name = "Likes")
public class Likes 
{
	@Id
	@Column(name = "likeID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int likeID;
	@ManyToOne
	@JoinColumn(name = "postID")
	private Posts post;
	@ManyToOne
	@JoinColumn(name = "userID")
	private Users user;
	@CreationTimestamp
	@Column(name = "timestamp", columnDefinition = "TIMESTAMP")
	private Timestamp timestamp;

	public Likes() {}

	public Likes(int likeID, Timestamp timestamp) 
	{
		this.likeID = likeID;
		this.timestamp = timestamp;
	}

	public int getLikeID() 
	{
		return likeID;
	}

	public void setLikeID(int likeID) 
	{
		this.likeID = likeID;
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

	public Timestamp getTimestamp() 
	{
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) 
	{
		this.timestamp = timestamp;
	}

}
