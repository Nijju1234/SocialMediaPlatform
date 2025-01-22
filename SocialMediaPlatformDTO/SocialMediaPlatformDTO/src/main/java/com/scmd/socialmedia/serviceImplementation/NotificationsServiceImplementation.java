package com.scmd.socialmedia.serviceImplementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scmd.socialmedia.dto.NotificationsDTO;
import com.scmd.socialmedia.entity.Notifications;
import com.scmd.socialmedia.entity.Users;
import com.scmd.socialmedia.exception.NotificationNotFoundException;
import com.scmd.socialmedia.repositories.NotificationsRepository;
import com.scmd.socialmedia.repositories.UsersRepository;
import com.scmd.socialmedia.services.NotificationsService;

@Service
public class NotificationsServiceImplementation implements NotificationsService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    NotificationsRepository notificationsRepository;

    // Helper method to convert Notifications entity to NotificationsDTO
    private NotificationsDTO convertToDTO(Notifications notification) {
        return new NotificationsDTO(
                notification.getNotificationID(),
                notification.getUser().getUserID(),
                notification.getContent(),
                notification.getTimestamp()  // We pass the Timestamp directly from the entity
        );
    }

    // Retrieve notification of user by notification Id
    @Override
    public NotificationsDTO notificationByUser(int userID, int notificationID) {
        Users user = usersRepository.findById(userID)
                .orElseThrow(() -> new NotificationNotFoundException("User with ID " + userID + " not found."));
        
        Notifications notification = notificationsRepository.findByUserAndNotificationID(user, notificationID)
                .orElseThrow(() -> new NotificationNotFoundException("Notification with ID " + notificationID + " not found for user with ID: " + userID));

        // Convert the entity to DTO
        return convertToDTO(notification);
    }

    // Update content in notification or Mark as Read
    @Override
    public NotificationsDTO updateNotification(String content, int notificationID) {
        Notifications notification = notificationsRepository.findById(notificationID)
                .orElseThrow(() -> new NotificationNotFoundException("No notification found with id: " + notificationID + " in database"));

        notification.setContent(content);
        notificationsRepository.save(notification);

        // Convert the updated entity to DTO
        return convertToDTO(notification);
    }

    // Retrieve all notifications for a specific user by userId
    @Override
    public List<NotificationsDTO> getNotificationsByUser(int userID) {
        Users user = usersRepository.findById(userID)
                .orElseThrow(() -> new NotificationNotFoundException("User with ID " + userID + " not found."));
        
        List<Notifications> notifications = notificationsRepository.findByUser(user);
        if (notifications.isEmpty()) {
            throw new NotificationNotFoundException("No notifications found for user with ID: " + userID);
        }

        // Convert all notifications to DTOs
        return notifications.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Delete Notification
    @Override
    public void deleteNotification(int notificationId) {
        // Validate that the notification exists
        Optional<Notifications> existingNotificationOptional = notificationsRepository.findById(notificationId);

        if (existingNotificationOptional.isEmpty()) {
            throw new NotificationNotFoundException("Notification with ID " + notificationId + " not found for deletion.");
        }

        // Mark the notification as deleted
        Notifications existingNotification = existingNotificationOptional.get();
        existingNotification.setDeleted(true); // Assuming the Notification entity has a 'deleted' field

        // Save the updated notification
        notificationsRepository.save(existingNotification);
    }

    // Soft delete Notification
    @Override
    public String softDeleteNotification(int notificationID) {
        Notifications notification = notificationsRepository.findById(notificationID)
                .orElseThrow(() -> new NotificationNotFoundException("No notification found with id: " + notificationID + " in database"));
        
        notification.setDeleted(true); // Assuming there's a 'deleted' field in your Notification entity
        notificationsRepository.save(notification);
        return "Notification with ID " + notificationID + " soft-deleted successfully.";
    }
}
