package com.scmd.socialmedia.repositories;
 
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.scmd.socialmedia.entity.Posts;
import com.scmd.socialmedia.entity.Users;
 
@Repository
public interface PostsRepository extends JpaRepository<Posts, Integer> 
{
    List<Posts> findByUser(Users user); 
    @Query("SELECT p FROM Posts p WHERE p.user.id = :userId")
    List<Posts> findByUserId(@Param("userId") int userId);
}