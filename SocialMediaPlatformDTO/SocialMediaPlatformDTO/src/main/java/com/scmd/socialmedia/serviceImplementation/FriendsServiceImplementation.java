package com.scmd.socialmedia.serviceImplementation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import com.scmd.socialmedia.dto.FriendsDTO;
import com.scmd.socialmedia.dto.MessagesDTO;
import com.scmd.socialmedia.entity.Friends;
import com.scmd.socialmedia.entity.FriendsStatus;
import com.scmd.socialmedia.entity.Messages;
import com.scmd.socialmedia.entity.Users;
import com.scmd.socialmedia.exception.FriendNotFoundException;
import com.scmd.socialmedia.exception.MessagesNotFoundException;
import com.scmd.socialmedia.exception.NotFriendsException;
import com.scmd.socialmedia.repositories.FriendsRepository;
import com.scmd.socialmedia.repositories.MessagesRepository;
import com.scmd.socialmedia.repositories.UsersRepository;
import com.scmd.socialmedia.services.FriendsService;

@Service
public class FriendsServiceImplementation implements FriendsService {
    @Autowired
    FriendsRepository friendsRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    MessagesRepository messagesRepository;

    @Override
    public List<FriendsDTO> getAcceptedUser(int userID) {
        List<Friends> friends = friendsRepository.findAll();
        List<FriendsDTO> acceptedFriendsDTO = new ArrayList<>();
        for (Friends friend : friends) {
            if (friend.getUser1().getUserID() == userID || friend.getUser2().getUserID() == userID) {
                if (friend.getStatus() == FriendsStatus.accepted) {
                    // Convert Friend entity to DTO
                    FriendsDTO friendsDTO = new FriendsDTO(
                        friend.getFriendshipID(),
                        friend.getUser1().getUserID(),
                        friend.getUser2().getUserID(),
                        friend.getStatus().toString()
                    );
                    acceptedFriendsDTO.add(friendsDTO);
                }
            }
        }
        return acceptedFriendsDTO;
    }

    // Add a friend and return status message
    @Override
    public String addFriends(int userId, int friendId) {
        Users user = usersRepository.findById(userId).orElse(null);
        Users friend = usersRepository.findById(friendId).orElse(null);
        if (user == null || friend == null) {
            return "User or friend not found";
        }

        // Check existing friendship status
        Friends existingFriendship = friendsRepository.findByUserIdAndFriendId(userId, friendId);
        if (existingFriendship != null) {
            if (existingFriendship.getStatus() == FriendsStatus.pending) {  // Use PENDING constant
                existingFriendship.setStatus(FriendsStatus.accepted);  // Use ACCEPTED constant
                friendsRepository.save(existingFriendship);
                return "Friend added successfully";
            }
            return "Already friends";
        }

        // Create a new friendship record
        Friends friendRequest = new Friends();
        friendRequest.setUser1(user);
        friendRequest.setUser2(friend);
        friendRequest.setStatus(FriendsStatus.pending);  // Use PENDING constant
        friendsRepository.save(friendRequest);

        return "Friendship request sent successfully";
    }


 

    
    @Override
    public String removeFriend(int userId, int friendId) {
        Users user = usersRepository.findById(userId).orElse(null);
        Users friend = usersRepository.findById(friendId).orElse(null);
        if (user == null || friend == null) {
            return "User or friend not found";
        }

        // Get the friendship status between the user and the friend
        FriendsStatus friendStatus = friendsRepository.findFriendshipStatus(userId, friendId);
        
        // Ensure comparison against the proper enum
        if (friendStatus == FriendsStatus.accepted) {
            Friends friends = friendsRepository.findByUserIdAndFriendId(userId, friendId);
            friendsRepository.delete(friends);
            return "Friend removed successfully";
        }

        return "No friendship exists to remove";
    }
    // Retrieve all messages between friends and return DTOs
    @Override
    public List<MessagesDTO> getMessagesBetweenFriends(int friendshipID) {
        Friends friend = friendsRepository.findById(friendshipID).orElse(null);
        if (friend == null || friend.getStatus() != FriendsStatus.accepted) {
            throw new NotFriendsException("Not friends with ID: " + friendshipID);
        }

        int userID1 = friend.getUser1().getUserID();
        int userID2 = friend.getUser2().getUserID();

        List<Messages> user1_user2 = new LinkedList<>();
        user1_user2.addAll(friend.getUser1().getSentMessages());
        user1_user2.addAll(friend.getUser2().getSentMessages());

        List<MessagesDTO> messagesDTOList = new ArrayList<>();
        for (Messages message : user1_user2) {
            if (message.getReceiver().getUserID() == userID1 || message.getReceiver().getUserID() == userID2) {
                MessagesDTO messageDTO = new MessagesDTO(
                    message.getMessageID(),
                    message.getSender().getUsername(),
                    message.getReceiver().getUsername(),
                    message.getMessageText(),
                    message.getTimestamp()
                );
                messagesDTOList.add(messageDTO);
            }
        }

        if (messagesDTOList.isEmpty()) {
            throw new MessagesNotFoundException("No messages between the users");
        }

        return messagesDTOList;
    }

    // Send a message to a friend and return the created message DTO
    @Override
    public MessagesDTO sendMessageToFriend(Messages message, int friendshipId) {
        Friends friend = friendsRepository.findById(friendshipId).orElse(null);
        if (friend == null) {
            throw new FriendNotFoundException("Friendship not found");
        }

        Users sender = friend.getUser1();
        Users receiver = friend.getUser2();

        Messages newMessage = new Messages();
        newMessage.setSender(sender);
        newMessage.setReceiver(receiver);
        newMessage.setMessageText(message.getMessageText() + " | Reply from " + sender.getUserID() + " to " + receiver.getUserID());
        newMessage.setTimestamp(new Timestamp(System.currentTimeMillis()));

        messagesRepository.save(newMessage);

        // Return the message as DTO
        return new MessagesDTO(
            newMessage.getMessageID(),
            sender.getUsername(),
            receiver.getUsername(),
            newMessage.getMessageText(),
            newMessage.getTimestamp()
        );
    }

    // Get Pending Friend Requests for a User and return as DTO
    @Override
    public List<FriendsDTO> getPendingFriendRequests(int userId) {
        List<Friends> pendingFriends = friendsRepository.findByUserPendingRequests(userId);
        List<FriendsDTO> pendingFriendsDTOList = new ArrayList<>();

        for (Friends friend : pendingFriends) {
            FriendsDTO friendsDTO = new FriendsDTO(
                friend.getFriendshipID(),
                friend.getUser1().getUserID(),
                friend.getUser2().getUserID(),
                friend.getStatus().toString()
            );
            pendingFriendsDTOList.add(friendsDTO);
        }

        return pendingFriendsDTOList;
    }

    // Service Method for Getting Friend IDs
    @Override
    public List<Integer> getFriendIds(int userId) {
        List<Friends> friendships = friendsRepository.findFriendshipsByUserId(userId);
        List<Integer> friendIds = new ArrayList<>();

        for (Friends friendship : friendships) {
            if (friendship.getUser1().getUserID() == userId) {
                friendIds.add(friendship.getUser2().getUserID());
            } else {
                friendIds.add(friendship.getUser1().getUserID());
            }
        }

        return friendIds;
    }

    // Soft delete a friend (mark as deleted)
    @Override
    public Optional<Friends> deleteFriend(int friendId) {
        Optional<Friends> existingFriendshipOptional = friendsRepository.findById(friendId);

        if (existingFriendshipOptional.isEmpty()) {
            throw new FriendNotFoundException("Friendship with ID " + friendId + " not found.");
        }

        Friends existingFriendship = existingFriendshipOptional.get();
        existingFriendship.setDeleted(true); // Mark as deleted

        friendsRepository.save(existingFriendship);

        return Optional.of(existingFriendship); // Return updated friendship
    }

    	
	
}
