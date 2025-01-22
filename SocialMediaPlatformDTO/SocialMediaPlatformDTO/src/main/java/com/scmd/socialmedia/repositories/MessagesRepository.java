package com.scmd.socialmedia.repositories;
 
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.scmd.socialmedia.entity.Messages;
import com.scmd.socialmedia.entity.Users;
 
@Repository
public interface MessagesRepository extends JpaRepository<Messages, Integer> 
{
	List<Messages> findBySenderAndReceiver(Users userId1, Users userId2);
}
