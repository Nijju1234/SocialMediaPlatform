package com.scmd.socialmedia.services;
 
import com.scmd.socialmedia.dto.CommentsDTO;
import com.scmd.socialmedia.entity.Comments;
 
import jakarta.validation.Valid;
 
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;
 
public interface CommentsService {
 
    // Retrieve Comments for the post as DTO
    List<CommentsDTO> getCommentsByPostId(int postId);
 
    // Retrieve a list of all comments as DTO
    List<CommentsDTO> getAllComments();
 
    // Retrieve a comment by its ID as DTO
    CommentsDTO getCommentsByID(int commentId);
 
    // Create a new comment from a DTO and return the saved comment as DTO
    CommentsDTO addComments(CommentsDTO commentDTO);
 
    // Update an existing comment by its ID from a DTO and return the updated comment as DTO
    CommentsDTO updateComments(int commentId, CommentsDTO commentDTO);
 
    // Delete a comment by its ID
    void deletePostComments(int commentId);
 
    // Add comment to post and return the comment as DTO
    CommentsDTO addPostComments(int postId, CommentsDTO commentDTO);
 
    // Delete a comment by its ID and return the deleted comment as DTO
    Optional<CommentsDTO> deleteComment(int commentId);
 
    // Delete a comment by postId and commentId
    ResponseEntity<Object> deleteCommentByPostId(int postId, int commentId);
 
	
}