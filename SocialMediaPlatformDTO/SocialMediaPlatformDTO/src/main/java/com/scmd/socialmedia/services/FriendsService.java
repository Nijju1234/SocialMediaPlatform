package com.scmd.socialmedia.services;

import java.util.List;
import java.util.Optional;

import com.scmd.socialmedia.dto.FriendsDTO;
import com.scmd.socialmedia.dto.MessagesDTO;
import com.scmd.socialmedia.entity.Friends;
import com.scmd.socialmedia.entity.Messages;

public interface FriendsService {
    List<FriendsDTO> getAcceptedUser(int userID);  // Change the return type to List<FriendsDTO>

    String addFriends(int userId, int friendId);

    String removeFriend(int userId, int friendId);

    List<MessagesDTO> getMessagesBetweenFriends(int friendshipId);

    MessagesDTO sendMessageToFriend(Messages message, int friendshipId);

    List<FriendsDTO> getPendingFriendRequests(int userId);
    
    List<Integer> getFriendIds(int userId);

    Optional<Friends> deleteFriend(int friendId);
}
