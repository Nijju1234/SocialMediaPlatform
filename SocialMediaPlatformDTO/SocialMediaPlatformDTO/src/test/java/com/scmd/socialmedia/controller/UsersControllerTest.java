package com.scmd.socialmedia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.scmd.socialmedia.entity.Comments;
import com.scmd.socialmedia.entity.Friends;
import com.scmd.socialmedia.entity.Messages;
import com.scmd.socialmedia.entity.Posts;
import com.scmd.socialmedia.entity.Users;
import com.scmd.socialmedia.services.UsersService;

class UsersControllerTest {

    @InjectMocks
    private UsersController usersController;

    @Mock
    private UsersService usersService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        List<Users> mockUsers = Arrays.asList(
                new Users(1, "user1", "user1@example.com", "password1"),
                new Users(2, "user2", "user2@example.com", "password2")
        );

        when(usersService.getAllUsers()).thenReturn(mockUsers);

        ResponseEntity<List<Users>> response = usersController.getAllUsers();
        assertNotNull(response);
        assertEquals(2, response.getBody().size());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetUserById() {
        Users mockUser = new Users(1, "user1", "user1@example.com", "password1");

        when(usersService.getUserById(1)).thenReturn(mockUser);

        ResponseEntity<Users> response = usersController.getUserById(1);
        assertNotNull(response);
        assertEquals(mockUser, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testSearchUsersByName() {
        Users mockUser = new Users(1, "user1", "user1@example.com", "password1");

        when(usersService.searchByUsername("user1")).thenReturn(mockUser);

        ResponseEntity<Users> response = usersController.searchUsersByName("user1");
        assertNotNull(response);
        assertEquals(mockUser, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testAddUser() {
        Users newUser = new Users(1, "user1", "user1@example.com", "password1");

        when(usersService.addUser(newUser)).thenReturn(newUser);

        ResponseEntity<Users> response = usersController.addUser(newUser);
        assertNotNull(response);
        assertEquals(newUser, response.getBody());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void testUpdateUser() {
        Users updatedUser = new Users(1, "user1Updated", "user1Updated@example.com", "passwordUpdated");

        when(usersService.updateUser(1, updatedUser)).thenReturn(updatedUser);

        ResponseEntity<Users> response = usersController.updateUser(1, updatedUser);
        assertNotNull(response);
        assertEquals(updatedUser, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetPostsByUserId() {
        List<Posts> mockPosts = Arrays.asList(new Posts(), new Posts());

        when(usersService.getPostsByUserId(1)).thenReturn(mockPosts);

        ResponseEntity<List<Posts>> response = usersController.getPostsByUserId(1);
        assertNotNull(response);
        assertEquals(2, response.getBody().size());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetCommentsByUserID() {
        List<Comments> mockComments = Arrays.asList(new Comments(), new Comments());

        when(usersService.getCommentsByUserID(1)).thenReturn(mockComments);

        ResponseEntity<List<Comments>> response = usersController.getCommentsByUserID(1);
        assertNotNull(response);
        assertEquals(2, response.getBody().size());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetAcceptedFriends() {
        List<Friends> mockFriends = Arrays.asList(new Friends(), new Friends());

        when(usersService.getAcceptedFriends(1)).thenReturn(mockFriends);

        ResponseEntity<List<Friends>> response = usersController.getAcceptedFriends(1);
        assertNotNull(response);
        assertEquals(2, response.getBody().size());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetPendingFriendRequests() {
        List<Friends> mockRequests = Arrays.asList(new Friends(), new Friends());

        when(usersService.getPendingFriendRequests(1)).thenReturn(mockRequests);

        ResponseEntity<List<Friends>> response = usersController.getPendingFriendRequests(1);
        assertNotNull(response);
        assertEquals(2, response.getBody().size());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testSendFriendRequest() {
        Friends mockFriend = new Friends();

        when(usersService.sendFriendRequest(1, 2)).thenReturn(mockFriend);

        ResponseEntity<Friends> response = usersController.sendFriendRequest(1, 2);
        assertNotNull(response);
        assertEquals(mockFriend, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetMessagesBetweenUsers() {
        List<Messages> mockMessages = Arrays.asList(new Messages(), new Messages());

        when(usersService.getMessagesBetweenUsers(1, 2)).thenReturn(mockMessages);

        ResponseEntity<List<Messages>> response = usersController.getMessagesBetweenUsers(1, 2);
        assertNotNull(response);
        assertEquals(2, response.getBody().size());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testSendMessage() {
        Messages mockMessage = new Messages();

        when(usersService.sendMessage(1, 2, mockMessage)).thenReturn(mockMessage);

        ResponseEntity<Messages> response = usersController.sendMessage(1, 2, mockMessage);
        assertNotNull(response);
        assertEquals(mockMessage, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testSearchUsersByEmailAndPassword() {
        Users mockUser = new Users(1, "user1", "user1@example.com", "password1");

        when(usersService.searchByEmailAndPassword("user1@example.com", "password1")).thenReturn(mockUser);

        ResponseEntity<Users> response = usersController.searchUsersByEmailAndPassword("user1@example.com", "password1");
        assertNotNull(response);
        assertEquals(mockUser, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }
}
