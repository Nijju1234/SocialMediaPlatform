package com.scmd.socialmedia.services;
 
import java.util.List;
 
import com.scmd.socialmedia.dto.PostsDTO;
import com.scmd.socialmedia.dto.PostsDTO1;
import com.scmd.socialmedia.entity.Posts;
 
 
public interface PostsService 
{
 
 
	Posts savepost(PostsDTO postDTO);
 
	Posts getByPostID(int PostID);
 
	Posts updatePosts(int postId, PostsDTO postDTO);
 
	void deletePostById(int PostID);
 
	List<Posts> getAllPostsOfFriends(int userId);

	List<PostsDTO1> getAllPostsAsDTO();
 
}