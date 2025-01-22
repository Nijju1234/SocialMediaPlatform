package com.scmd.socialmedia.services;
 
import java.util.List;
import com.scmd.socialmedia.entity.Likes;
import com.scmd.socialmedia.entity.Users;
 
public interface LikesService 
{
	List<Likes> getLikeByPostID(int PostID); //Retrieve all likes on a specific post
 
	Likes savelike(int likeID, int postId, int userId); //Add likes to post
 
	Likes getByLikeID(int likeId); //Get likes info by likeID
 
	List<Likes> getLikeBySpecfUser(int UserID); //Retrieve all likes on posts created by a specific user
 
	void deleteLikes(int likeID); //Remove a like from a post
 
	List<Likes> getLikesByUser(Users user); //Retrieve all likes given by a specific user
	Likes getLikeByPostIdAndUserId(int postId, int userId);

	Likes saveLike(int postID, int userID);
}