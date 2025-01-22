package com.scmd.socialmedia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.scmd.socialmedia.entity.Comments;
import com.scmd.socialmedia.entity.Friends;
import com.scmd.socialmedia.entity.Messages;
import com.scmd.socialmedia.entity.Posts;
import com.scmd.socialmedia.entity.Users;
import com.scmd.socialmedia.services.LikesService;
import com.scmd.socialmedia.services.UsersService;
// backend code 
@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    UsersService usersService;

    @Autowired
    LikesService likesService;

    // Get a list of all users
    @GetMapping("/all")
    public ResponseEntity<List<Users>> getAllUsers() {
        return new ResponseEntity<>(usersService.getAllUsers(), HttpStatus.OK);
    }

    // Get details of a specific user by ID
    @GetMapping("/{userID}")
    public ResponseEntity<Users> getUserById(@PathVariable("userID") int userID) {
        return new ResponseEntity<>(usersService.getUserById(userID), HttpStatus.OK);
    }

    // Search for users by name
    @GetMapping("/search/{username}")
    public ResponseEntity<Users> searchUsersByName(@PathVariable("username") String username) {
        return new ResponseEntity<>(usersService.searchByUsername(username), HttpStatus.OK);
    }

    // Add Users
    @PostMapping
    public ResponseEntity<Users> addUser(@RequestBody Users user) {
        return new ResponseEntity<>(usersService.addUser(user), HttpStatus.CREATED);
    }

    // Update user
    @PutMapping("/update/{userId}")
    public ResponseEntity<Users> updateUser(@PathVariable("userId") int userId, @RequestBody Users updatedUser) {
        return new ResponseEntity<>(usersService.updateUser(userId, updatedUser), HttpStatus.OK);
    }

    // Delete user
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") int userId) {
        usersService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Retrieve all posts created by a specific user
    @GetMapping("/{userID}/posts")
    public ResponseEntity<List<Posts>> getPostsByUserId(@PathVariable("userID") int userID) {
        return new ResponseEntity<>(usersService.getPostsByUserId(userID), HttpStatus.OK);
    }

    // Retrieve all comments on posts created by a specific user
    @GetMapping("/{userID}/posts/comments")
    public ResponseEntity<List<Comments>> getCommentsByUserID(@PathVariable("userID") int userID) {
        return new ResponseEntity<>(usersService.getCommentsByUserID(userID), HttpStatus.OK);
    }

    // Retrieve all friends of a specific user
    @GetMapping("/{userID}/friends")
    public ResponseEntity<List<Friends>> getAcceptedFriends(@PathVariable("userID") int userID) {
        return new ResponseEntity<>(usersService.getAcceptedFriends(userID), HttpStatus.OK);
    }

    // Retrieve pending friend requests for a specific user
    @GetMapping("/{userID}/friend-requests/pending")
    public ResponseEntity<List<Friends>> getPendingFriendRequests(@PathVariable("userID") int userID) {
        return new ResponseEntity<>(usersService.getPendingFriendRequests(userID), HttpStatus.OK);
    }

    // Send a friend request from one user to another
    @PostMapping("/{userId}/friend-requests/send/{friendId}")
    public ResponseEntity<Friends> sendFriendRequest(@PathVariable("userId") int userID, @PathVariable("friendId") int friendID) {
        return new ResponseEntity<>(usersService.sendFriendRequest(userID, friendID), HttpStatus.OK);
    }

    // Retrieve all messages between two users
    @GetMapping("/{userId}/messages/{otherUserId}")
    public ResponseEntity<List<Messages>> getMessagesBetweenUsers(@PathVariable("userId") int senderID,
                                                                  @PathVariable("otherUserId") int receiverID) {
        return new ResponseEntity<>(usersService.getMessagesBetweenUsers(senderID, receiverID), HttpStatus.OK);
    }

    // Send a message from one user to another
    @PostMapping("/{userId}/messages/send/{otherUserId}")
    public ResponseEntity<Messages> sendMessage(@PathVariable("userId") int userId,
                                                 @PathVariable("otherUserId") int otherUserId,
                                                 @RequestBody Messages message) {
        return new ResponseEntity<>(usersService.sendMessage(userId, otherUserId, message), HttpStatus.OK);
    }

    // Search for users by email and password
    @GetMapping("/search/{email}/{password}")
    public ResponseEntity<Users> searchUsersByEmailAndPassword(@PathVariable("email") String email,
                                                               @PathVariable("password") String password) {
        return new ResponseEntity<>(usersService.searchByEmailAndPassword(email, password), HttpStatus.OK);
    }
}
