package com.scmd.socialmedia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.scmd.socialmedia.entity.Comments;
import com.scmd.socialmedia.entity.Friends;
import com.scmd.socialmedia.entity.Groups;
import com.scmd.socialmedia.entity.Messages;
import com.scmd.socialmedia.entity.Notifications;
import com.scmd.socialmedia.entity.Posts;
import com.scmd.socialmedia.entity.Users;

public interface UsersService {

	@Autowired

	List<Users> getAllUsers(); // Get a list of all users
	Users getUserById(int userID); // Get details of a specific user by ID.
	Users searchByUsername(String username);  // Search user by name
	Users addUser(Users user);
	Users updateUser(int userId, Users updatedUser);
	Users deleteUser(int userId);
	
//	List<Posts> getPostsByUserId(int userID);
	
	List<Friends> getAcceptedFriends(int userID); // Retrieve all friends of a specific user.
	List<Friends> getPendingFriendRequests(int userID); // Retrieve pending friend requests for a specific user
	Friends sendFriendRequest(int requesterID, int receiverID); // Send a friend request from one user to another.
	List<Messages> getMessagesBetweenUsers(int senderID, int receiverID); //Retrieve all messages between two users
	List<Notifications> getNotificationsByUserID(int userID); //Retrieve all notifications for a specific user
	Messages sendMessage(int senderID, int receiverID, Messages message); //Send a message from one user to another
	Users searchByEmailAndPassword(String email, String password);
	List<Groups> getgroupsByUsers(int userId);
	List<Posts> getPostsByUserId(int userID);
	List<Comments> getCommentsByUserID(int userID);
	
	
	
	


	
	
	
}
