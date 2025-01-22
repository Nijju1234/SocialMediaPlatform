package com.scmd.socialmedia.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.scmd.socialmedia.entity.Friends;
import com.scmd.socialmedia.entity.FriendsStatus;
import com.scmd.socialmedia.entity.Users;

import ch.qos.logback.core.status.Status;

@Repository
public interface FriendsRepository extends JpaRepository<Friends, Integer> {

    // Find all friends for a user
    List<Friends> findByUser1(Users userID);
    List<Friends> findByStatus(FriendsStatus status);
    // Find a friend by their friendship ID
    Friends findByFriendshipID(int friendshipID);
    
    // Find friendship by userId and friendId
    @Query("SELECT f FROM Friends f WHERE f.user1.id = :userId AND f.user2.id = :friendId")
    Friends findByUserIdAndFriendId(@Param("userId") int userId, @Param("friendId") int friendId);
    
    // Find friendship status by userId and friendId (returns FriendsStatus, not String)
    @Query("SELECT f.status FROM Friends f WHERE f.user1.id = :userId AND f.user2.id = :friendId")
    FriendsStatus findFriendshipStatus(@Param("userId") int userId, @Param("friendId") int friendId);
    
    // Find pending friend requests for a user
    @Query("SELECT f FROM Friends f WHERE f.user2.id = :userId AND f.status = :status")
    List<Friends> findByUserPendingRequests(@Param("userId") int userId, @Param("status") FriendsStatus status);


    // Find accepted friendships for a user
    @Query("SELECT f FROM Friends f WHERE (f.user1.id = :userId OR f.user2.id = :userId) AND f.status = :status")
    List<Friends> findFriendshipsByUserId(@Param("userId") int userId, @Param("status") FriendsStatus status);
    
 // Find pending requests for a user
    @Query("SELECT f FROM Friends f WHERE f.user2.id = :userId AND f.status = 'pending'")
    List<Friends> findByUserPendingRequests(@Param("userId") int userId);
    
    List<Friends> findByStatus(Status status);
 
    
    @Query("SELECT f FROM Friends f WHERE (f.user1.id = :userId OR f.user2.id = :userId) AND f.status = 'accepted'")
    List<Friends> findFriendshipsByUserId(@Param("userId") int userId);
    
    
}
