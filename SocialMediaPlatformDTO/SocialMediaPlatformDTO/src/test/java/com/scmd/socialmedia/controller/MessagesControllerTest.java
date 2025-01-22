//package com.scmd.socialmedia.controller;
// 
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
// 
//import java.util.Arrays;
//import java.util.List;
// 
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
// 
//import com.scmd.socialmedia.controller.MessagesController;
//import com.scmd.socialmedia.dto.MessagesDTO;
//import com.scmd.socialmedia.services.MessagesService;
// 
//@ExtendWith(MockitoExtension.class)
//public class MessagesControllerTest {
// 
//    @Mock
//    private MessagesService messagesService;
// 
//    @InjectMocks
//    private MessagesController messagesController;
// 
//    private MessagesDTO mockMessageDTO;
//    private List<MessagesDTO> mockMessagesList;
// 
//    @BeforeEach
//    void setUp() {
//        // Initialize test data using MessagesDTO instead of Messages entity
//        mockMessageDTO = new MessagesDTO();
//        mockMessageDTO.setId(1);
//        mockMessageDTO.setMessageText("Test message content");
// 
//        mockMessagesList = Arrays.asList(mockMessageDTO);
//    }
// 
//    @Test
//    void getAllMessages_ShouldReturnAllMessages() {
//        // Arrange
//        when(messagesService.getAllMessages()).thenReturn(mockMessagesList);
// 
//        // Act
//        ResponseEntity<ApiResponse<List<MessagesDTO>>> response = messagesController.getAllMessages();
// 
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        ApiResponse<List<MessagesDTO>> responseBody = response.getBody();
//        assertEquals("success", responseBody.getStatus());
//        assertEquals("Messages retrieved successfully", responseBody.getMessage());
//        assertEquals(mockMessagesList, responseBody.getData());
//        verify(messagesService, times(1)).getAllMessages();
//    }
// 
//    @Test
//    void getMessageById_ShouldReturnMessage() {
//        // Arrange
//        when(messagesService.getMessageById(anyInt())).thenReturn(mockMessageDTO);
// 
//        // Act
//        ResponseEntity<ApiResponse<MessagesDTO>> response = messagesController.getMessageById(1);
// 
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        ApiResponse<MessagesDTO> responseBody = response.getBody();
//        assertEquals("success", responseBody.getStatus());
//        assertEquals("Message retrieved successfully", responseBody.getMessage());
//        assertEquals(mockMessageDTO, responseBody.getData());
//        verify(messagesService, times(1)).getMessageById(1);
//    }
// 
//    @Test
//    void createMessage_ShouldCreateNewMessage() {
//        // Arrange
//        MessagesDTO newMessageDTO = new MessagesDTO();
//        newMessageDTO.setMessageText("New message content");
//        when(messagesService.saveMessage(any(MessagesDTO.class))).thenReturn(mockMessageDTO);
// 
//        // Act
//        ResponseEntity<ApiResponse<MessagesDTO>> response = messagesController.createMessage(newMessageDTO);
// 
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        ApiResponse<MessagesDTO> responseBody = response.getBody();
//        assertEquals("success", responseBody.getStatus());
//        assertEquals("Message created successfully", responseBody.getMessage());
//        assertEquals(mockMessageDTO, responseBody.getData());
//        verify(messagesService, times(1)).saveMessage(newMessageDTO);
//    }
// 
//    @Test
//    void updateMessageById_ShouldUpdateMessage() {
//        // Arrange
//        MessagesDTO updatedMessageDTO = new MessagesDTO();
//        updatedMessageDTO.setMessageText("Updated content");
//        when(messagesService.updateMessageById(anyInt(), any(MessagesDTO.class))).thenReturn(mockMessageDTO);
// 
//        // Act
//        ResponseEntity<ApiResponse<MessagesDTO>> response = messagesController.updateMessageById(1, updatedMessageDTO);
// 
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        ApiResponse<MessagesDTO> responseBody = response.getBody();
//        assertEquals("success", responseBody.getStatus());
//        assertEquals("Message updated successfully", responseBody.getMessage());
//        assertEquals(mockMessageDTO, responseBody.getData());
//        verify(messagesService, times(1)).updateMessageById(1, updatedMessageDTO);
//    }
// 
//    @Test
//    void deleteMessageById_ShouldDeleteMessage() {
//        // Arrange
//        doNothing().when(messagesService).deleteMessageById(anyInt());
// 
//        // Act
//        ResponseEntity<ApiResponse<Void>> response = messagesController.deleteMessageById(1);
// 
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        ApiResponse<Void> responseBody = response.getBody();
//        assertEquals("success", responseBody.getStatus());
//        assertEquals("Message deleted successfully", responseBody.getMessage());
//        verify(messagesService, times(1)).deleteMessageById(1);
//    }
//}