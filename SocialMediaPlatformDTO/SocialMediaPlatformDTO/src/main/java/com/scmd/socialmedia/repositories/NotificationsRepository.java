package com.scmd.socialmedia.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.scmd.socialmedia.entity.Notifications;
import com.scmd.socialmedia.entity.Users;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, Integer> 
{
	List<Notifications> findByUser(Users user);
	Optional<Notifications> findByUserAndNotificationID(Users user, int notificationID);
}
