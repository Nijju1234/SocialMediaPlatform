package com.scmd.socialmedia.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scmd.socialmedia.entity.Comments;
import com.scmd.socialmedia.entity.Posts;

public interface CommentsRepository extends JpaRepository<Comments, Integer> {

	List<com.scmd.socialmedia.entity.Comments> findByPost(Posts post);

}