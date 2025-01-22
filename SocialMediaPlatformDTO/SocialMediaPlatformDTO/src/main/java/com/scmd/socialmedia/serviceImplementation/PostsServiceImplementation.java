package com.scmd.socialmedia.serviceImplementation;
 
import com.scmd.socialmedia.dto.PostsDTO;
import com.scmd.socialmedia.entity.Posts;
import com.scmd.socialmedia.entity.Users;
import com.scmd.socialmedia.exception.InvalidPostException;
import com.scmd.socialmedia.exception.PostIdCannotBeNegativeException;
import com.scmd.socialmedia.exception.PostNotFoundException;
import com.scmd.socialmedia.repositories.PostsRepository;
import com.scmd.socialmedia.services.FriendsService;
import com.scmd.socialmedia.services.PostsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
@Service
public class PostsServiceImplementation implements PostsService {
 
    @Autowired
    PostsRepository postsRepository;
 
    @Autowired
    FriendsService friendsService;
 
    @Override
    public Posts savepost(PostsDTO postDTO) {
        if (postDTO == null || postDTO.getContent() == null || postDTO.getContent().trim().isEmpty()) {
            throw new InvalidPostException("Invalid post content provided.");
        }
 
        Users user = new Users();
        user.setUserID(postDTO.getUser().getUserID());
        // Fetch user entity based on the userID
        // You can use a UserRepository to fetch the user if it's stored in the DB
 
        Posts post = new Posts();
        post.setUser(user);
        post.setContent(postDTO.getContent());
        post.setTimestamp(new Timestamp(System.currentTimeMillis()));
 
        return postsRepository.save(post);
    }
 
    @Override
    public Posts getByPostID(int PostID) {
        if(PostID < 0) {
            throw new  PostIdCannotBeNegativeException("Post ID cannot be negative.");
        }
        Posts post = postsRepository.findById(PostID).orElse(null);
        if (post == null) {
            throw new PostNotFoundException("Post with ID " + PostID + " not found.");
        }
        return post;
    }
 
    @Override
    public Posts updatePosts(int postId, PostsDTO postDTO) {
        Posts oldPost = getByPostID(postId);
        if (oldPost == null) {
            throw new PostNotFoundException("Post with ID " + postId + " not found for update.");
        }
 
        if (postDTO.getContent() == null || postDTO.getContent().trim().isEmpty()) {
            throw new InvalidPostException("Post content cannot be empty.");
        }
 
        oldPost.setContent(postDTO.getContent());
        oldPost.setTimestamp(new Timestamp(System.currentTimeMillis()));
 
        return postsRepository.save(oldPost);
    }
 
    @Override
    public void deletePostById(int PostID) {
        Optional<Posts> existingPostOptional = postsRepository.findById(PostID);
 
        if (existingPostOptional.isEmpty()) {
            throw new PostNotFoundException("Post with ID " + PostID + " not found for deletion.");
        }
 
        Posts existingPost = existingPostOptional.get();
        // Soft delete (set is_deleted = true)
        existingPost.setIs_deleted(true);
 
        postsRepository.save(existingPost);
    }
 
    @Override
    public List<Posts> getAllPostsOfFriends(int userId) {
        List<Integer> friendIds = friendsService.getFriendIds(userId);
        List<Posts> friendsPosts = new ArrayList<>();
        for (int friendId : friendIds) {
            List<Posts> postsByFriend = postsRepository.findByUserId(friendId);
            friendsPosts.addAll(postsByFriend);
        }
 
        return friendsPosts.isEmpty() ? null : friendsPosts;
    }
}