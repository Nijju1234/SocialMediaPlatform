package com.scmd.socialmedia.serviceImplementation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scmd.socialmedia.entity.Comments;
import com.scmd.socialmedia.entity.Friends;
import com.scmd.socialmedia.entity.FriendsStatus;
import com.scmd.socialmedia.entity.Groups;
import com.scmd.socialmedia.entity.Messages;
import com.scmd.socialmedia.entity.Notifications;
import com.scmd.socialmedia.entity.Posts;
import com.scmd.socialmedia.entity.Users;
import com.scmd.socialmedia.exception.CommentsNotFoundException;
import com.scmd.socialmedia.exception.GroupsNotFoundException;
import com.scmd.socialmedia.exception.NoAcceptedFriendsFoundException;
import com.scmd.socialmedia.exception.NoMessageFoundException;
import com.scmd.socialmedia.exception.NoPendingFriendsFoundException;
import com.scmd.socialmedia.exception.NotificationNotFoundException;
import com.scmd.socialmedia.exception.PostsNotFoundException;
import com.scmd.socialmedia.exception.UserCreationException;
import com.scmd.socialmedia.exception.UserNotFoundException;
import com.scmd.socialmedia.repositories.CommentsRepository;
import com.scmd.socialmedia.repositories.FriendsRepository;
import com.scmd.socialmedia.repositories.GroupsRepository;
import com.scmd.socialmedia.repositories.MessagesRepository;
import com.scmd.socialmedia.repositories.NotificationsRepository;
import com.scmd.socialmedia.repositories.PostsRepository;
import com.scmd.socialmedia.repositories.UsersRepository;
import com.scmd.socialmedia.services.UsersService;

@Service
public class UsersServiceImplementation implements UsersService{

	@Autowired
	UsersRepository usersRepository;
	
	@Autowired 
	PostsRepository postsRepository;
	
	@Autowired
	FriendsRepository  friendsRepository;
	
	@Autowired
	MessagesRepository messagesRepository;
	
	@Autowired
	NotificationsRepository notificationsRepository;
	
	@Autowired
	GroupsRepository groupsRepository;
	
	@Autowired
	CommentsRepository commentsRepository;

    //  Get a list of all users
	@Override
	public List<Users> getAllUsers() {
	    // Fetch all users
	    List<Users> userList = usersRepository.findAll();
	    
	    // If no users are found, throw an exception
	    if (userList.isEmpty()) {
	        throw new UserNotFoundException("No users found");
	    }

	    // Filter out users that are marked as deleted
	    List<Users> activeUsers = userList.stream()
	            .filter(user -> !user.isDeleted())
	            .collect(Collectors.toList());

	    return activeUsers;
	}


	// Get details of a specific user by ID
		@Override
		public Users getUserById(int userID) 
		{
			return usersRepository.findById(userID)
					.orElseThrow(() -> new UserNotFoundException("User with ID '" + userID + "' not found."));
		}
		
		// Search for users by name
		@Override
		public Users searchByUsername(String username) 
		{
			Optional<Users> user = usersRepository.findByUsername(username);
			Users s = user.orElse(null);
			if (s == null) 
			{
				throw new UserNotFoundException("User with username '" + username + "' not found.");
			}

			return s;
		}
	
		// Add Users
		@Override
		public Users addUser(Users user) 
		{

			Optional<Users> existingUser = usersRepository.findByEmail(user.getEmail());
			Optional<Users> existingUserByUsername = usersRepository.findByUsername(user.getUsername());
			if (existingUserByUsername.isPresent()) 
			{
				throw new UserCreationException("Username already exists.");
			}
			if (existingUser.isPresent()) 
			{
				throw new UserCreationException("Email already exists.");
			}
			return usersRepository.save(user);
		}

		// Update user details
		@Override
		public Users updateUser(int userID, Users user) 
		{

			Optional<Users> existingUser = usersRepository.findByEmail(user.getEmail());

			Optional<Users> existingUserByUsername = usersRepository.findByUsername(user.getUsername());

			if (existingUserByUsername.isPresent() && existingUserByUsername.get().getUserID() != userID) 
			{
				throw new UserCreationException("Username already exists.");
			}

			if (existingUser.isPresent() && existingUser.get().getUserID() != userID) 
			{
				throw new UserCreationException("Email already exists.");
			}

			Users user1 = getUserById(userID);
			user1.setUsername(user.getUsername());
			user1.setEmail(user.getEmail());
			user1.setPassword(user.getPassword());
			user1.setProfilePicture(user.getProfilePicture());
			return usersRepository.save(user1);
		}

	//delete user
    public Users deleteUser(int userId) {
        Optional<Users> existingUserOptional = usersRepository.findById(userId);

        if (existingUserOptional.isPresent()) {
            Users existingUser = existingUserOptional.get();
            existingUser.setDeleted(true);  // Flag to mark it as "deleted"  
            return usersRepository.save(existingUser);
        }
		return null;
       
    }
    


    //  Retrieve all posts created by a specific user
	@Override
	public List<Posts> getPostsByUserId(int userID) 
	{
		Users user = getUserById(userID);
		List<Posts> posts = postsRepository.findByUser(user);
		if (posts == null || posts.isEmpty()) 
		{
			throw new PostsNotFoundException("No posts found for the user with ID: " + user.getUserID());
		}
		return posts;
	}
	
//  Retrieve all comments on posts created by a specific user
	@Override
	public List<Comments> getCommentsByUserID(int userID) 
	{
		Users user = getUserById(userID);
		List<Posts> userPosts = postsRepository.findByUser(user);
		if (userPosts == null || userPosts.isEmpty()) 
		{
			throw new PostsNotFoundException("No posts found for the user with ID: " + userID);
		}

		List<Comments> allComments = new ArrayList<>();
		for (Posts post : userPosts) 
		{
			List<Comments> commentsOnPost = commentsRepository.findByPost(post);
			if (commentsOnPost != null) 
			{
				allComments.addAll(commentsOnPost);
			}
		}
		if (allComments == null || allComments.isEmpty()) 
		{
			throw new CommentsNotFoundException("No comments for the post created by this userID: " + userID);
		}

		return allComments;
	}


	// Retrieve all friends of a specific user.
	@Override
	public List<Friends> getAcceptedFriends(int userID) 
	{
		Users user = getUserById(userID);
		List<Friends> friendList = friendsRepository.findByUser1(user);
		List<Friends> accepted = new ArrayList<>();
		for (Friends f : friendList) 
		{
			if (f.getStatus() == FriendsStatus.accepted) 
			{
				accepted.add(f);
			}
		}
		if (accepted.isEmpty()) 
		{
			throw new NoAcceptedFriendsFoundException("No accepted friends found for user with ID: " + userID);
		}
		return accepted;
	}

	// Retrieve pending friend requests for a specific user
	@Override
	public List<Friends> getPendingFriendRequests(int userID) 
	{
		Users user = getUserById(userID);
		List<Friends> friList = friendsRepository.findByUser1(user);
		List<Friends> pending = new ArrayList<>();
		for (Friends f : friList) 
		{
			if (f.getStatus() == FriendsStatus.pending) {
				pending.add(f);
			}
		}
		if (pending.isEmpty()) 
		{
			throw new NoPendingFriendsFoundException("No pending friends found for user with ID: " + userID);
		}
		return pending;
	}

	// Send a friend request from one user to another.
		@Override
		public Friends sendFriendRequest(int requesterID, int receiverID) 
		{
			Users requester = getUserById(requesterID);
			Users receiver = getUserById(receiverID);
			Friends friendRequest = new Friends();
			friendRequest.setUser1(requester);
			friendRequest.setUser2(receiver);
			friendRequest.setStatus(FriendsStatus.pending);

			return friendsRepository.save(friendRequest);
		}

		// Retrieve all messages between two users
		@Override
		public List<Messages> getMessagesBetweenUsers(int senderID, int receiverID) 
		{

			Users user1 = getUserById(senderID);
			Users user2 = getUserById(receiverID);
			List<Messages> messages = new ArrayList<>();
			List<Messages> msgList1 = messagesRepository.findBySenderAndReceiver(user1, user2);
			List<Messages> msgList2 = messagesRepository.findBySenderAndReceiver(user2, user1);
			messages.addAll(msgList1);
			messages.addAll(msgList2);
			if (messages.isEmpty()) 
			{
				throw new NoMessageFoundException("No messages found between these users.");
			}
			return messages;
		}
		
		// Send a message from one user to another
		// Send a message from one user to another
		@Override
		public Messages sendMessage(int senderID, int receiverID, Messages message) {
		    Users sender = getUserById(senderID); // Fetch sender user object
		    Users receiver = getUserById(receiverID); // Fetch receiver user object

		    // Set sender, receiver, and timestamp for the message
		    message.setSender(sender);
		    message.setReceiver(receiver);
		    message.setTimestamp(new Timestamp(System.currentTimeMillis()));

		    // Save the message to the repository
		    return messagesRepository.save(message);
		}


		
		// Retrieve all notifications for a specific user
		@Override
		public List<Notifications> getNotificationsByUserID(int userID) 
		{
			Users user = getUserById(userID);
			List<Notifications> notifications = notificationsRepository.findByUser(user);
			if (notifications.isEmpty()) 
			{
				throw new NotificationNotFoundException("Notifications not found for the given userId");
			}
			return notifications;

		}

		
		
		// Retrieve all groups a user is a member of
		@Override
		public List<Groups> getgroupsByUsers(int userId) 
		{
			Users userList = getUserById(userId); // throws exception
			List<Groups> group = groupsRepository.findByAdmin(userList);
			if (group == null || group.isEmpty()) 
			{
				throw new GroupsNotFoundException("group not found for this user");
			}
			return group;

		}
		
		// Search for users by user name and password
			@Override
			public Users searchByEmailAndPassword(String email,String password) 
			{
				Optional<Users> user = usersRepository.findByEmailAndPassword(email, password);
				Users s = user.orElse(null);
				if (s == null) 
				{
					throw new UserNotFoundException("User with email:" + email +"and password:"+ password+"not found.");
				}

				return s;
			}


		

			
}
