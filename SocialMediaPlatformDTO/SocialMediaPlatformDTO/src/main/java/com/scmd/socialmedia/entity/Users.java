package com.scmd.socialmedia.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class Users {
	
	
		@Id
		@Column(name = "userid")
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
		@SequenceGenerator(name = "user_seq", sequenceName = "users_seq", allocationSize = 1)

		private int userID;
		@Column(name = "username", nullable = false)
		private String username;
		@Column(name = "email", nullable = false)
		private String email;
		@Column(name = "password", nullable = false)
		private String password;
		
		@Column(name = "profile_picture")
		private String profilePicture;
		
//		change
		@ManyToMany(mappedBy = "users")
		private Set<Groups> groups = new HashSet<>();


		@JsonIgnore
		private boolean isDeleted = false; // Flag for soft deletion
		
		
//		@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//		@JsonIgnore
//		private List<Posts> posts;
		
		@OneToMany(mappedBy = "user1", cascade = CascadeType.ALL)
		@JsonIgnore
		private List<Friends> userID1;
		
//		@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//		@JsonIgnore
//		private List<Comments> comments;
//
//		@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//		@JsonIgnore
//		private List<Likes> likes;

		@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
		@JsonIgnore
		private List<Notifications> notifications;

		@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
		@JsonIgnore
		private List<Messages> sentMessages;

		@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
		@JsonIgnore
		private List<Messages> receivedMessages;

		@OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
		@JsonIgnore
		private List<Groups> adminGroups;

		public List<Friends> getUserID1() {
			return userID1;
		}


		public void setUserID1(List<Friends> userID1) {
			this.userID1 = userID1;
		}


		public List<Friends> getUserID2() {
			return userID2;
		}


		public void setUserID2(List<Friends> userID2) {
			this.userID2 = userID2;
		}



		@OneToMany(mappedBy = "user2", cascade = CascadeType.ALL)
		@JsonIgnore
		private List<Friends> userID2;

		public Users() {}
	
	
	public Users(int userID, String username, String email, String password, String profilePicture) {
			super();
			this.userID = userID;
			this.username = username;
			this.email = email;
			this.password = password;
			this.profilePicture = profilePicture;
		}

	
	public Users(int userID, String username, String email, String password) {
		super();
		this.userID = userID;
		this.username = username;
		this.email = email;
		this.password = password;
	}
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	
	public boolean isDeleted() {
		return isDeleted;
	}

	 public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	
//	public List<Posts> getPosts() {
//		return posts;
//	}
//
//
//	public void setPosts(List<Posts> posts) {
//		this.posts = posts;
//	}
//
//
//	public List<Comments> getComments() {
//		return comments;
//	}
//
//
//	public void setComments(List<Comments> comments) {
//		this.comments = comments;
//	}
//
//
//	public List<Likes> getLikes() {
//		return likes;
//	}
//
//
//	public void setLikes(List<Likes> likes) {
//		this.likes = likes;
//	}


	public List<Notifications> getNotifications() {
		return notifications;
	}


	public void setNotifications(List<Notifications> notifications) {
		this.notifications = notifications;
	}


	public List<Messages> getSentMessages() {
		return sentMessages;
	}


	public void setSentMessages(List<Messages> sentMessages) {
		this.sentMessages = sentMessages;
	}


	public List<Messages> getReceivedMessages() {
		return receivedMessages;
	}


	public void setReceivedMessages(List<Messages> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}


	public List<Groups> getAdminGroups() {
		return adminGroups;
	}


	public void setAdminGroups(List<Groups> adminGroups) {
		this.adminGroups = adminGroups;
	}

	

}

