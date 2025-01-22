package com.scmd.socialmedia.serviceImplementation;
 
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scmd.socialmedia.entity.Likes;
import com.scmd.socialmedia.entity.Posts;
import com.scmd.socialmedia.entity.Users;
import com.scmd.socialmedia.exception.PostNotFoundException;
import com.scmd.socialmedia.exception.UserNotFoundException;
import com.scmd.socialmedia.repositories.LikesRepository;
import com.scmd.socialmedia.repositories.PostsRepository;
import com.scmd.socialmedia.repositories.UsersRepository;
import com.scmd.socialmedia.services.LikesService;

 
@Service
public class LikesServiceImplementation implements LikesService {
 
    @Autowired
    private LikesRepository likesRepository;
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private UsersRepository usersRepository;
 
    // Retrieve all likes on a specific post
    @Override
    public List<Likes> getLikeByPostID(int PostID) {
        Posts post = postsRepository.findById(PostID).orElse(null);
        if (post != null) {
            List<Likes> likeList = likesRepository.findByPost(post);
            return likeList.isEmpty() ? null : likeList;
        }
        return null;  // Post not found
    }
 
    // Add likes to post
//    @Override
//    public Likes savelike(int likeID, int postId, int userId) {
//        Users user = usersRepository.findById(userId).orElse(null);
//        Posts post = postsRepository.findById(postId).orElse(null);
// 
//        if (user != null && post != null) {
//            Likes like = new Likes();
//            like.setLikeID(likeID);
//            like.setPost(post);
//            like.setUser(user);
//            like.setTimestamp(new Timestamp(System.currentTimeMillis()));
//            return likesRepository.save(like);
//        }
//        return null;  // User or Post not found
//    }
    @Override
    public Likes saveLike(int postID, int userID) {
        // Fetch the Post and User from the database
        Posts post = postsRepository.findById(postID).orElseThrow(() -> new PostNotFoundException("Post not found"));
        Users user = usersRepository.findById(userID).orElseThrow(() -> new UserNotFoundException("User not found"));
 
        // Create a new Like object
        Likes like = new Likes();
        like.setPost(post);  // Set the Post
        like.setUser(user);  // Set the User
 
        // Save the Like, let the database generate the likeID
        return likesRepository.save(like);
    }
 
    // Retrieve all likes on posts created by a specific user
    @Override
    public List<Likes> getLikeBySpecfUser(int UserID) {
        Users user = usersRepository.findById(UserID).orElse(null);
        if (user != null) {
            List<Posts> userPosts = postsRepository.findByUser(user);
            if (userPosts != null && !userPosts.isEmpty()) {
                List<Likes> allLikes = new ArrayList<>();
                for (Posts post : userPosts) {
                    List<Likes> likeList = likesRepository.findByPost(post);
                    if (likeList != null) {
                        allLikes.addAll(likeList);
                    }
                }
                return allLikes.isEmpty() ? null : allLikes;
            }
        }
        return null;  // User not found or no posts
    }
 
    // Get likes info by likeID
    @Override
    public Likes getByLikeID(int likeId) {
        return likesRepository.findById(likeId).orElse(null);  // Return null if like not found
    }
 
    // Remove a like from a post
    @Override
    public void deleteLikes(int likeID) {
        likesRepository.deleteById(likeID);
    }
 
    // Retrieve all likes given by a specific user
    @Override
    public List<Likes> getLikesByUser(Users user) {
        List<Likes> allLikes = likesRepository.findByUser(user);
        return allLikes.isEmpty() ? null : allLikes;  // Return null if no likes found
    }
 
    // Retrieve a like by postId and userId
    @Override
    public Likes getLikeByPostIdAndUserId(int postId, int userId) {
        return likesRepository.findByPostIdAndUserId(postId, userId);  // Return null if no like found
    }

	@Override
	public Likes savelike(int likeID, int postId, int userId) {
		// TODO Auto-generated method stub
		return null;
	}
}
