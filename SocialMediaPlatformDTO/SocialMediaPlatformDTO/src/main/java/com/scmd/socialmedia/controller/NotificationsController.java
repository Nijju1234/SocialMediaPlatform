package com.scmd.socialmedia.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scmd.socialmedia.dto.NotificationsDTO;
import com.scmd.socialmedia.entity.Notifications;
import com.scmd.socialmedia.services.NotificationsService;


@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/api/users")
public class NotificationsController 
{
	@Autowired
	NotificationsService notificationsService;

	// Retrieve notification of user by notification Id
//	@GetMapping("/{userID}/notifications/{notificationID}")
//	public ResponseEntity<Map<String, Object>> getNotification(@PathVariable int userID,@PathVariable int notificationID) 
//	{
//		NotificationsDTO notification = notificationsService.notificationByUser(userID, notificationID);
//		Map<String, Object> response = new LinkedHashMap<>();
//		response.put("status", "success");
//		// response.put("message", "Notification of specific user retrieved
//		// successfully.");
//		response.put("data", notification);
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}
//
//	// Mark notification as read
//	@PutMapping("/{userID}/notifications/mark-read/{notificationID}")
//	public ResponseEntity<Object> changeMessageType(@RequestBody Notifications obj, @PathVariable("userID") int userID,
//			@PathVariable("notificationID") int notificationID) 
//	{
//		NotificationsDTO user = notificationsService.updateNotification(obj.getContent(), notificationID);
//		Map<String, Object> response = new LinkedHashMap<>();
//		response.put("status", "success");
//		response.put("message", "Notification marked as read successfully");
//		// response.put("data", user);
//		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
//	}
//
//	// Retrieve all notifications for a specific user by userId
//	@GetMapping("/{userID}/notification")
//	public ResponseEntity<Map<String, Object>> getNotifications(@PathVariable int userID) 
//	{
//		List<NotificationsDTO> notifications = notificationsService.getNotificationsByUser(userID);
//		Map<String, Object> response = new LinkedHashMap<>();
//		response.put("status", "success");
//		response.put("message", "Notification of specific user retrieved successfully");
//		response.put("data", notifications);
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}
//
//	// Delete Notification
//	@DeleteMapping("/{userID}/notifications/delete/{notificationID}")
//	public ResponseEntity<Object> deleteNotification(@PathVariable("userID") int userID,
//			@PathVariable("notificationID") int notificationID) 
//	{
//		notificationsService.deleteNotification(notificationID);
//		Map<String, Object> response = new LinkedHashMap<>();
//		response.put("status", "success");
//		response.put("message", "Notification deleted successfully");
//		return ResponseEntity.ok().body(response);
//	}
	
	@GetMapping("/{userID}/notifications/{notificationID}")
	// Retrieve a specific notification by userId and notificationId
	public ResponseEntity<Map<String, Object>> getNotification(@PathVariable int userID, @PathVariable int notificationID) {
	    NotificationsDTO notification = notificationsService.notificationByUser(userID, notificationID);
	    Map<String, Object> response = new LinkedHashMap<>();
	    response.put("status", "success");
	    response.put("data", notification);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/{userID}/notifications/mark-read/{notificationID}")
	// Mark notification as read
	public ResponseEntity<Object> changeMessageType(@RequestBody Notifications obj, @PathVariable("userID") int userID,
	                                                @PathVariable("notificationID") int notificationID) {
	    NotificationsDTO user = notificationsService.updateNotification(obj.getContent(), notificationID);
	    Map<String, Object> response = new LinkedHashMap<>();
	    response.put("status", "success");
	    response.put("message", "Notification marked as read successfully");
	    return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}

	@GetMapping("/{userID}/notifications")  // This is for getting all notifications.
	public ResponseEntity<Map<String, Object>> getNotifications(@PathVariable int userID) {
	    List<NotificationsDTO> notifications = notificationsService.getNotificationsByUser(userID);
	    Map<String, Object> response = new LinkedHashMap<>();
	    response.put("status", "success");
	    response.put("message", "Notification of specific user retrieved successfully");
	    response.put("data", notifications);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}


	@DeleteMapping("/{userID}/notifications/delete/{notificationID}")
	// Delete a notification
	public ResponseEntity<Object> deleteNotification(@PathVariable("userID") int userID,
	                                                @PathVariable("notificationID") int notificationID) {
	    notificationsService.deleteNotification(notificationID);
	    Map<String, Object> response = new LinkedHashMap<>();
	    response.put("status", "success");
	    response.put("message", "Notification deleted successfully");
	    return ResponseEntity.ok().body(response);
	}

}
