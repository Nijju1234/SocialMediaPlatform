package com.scmd.socialmedia.services;

import java.util.List;
import java.util.Optional;

import com.scmd.socialmedia.dto.NotificationsDTO;
import com.scmd.socialmedia.entity.Notifications;

public interface NotificationsService 
{
	NotificationsDTO notificationByUser(int userID, int notificationID); // Retrieve notification of user by notification Id
	
	
	NotificationsDTO updateNotification(String content,int notificationID); // Mark notification as read
	
	
	List<NotificationsDTO> getNotificationsByUser(int userID); 
	
	String softDeleteNotification(int notificationID); //soft delete
	
	
	void deleteNotification(int notificationId);
}
