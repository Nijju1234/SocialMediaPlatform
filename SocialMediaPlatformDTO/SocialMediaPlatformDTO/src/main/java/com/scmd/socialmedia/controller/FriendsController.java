package com.scmd.socialmedia.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scmd.socialmedia.dto.FriendsDTO;
import com.scmd.socialmedia.dto.MessagesDTO;
import com.scmd.socialmedia.entity.Friends;
import com.scmd.socialmedia.entity.FriendsStatus;
import com.scmd.socialmedia.entity.Messages;
import com.scmd.socialmedia.exception.MessagesNotFoundException;
//import com.scmd.socialmedia.entity.Messages;
import com.scmd.socialmedia.services.FriendsService;


@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class FriendsController 
{
	@Autowired
	FriendsService friendsService;



	// Add a friend
	@PostMapping("/users/{userId}/friends/{friendId}")
	public ResponseEntity<Map<String, Object>> addFriend(@PathVariable int userId,@PathVariable int friendId) 
	{
	    // Call the service to add a friend
	    String result = friendsService.addFriends(userId, friendId);
	    // Create a response body
	    Map<String, Object> response = new LinkedHashMap<>();
	    response.put("status", result.equals("Friend added successfully") ? "success" : "failure");
	    response.put("message", result);
	    // Return response entity
	    HttpStatus status = result.equals("Friend added successfully") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
	    return ResponseEntity.status(status).body(response);
	}
	// Retrieve friend of a user DTO
	@GetMapping("/user/{UserId}/friends")
	public ResponseEntity<Object> getAllAcceptedFriends(@PathVariable("UserId") int userId) {
	    // Retrieve the list of accepted friends directly (no DTO transformation)
	    List<FriendsDTO> friendsList = friendsService.getAcceptedUser(userId);  // Get the list of Friends entities
	    
	    // Prepare the response body
	    Map<String, Object> response = new LinkedHashMap<>();
	    response.put("status", "success");
	    response.put("data", friendsList);  // Return the raw Friends entities
	    
	    // Return the response with the list of friends
	    return ResponseEntity.ok().body(response);
	}






	// Remove a Friend
	@DeleteMapping("/users/{userId}/friends/{friendId}")
	public ResponseEntity<Object> removeFriendbyid(@PathVariable int userId,@PathVariable int friendId) 
	{
		friendsService.deleteFriend(friendId);
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("status", "success");
		response.put("message", "result");
		return ResponseEntity.ok().body(response);
	}

//	 Retrieve all messages between friends
	@GetMapping("/friends/{friendshipId}/messages")
	public ResponseEntity<Object> msgbwfrnds(@PathVariable("friendshipId") int friendshipId) 
	{
		List<MessagesDTO> messages = friendsService.getMessagesBetweenFriends(friendshipId);
		if (messages.isEmpty()) {
			throw new MessagesNotFoundException(
					"No messages found between friends with friendsshipID: " + friendshipId);
		}
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("status", "success");
		response.put("message", "Messages Retrieved successfully");
		response.put("data", messages);
		return ResponseEntity.ok().body(response);
	}

	// Send a message to a friend
	@PostMapping("/friends/{friendshipId}/messages/send")
	public ResponseEntity<Object> sendMessagetofriend(@PathVariable("friendshipId") int friendshipId,
			@RequestBody Messages message) 
	{
		MessagesDTO Message = friendsService.sendMessageToFriend(message, friendshipId);
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("status", "success");
		response.put("message", "Message sent successfully");
		 response.put("data",Message);
		return ResponseEntity.ok().body(response);
	}

//	// Add an endpoint to retrieve friends by status
//	@GetMapping("/friends/{Status}")
//	public ResponseEntity<Object> getFriendsByStatus(@PathVariable("Status") String Status) 
//	{
//		List<Friends> friend = friendsService.getByFriendStatus(Status);
//		Map<String, Object> response = new LinkedHashMap<>();
//		response.put("status", "success");
//		response.put("message", " Retrieved friend by Status successfully");
//		response.put("data", friend);
//		return ResponseEntity.ok().body(response);
//	}
	


	// Retrieve pending friend request of a user
	@GetMapping("/user/pending/{UserId}/friends")
	public ResponseEntity<Object> getPendingFriendRequests(@PathVariable("UserId") int userId) {
	    // Retrieve the list of pending friends directly (no DTO transformation)
	    List<FriendsDTO> pendingFriendsList = friendsService.getPendingFriendRequests(userId);  // Get the list of Friends entities
	    
	    // Prepare the response body
	    Map<String, Object> response = new LinkedHashMap<>();
	    response.put("status", "success");
	    response.put("data", pendingFriendsList);  // Return the raw Friends entities
	    
	    // Return the response with the list of pending friends
	    return ResponseEntity.ok().body(response);
	}


}
