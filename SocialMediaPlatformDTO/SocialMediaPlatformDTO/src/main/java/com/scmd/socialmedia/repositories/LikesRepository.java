package com.scmd.socialmedia.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scmd.socialmedia.entity.Likes;
import com.scmd.socialmedia.entity.Posts;
import com.scmd.socialmedia.entity.Users;


public interface LikesRepository extends JpaRepository<Likes, Integer> 
{
	List<Likes> findByPost(Posts post); 
	List<Likes> findByUser(Users user);
	
	
	@Query("SELECT l FROM Likes l WHERE l.post.id = :postId AND l.user.id = :userId")
	Likes findByPostIdAndUserId(@Param("postId")int postId,@Param("userId") int userId);
}
