package com.scmd.socialmedia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scmd.socialmedia.dto.FriendsDTO;
import com.scmd.socialmedia.dto.MessagesDTO;
import com.scmd.socialmedia.entity.Friends;
import com.scmd.socialmedia.services.FriendsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FriendsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FriendsService friendsService;

    @InjectMocks
    private FriendsController friendsController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(friendsController).build();
    }

    // Test for adding a friend
    @Test
    public void testAddFriend() throws Exception {
        when(friendsService.addFriends(anyInt(), anyInt())).thenReturn("Friend added successfully");

        mockMvc.perform(post("/api/users/1/friends/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Friend added successfully"));
    }

    // Test for retrieving all accepted friends
    @Test
    public void testGetAllAcceptedFriends() throws Exception {
        List<FriendsDTO> friendsList = new ArrayList<>();
        friendsList.add(new FriendsDTO(1, 1, 2, "ACCEPTED"));
        friendsList.add(new FriendsDTO(2, 1, 3, "ACCEPTED"));

        when(friendsService.getAcceptedUser(anyInt())).thenReturn(friendsList);

        mockMvc.perform(get("/api/user/1/friends"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].status").value("ACCEPTED"))
                .andExpect(jsonPath("$.data[1].status").value("ACCEPTED"));
    }

    @Test
    public void testRemoveFriend_Success() throws Exception {
        int userId = 1;
        int friendId = 2;

        // Mocking the deleteFriend method to return an Optional with a mock Friends object
        Friends mockFriend = new Friends();
        mockFriend.setFriendshipID(1);
        when(friendsService.deleteFriend(friendId)).thenReturn(Optional.of(mockFriend));

        // Perform the delete request and validate the result
        mockMvc.perform(delete("/api/users/{userId}/friends/{friendId}", userId, friendId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("result"));
    }

    // Test for retrieving messages between friends
    @Test
    public void testGetMessagesBetweenFriends() throws Exception {
        List<MessagesDTO> messages = new ArrayList<>();
        MessagesDTO msg1 = new MessagesDTO(1, 1, 2, "Hello!", null, false);
        MessagesDTO msg2 = new MessagesDTO(2, 2, 1, "Hi!", null, false);

        messages.add(msg1);
        messages.add(msg2);

        when(friendsService.getMessagesBetweenFriends(anyInt())).thenReturn(messages);

        mockMvc.perform(get("/api/friends/1/messages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Messages Retrieved successfully"))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].messageText").value("Hello!"))
                .andExpect(jsonPath("$.data[1].messageText").value("Hi!"));
    }

    // Test for sending a message to a friend
    @Test
    public void testSendMessageToFriend() throws Exception {
        MessagesDTO message = new MessagesDTO(1, 1, 2, "Test Message", null, false);
        when(friendsService.sendMessageToFriend(any(), anyInt())).thenReturn(message);

        mockMvc.perform(post("/api/friends/1/messages/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(message)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Message sent successfully"))
                .andExpect(jsonPath("$.data.messageText").value("Test Message"));
    }

    // Test for retrieving pending friend requests
    @Test
    public void testGetPendingFriendRequests() throws Exception {
        List<FriendsDTO> pendingFriendsList = new ArrayList<>();
        pendingFriendsList.add(new FriendsDTO(1, 1, 2, "PENDING"));
        pendingFriendsList.add(new FriendsDTO(2, 1, 3, "PENDING"));

        when(friendsService.getPendingFriendRequests(anyInt())).thenReturn(pendingFriendsList);

        mockMvc.perform(get("/api/user/pending/1/friends"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].status").value("PENDING"))
                .andExpect(jsonPath("$.data[1].status").value("PENDING"));
    }
}
