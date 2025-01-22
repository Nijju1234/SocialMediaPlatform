package com.scmd.socialmedia.repositories;

import java.util.List;
import java.util.Optional;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
 
import com.scmd.socialmedia.entity.Groups;
import com.scmd.socialmedia.entity.Users;
 
@Repository
 
public interface GroupsRepository extends JpaRepository<Groups, Integer>
{
	  @Query("SELECT u FROM Users u WHERE u.userID = (SELECT g.admin.userID FROM Groups g WHERE g.groupID = :groupID)")
	  List<Users> findUsersByGroupID(@Param("groupID") int groupID);
	  @Query("SELECT u FROM Users u WHERE u.userID IN (SELECT f.user1.userID FROM Friends f WHERE f.user2.userID IN (SELECT g.admin.userID FROM Groups g WHERE g.groupID = :groupID) AND f.status = 'accepted') OR u.userID IN (SELECT f.user2.userID FROM Friends f WHERE f.user1.userID IN (SELECT g.admin.userID FROM Groups g WHERE g.groupID = :groupID) AND f.status = 'accepted')")
     List<Users> findFriendsOfGroupMembers(@Param("groupID") int groupID);
	  List<Groups> findByAdmin(Users user);
	    Optional<Groups> findByGroupIDAndAdminAndIsUserRemovedFromGroupFalse(int groupID, Users admin);
 
}		
