package com.scmd.socialmedia.controller;
 
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.scmd.socialmedia.dto.CommentsDTO;
import com.scmd.socialmedia.services.CommentsService;

import jakarta.validation.Valid;
 
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class CommentsController {
 
    @Autowired
    CommentsService commentsService;
 
    // 1. Retrieve Comments for the post
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<Object> getCommentsByPostId(@PathVariable("postId") int postId) {
        List<CommentsDTO> comments = commentsService.getCommentsByPostId(postId);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("message", "Retrieved comments for the Post successfully");
        response.put("data", comments);
        return ResponseEntity.ok().body(response);
    }
 
    // 2. Retrieve a list of all comments
    @GetMapping("/comments")
    public ResponseEntity<Object> getAllCommentsData() {
        List<CommentsDTO> comments = commentsService.getAllComments();
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("message", "Retrieved a list of all comments successfully");
        response.put("data", comments);
        return ResponseEntity.ok().body(response);
    }
 
    // 3. Retrieve a comment by its ID
    @GetMapping("/comments/{commentId}")
    public ResponseEntity<Object> getCommentsDataById(@PathVariable("commentId") int commentId) {
        CommentsDTO comment = commentsService.getCommentsByID(commentId);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("message", "Retrieved a comment by its ID successfully");
        response.put("data", comment);
        return ResponseEntity.ok().body(response);
    }
 
    // 4. Create a new comment
    @PostMapping("/comments")
    public ResponseEntity<Object> addComment(@Valid @RequestBody CommentsDTO commentDTO) {
        CommentsDTO savedComment = commentsService.addComments(commentDTO);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("message", "Comment created successfully");
        response.put("data", savedComment); // Include the saved comment in the response
        return ResponseEntity.ok().body(response);
    }
 
    // 5. Update an existing comment by its ID
    @PutMapping("/comments/{commentsId}")
    public ResponseEntity<Object> updateComment(@PathVariable("commentsId") int commentsId, @Valid @RequestBody CommentsDTO commentDTO) {
        CommentsDTO updatedComment = commentsService.updateComments(commentsId, commentDTO);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("message", "Comment updated successfully");
        response.put("data", updatedComment); // Include the updated comment in the response
        return ResponseEntity.ok().body(response);
    }
 
    // 6. Delete a comment by its ID
    @DeleteMapping("/comments/{commentsId}")
    public ResponseEntity<Object> deleteCommentsDataById(@PathVariable("commentsId") int commentsId) {
        commentsService.deleteComment(commentsId);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("message", "Comment deleted successfully");
        return ResponseEntity.ok().body(response);
    }
 
    // 7. Add comment to post
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Object> addCommentToPost(@PathVariable("postId") int postId, @Valid @RequestBody CommentsDTO commentDTO) {
        CommentsDTO savedComment = commentsService.addPostComments(postId, commentDTO);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("message", "Comment added to post successfully");
        response.put("data", savedComment); // Include the saved comment in the response
        return ResponseEntity.ok().body(response);
    }
 
    // 8. Delete a comment by postId and commentId
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<Object> deleteCommentByPostId(@PathVariable("postId") int postId, @PathVariable("commentId") int commentId) {
        return commentsService.deleteCommentByPostId(postId, commentId);
    }
}

