package com.scmd.socialmedia.serviceImplementation;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.scmd.socialmedia.dto.CommentsDTO;
import com.scmd.socialmedia.entity.Comments;
import com.scmd.socialmedia.entity.Posts;
import com.scmd.socialmedia.exception.CommentDeletionException;
import com.scmd.socialmedia.exception.CommentsNotFoundException;
import com.scmd.socialmedia.exception.PostNotFoundException;
import com.scmd.socialmedia.repositories.CommentsRepository;
import com.scmd.socialmedia.repositories.PostsRepository;
import com.scmd.socialmedia.services.CommentsService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
 
@Service
public class CommentsServiceImplementation implements CommentsService {
 
    @Autowired
    private CommentsRepository commentsRepository;
 
    @Autowired
    private PostsRepository postsRepository;
 
    // Helper method to convert Comments to CommentsDTO
    private CommentsDTO convertToDTO(Comments comment) {
        return new CommentsDTO(
            comment.getCommentID(),
            comment.getCommentText(),
            comment.getTimestamp()
        );
    }
 
    // Helper method to convert CommentsDTO to Comments entity
    private Comments convertToEntity(CommentsDTO commentDTO) {
        Comments comment = new Comments();
        comment.setCommentText(commentDTO.getCommentText());
        return comment;
    }
 
    // 1. Retrieve Comments for the post
    @Override
    public List<CommentsDTO> getCommentsByPostId(int postId) {
        // Validate that the post exists
        Posts post = postsRepository.findById(postId).orElseThrow(() -> 
            new PostNotFoundException("Post with ID " + postId + " not found"));
 
        List<Comments> comments = post.getComments();
        return comments.stream()
                       .map(this::convertToDTO)
                       .collect(Collectors.toList());
    }
 
    // 2. Retrieve a list of all comments
    @Override
    public List<CommentsDTO> getAllComments() {
        List<Comments> comments = commentsRepository.findAll();
        return comments.stream()
                       .map(this::convertToDTO)
                       .collect(Collectors.toList());
    }
 
    // 3. Retrieve a comment by its ID
    @Override
    public CommentsDTO getCommentsByID(int commentId) {
        // Validate that the comment exists
        Comments comment = commentsRepository.findById(commentId).orElseThrow(() -> 
            new CommentsNotFoundException("Comment with ID " + commentId + " not found"));
        return convertToDTO(comment);
    }
 
    // 4. Create a new comment
    @Override
    public CommentsDTO addComments(CommentsDTO commentDTO) {
        // Validate the comment text (it shouldn't be empty or null)
        if (commentDTO.getCommentText() == null || commentDTO.getCommentText().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment text cannot be empty or null");
        }
 
        // Convert DTO to entity and save the comment
        Comments comment = convertToEntity(commentDTO);
        Comments savedComment = commentsRepository.save(comment);
        return convertToDTO(savedComment);
    }
 
    // 5. Update an existing comment by its ID
    @Override
    public CommentsDTO updateComments(int commentId, CommentsDTO commentDTO) {
        // Validate the comment text (it shouldn't be empty or null)
        if (commentDTO.getCommentText() == null || commentDTO.getCommentText().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment text cannot be empty or null");
        }
 
        // Validate that the comment exists
        Comments existingComment = commentsRepository.findById(commentId).orElseThrow(() -> 
            new CommentsNotFoundException("Comment with ID " + commentId + " not found"));
 
        existingComment.setCommentText(commentDTO.getCommentText());
        Comments updatedComment = commentsRepository.save(existingComment);
        return convertToDTO(updatedComment);
    }
 
    // 6. Delete a comment by its ID
    @Override
    public void deletePostComments(int commentId) {
        // Validate that the comment exists
        Comments comment = commentsRepository.findById(commentId)
            .orElseThrow(() -> new CommentDeletionException("Comment with ID " + commentId + " could not be deleted because it does not exist."));
 
        // Mark the comment as deleted (soft delete)
        comment.setDeleted(true);  // Assuming 'setDeleted' sets the deleted flag to true
 
        // Save the updated comment back to the repository
        commentsRepository.save(comment);
    }
 
    // 7. Add comment to post
    @Override
    public CommentsDTO addPostComments(int postId, CommentsDTO commentDTO) {
        // Validate the comment text (it shouldn't be empty or null)
        if (commentDTO.getCommentText() == null || commentDTO.getCommentText().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment text cannot be empty or null");
        }
 
        // Validate that the post exists
        Posts post = postsRepository.findById(postId).orElseThrow(() -> 
            new PostNotFoundException("Post with ID " + postId + " not found"));
 
        // Convert DTO to entity, associate it with the post, and save the comment
        Comments comment = convertToEntity(commentDTO);
        comment.setPost(post);
        Comments savedComment = commentsRepository.save(comment);
 
        return convertToDTO(savedComment);
    }
 
    // 8. Delete a comment by postId and commentId
    @Override
    public ResponseEntity<Object> deleteCommentByPostId(int postId, int commentId) {
        // Check if the post exists
        Posts post = postsRepository.findById(postId).orElseThrow(() -> 
            new PostNotFoundException("Post with ID " + postId + " not found"));
 
        // Check if the comment exists
        Comments comment = commentsRepository.findById(commentId).orElseThrow(() -> 
            new CommentsNotFoundException("Comment with ID " + commentId + " not found"));
 
        // Check if the comment is associated with the provided postId
        if (comment.getPost().getPostID() != postId) {
            throw new CommentsNotFoundException("Comment with ID " + commentId + " does not belong to Post with ID " + postId);
        }
 
        // Soft delete: Mark the comment as deleted
        comment.setDeleted(true); // Set the deleted flag to true
        // Save the updated comment
        commentsRepository.save(comment);
 
        // Prepare response map
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("message", "Comment with ID " + commentId + " deleted successfully");
 
        // Return response with success message
        return ResponseEntity.ok().body(response);
    }
 
    @Override
    public Optional<CommentsDTO> deleteComment(int commentId) {
        // Validate that the comment exists
        Optional<Comments> existingCommentOptional = commentsRepository.findById(commentId);
 
        if (existingCommentOptional.isEmpty()) {
            throw new CommentsNotFoundException("Comment with ID " + commentId + " not found for deletion.");
        }
 
        // Get the comment from the Optional object
        Comments existingComment = existingCommentOptional.get();
 
        // Mark the comment as deleted (soft delete)
        existingComment.setDeleted(true); // Assume 'setDeleted' is a method that marks the comment as deleted
 
        // Save the updated comment
        commentsRepository.save(existingComment);
 
        // Return the deleted comment wrapped in an Optional
        return Optional.of(convertToDTO(existingComment));
    }
}
 