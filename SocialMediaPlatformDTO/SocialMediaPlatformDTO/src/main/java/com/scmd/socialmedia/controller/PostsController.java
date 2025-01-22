
package com.scmd.socialmedia.controller;

import com.scmd.socialmedia.dto.PostsDTO;
import com.scmd.socialmedia.dto.PostsDTO1;
import com.scmd.socialmedia.entity.Likes;
import com.scmd.socialmedia.entity.Posts;
import com.scmd.socialmedia.services.LikesService;
import com.scmd.socialmedia.services.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostsController {

    @Autowired
    PostsService postService;

    @Autowired
    LikesService likesService;
    
 //// Retrieve all posts from all users
 // Retrieve all posts from all users
    
    
    @GetMapping("/posts/all-users")
    public ResponseEntity<List<PostsDTO1>> getAllPosts() {
        List<PostsDTO1> postsDTOList = postService.getAllPostsAsDTO();
        return new ResponseEntity<>(postsDTOList, HttpStatus.OK);
    }
    
   
    // Create a new post
    @PostMapping("/post")
    public ResponseEntity<Posts> addPost(@Valid @RequestBody PostsDTO postDTO) {
        Posts savedPost = postService.savepost(postDTO);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    // Retrieve Post by id
    @GetMapping("/post/{postId}")
    public ResponseEntity<Posts> getPosts(@PathVariable("postId") int PostId) {
        Posts post = postService.getByPostID(PostId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // Update post details
    @PutMapping("/post/update/{postId}")
    public ResponseEntity<Posts> updatePostDetails(@PathVariable("postId") int postId, @Valid @RequestBody PostsDTO postDTO) {
        Posts updatedPost = postService.updatePosts(postId, postDTO);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    // Delete a post
    @DeleteMapping("/post/delete/{postID}")
    public ResponseEntity<Void> deletePosts(@PathVariable("postID") int PostId) {
        postService.deletePostById(PostId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Add Like
    @PostMapping("/posts/{postId}/likes/add/{userId}")
    public ResponseEntity<Likes> addLike(@PathVariable int postId, @PathVariable int userId) {
        Likes savedLike = likesService.saveLike(postId, userId);
        return new ResponseEntity<>(savedLike, HttpStatus.CREATED);
    }

    // Retrieve all likes on a specific post.
    @GetMapping("/posts/{postid}/likes")
    public ResponseEntity<List<Likes>> getLikesByPostId(@PathVariable("postid") int PostId) {
        List<Likes> likes = likesService.getLikeByPostID(PostId);
        return new ResponseEntity<>(likes, HttpStatus.OK);
    }

    // Remove a like from a post
    @DeleteMapping("/posts/{postId}/likes/remove/{likeId}")
    public ResponseEntity<Void> deletelike(@PathVariable("postId") int postId, @PathVariable("likeId") int likeId) {
        likesService.deleteLikes(likeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Retrieve all likes on posts created by a specific user
    @GetMapping("/user/{userId}/posts/likes")
    public ResponseEntity<List<Likes>> getAllLikesOnPostsByUserId(@PathVariable int userId) {
        List<Likes> likes = likesService.getLikeBySpecfUser(userId);
        return new ResponseEntity<>(likes, HttpStatus.OK);
    }

    // Get like status for a post by a specific user
    @GetMapping("/user/{postId}/posts/{userId}/likestatus")
    public ResponseEntity<Likes> getLikeStatus(@PathVariable("postId") int postId, @PathVariable int userId) {
        Likes like = likesService.getLikeByPostIdAndUserId(postId, userId);
        return new ResponseEntity<>(like, HttpStatus.OK);
    }
}
