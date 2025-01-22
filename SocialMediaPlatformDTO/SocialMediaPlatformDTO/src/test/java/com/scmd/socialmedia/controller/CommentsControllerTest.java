package com.scmd.socialmedia.controller;
 
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.scmd.socialmedia.dto.CommentsDTO;
import com.scmd.socialmedia.entity.Comments;
import com.scmd.socialmedia.services.CommentsService;

import org.attoparser.dom.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.doNothing;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
 
import static org.mockito.ArgumentMatchers.anyInt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
 
class CommentsControllerTest {
 
    @Mock
    private CommentsService commentsService;
 
    @InjectMocks
    private CommentsController commentsController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
 
    private CommentsDTO comment;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commentsController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // Create a sample comment object
        comment = new CommentsDTO();
        comment.setCommentID(1);
        comment.setCommentText("This is a test comment");
        comment.setTimestamp(LocalDateTime.now());
    }
 
    @Test
    void testGetCommentsByPostId() throws Exception {
        // Mock the response from the service layer
        when(commentsService.getCommentsByPostId(anyInt())).thenReturn(Arrays.asList(comment));
 
        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/posts/1/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Retrieved comments for the Post successfully"))
                .andExpect(jsonPath("$.data[0].commentID").value(1))
                .andExpect(jsonPath("$.data[0].commentText").value("This is a test comment"));
    }
 
    @Test
    void testGetAllCommentsData() throws Exception {
        // Mock the response from the service layer
        when(commentsService.getAllComments()).thenReturn(Arrays.asList(comment));
 
        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Retrieved a list of all comments successfully"))
                .andExpect(jsonPath("$.data[0].commentID").value(1))
                .andExpect(jsonPath("$.data[0].commentText").value("This is a test comment"));
    }
 
    @Test
    void testGetCommentsDataById() throws Exception {
        // Mock the response from the service layer
        when(commentsService.getCommentsByID(anyInt())).thenReturn(comment);
 
        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/comments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Retrieved a comment by its ID successfully"))
                .andExpect(jsonPath("$.data.commentID").value(1))
                .andExpect(jsonPath("$.data.commentText").value("This is a test comment"));
    }
 
    @Test
    void testAddComment() throws Exception {
        // Mock the response from the service layer
        when(commentsService.addComments(any(CommentsDTO.class))).thenReturn(comment);
 
        // Perform the POST request and verify the response
        mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Comment created successfully"))
                .andExpect(jsonPath("$.data.commentID").value(1))
                .andExpect(jsonPath("$.data.commentText").value("This is a test comment"));
    }
    
   @Test
    public void testLocalDateTimeSerialization() throws Exception {
        // Register JavaTimeModule
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
 
        // Create a LocalDateTime object
        LocalDateTime now = LocalDateTime.now();
 
        // Serialize LocalDateTime to JSON
        String json = objectMapper.writeValueAsString(now);
 
        // Deserialize JSON back to LocalDateTime
        LocalDateTime deserialized = objectMapper.readValue(json, LocalDateTime.class);
 
        // Verify the value
        assertEquals(now.getYear(), deserialized.getYear());
        assertEquals(now.getMonth(), deserialized.getMonth());
        assertEquals(now.getDayOfMonth(), deserialized.getDayOfMonth());
        assertEquals(now.getHour(), deserialized.getHour());
    }
    @Test
    public void testUpdateComment() throws Exception {
        // Ensure comment is initialized and populated correctly
        CommentsDTO comment = new CommentsDTO();
        comment.setCommentID(1);
        comment.setCommentText("This is a test comment");
        comment.setTimestamp(LocalDateTime.now());  // Set current LocalDateTime
 
        // Ensure the comment is not null
        assertNotNull(comment);
 
        // Mock the response from the service layer
        when(commentsService.updateComments(anyInt(), any(CommentsDTO.class))).thenReturn(comment);
 
        // Convert the comment object to JSON string
        String jsonContent = objectMapper.writeValueAsString(comment);  // Should not throw error now
 
        // Perform the PUT request and verify the response
        mockMvc.perform(put("/api/comments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))  // Use the JSON string here
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Comment updated successfully"))
                .andExpect(jsonPath("$.data.commentID").value(1))
                .andExpect(jsonPath("$.data.commentText").value("This is a test comment"));
    }
 
 
    @Test
 
    void testDeleteCommentById() throws Exception {
        // Mock the service layer (delete operation)
        doNothing().when(commentsService).deletePostComments(anyInt());
 
        // Perform the DELETE request and verify the response
        mockMvc.perform(delete("/api/comments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Comment deleted successfully"));
    }
 
 
    @Test
    void testAddCommentToPost() throws Exception {
        // Mock the response from the service layer
        when(commentsService.addPostComments(anyInt(), any(CommentsDTO.class))).thenReturn(comment);
 
        // Perform the POST request and verify the response
        mockMvc.perform(post("/api/posts/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Comment added to post successfully"))
                .andExpect(jsonPath("$.data.commentID").value(1))
                .andExpect(jsonPath("$.data.commentText").value("This is a test comment"));
    }
 
    @Test
    void testDeleteCommentByPostIdAndCommentId() throws Exception {
        // Mock the service layer (delete operation by postId and commentId)
        when(commentsService.deleteCommentByPostId(anyInt(), anyInt())).thenReturn(ResponseEntity.ok().body(Map.of("status", "success", "message", "Comment deleted successfully")));
 
        // Perform the DELETE request and verify the response
        mockMvc.perform(delete("/api/posts/1/comments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Comment deleted successfully"));
    }
}