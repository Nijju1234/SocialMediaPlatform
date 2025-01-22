package com.scmd.socialmedia.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scmd.socialmedia.dto.GroupDTO;
import com.scmd.socialmedia.entity.Groups;
import com.scmd.socialmedia.entity.Messages;
import com.scmd.socialmedia.entity.Users;
import com.scmd.socialmedia.exception.GroupsIdNotFoundException;
import com.scmd.socialmedia.services.GroupsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@Validated

public class GroupsController {
	@Autowired
	GroupsService groupsService;

	// Retrieve a list of all groups
	@GetMapping("/groups")
	public ResponseEntity<Object> getAllGroupsData() {
		List<GroupDTO> groupDTOs = groupsService.getAllGroupsData();
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("status", "success");
		response.put("message", "Groups retrieved successfully.");
		response.put("data", groupDTOs);
		return ResponseEntity.ok().body(response);
	}
		// Retrieve a group by its ID.
	@GetMapping("/groups/{groupId}")
	public ResponseEntity<Object> getGroupDataById(@PathVariable("groupId") int groupId) {
		GroupDTO groupDTO = groupsService.getGroupByID(groupId); // Retrieve the group as a DTO
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("status", "success");
		response.put("message", "Group details retrieved successfully.");
		response.put("data", groupDTO);
		return ResponseEntity.ok().body(response);
	}

	// Create a new group
	@PostMapping("/groups")
	public ResponseEntity<Object> addData(@Valid @RequestBody Groups group) {
		Groups savedGroup = groupsService.addGroups(group);
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("status", "success");
		response.put("message", "Groups created successfully.");
		response.put("data", savedGroup);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// Update an existing group by its ID.
	@PutMapping("/groups/{groupId}")
	public ResponseEntity<Object> updateGroupName(@Valid @RequestBody Groups obj,
			@PathVariable("groupId") int groupID) {
		Groups grp = groupsService.updateGroupName(obj.getGroupName(), groupID);
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("status", "success");
		response.put("message", "Group updated successfully");
		response.put("data", grp);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);

	}

	// delete a group by its id
	@DeleteMapping("/groups/{groupId}")
	public ResponseEntity<Object> deleteGroup(@PathVariable("groupId") int groupID) {
		// Call the service method to soft delete the group
		groupsService.deleteGroup(groupID);

		// Prepare the response to indicate success
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("status", "success");
		response.put("message", "Group marked as deleted successfully");

		return ResponseEntity.ok().body(response);
	}

	// working
	// remove user from group
	// Controller method to remove user from group
	@DeleteMapping("/groups/{groupId}/leave/{userId}")
	public ResponseEntity<Object> removeUserFromGroup(@PathVariable("groupId") int groupID,
			@PathVariable("userId") int userID) {
		groupsService.removeUserFromGroup(groupID, userID);
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("status", "success");
		response.put("message", "User successfully left the group");
		return ResponseEntity.ok().body(response);
	}

	// Join a user to a group
	@PostMapping("/groups/{groupId}/join/{userId}")
	public ResponseEntity<Object> joinusertogroup(@Valid @PathVariable("groupId") int groupId,
			@PathVariable("userId") int userid) {
		Groups addUser = groupsService.addmember(groupId, userid);
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("status", "success");
		response.put("message", "User successfully joined the group.");
		response.put("data", addUser);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// Retrieve all groups a user is a member of.
	@GetMapping("/users/{userId}/groups")
	public ResponseEntity<Object> getGroupsOfUser(@PathVariable("userId") int userID) {
		List<Groups> groups = groupsService.userGroups(userID);
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("status", "success");
		response.put("message", "User's groups retrieved successfully");
		response.put("data", groups);
		return ResponseEntity.ok().body(response);
	}

	// Retrieve all messages in a group
	@GetMapping("/groups/{groupId}/messages")
	public ResponseEntity<Object> getGroupMessages(@PathVariable("groupId") int groupID) {
		List<Messages> messages = groupsService.groupMessages(groupID);
		System.out.println("messages: -----" + messages);
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("status", "success");
		response.put("message", "Group messages retrieved successfully");
		response.put("data", messages);
		return ResponseEntity.ok().body(response);
	}

	// Send a message to a group
	@PostMapping("/groups/{groupId}/messages/send/{userId}")
	public ResponseEntity<Object> sendMessageToGroup(@Valid @RequestBody Messages messageObject,
			@PathVariable("groupId") int groupID, @PathVariable("userId") int userID) {
		Messages message = groupsService.sendMessageToGroup(groupID, userID, messageObject);
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("status", "success");
		response.put("message", "Message sent to the group successfully");
		response.put("data", message);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// Retrieve all groups where a user's friends are members
	@GetMapping("/users/{userID}/friends/groups")
	public ResponseEntity<Object> getGroupsByUserFriends(@PathVariable int userID) {
		Set<Groups> groups = groupsService.getGroupsByUserFriends(userID);
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("status", "success");
		response.put("message", "User's friends' groups retrieved successfully.");
		response.put("data", groups);
		return ResponseEntity.ok().body(response);
	}

	// Retrieve all friends who are members of a specific group
	@GetMapping("/groups/{groupId}/friends")
	public ResponseEntity<Object> getFriendsOfGroupMembers(@PathVariable("groupId") int groupId) {
		List<Users> friends = groupsService.retrieveFriendsOfGroupMembers(groupId);
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("status", "success");
		response.put("message", "Group's friends retrieved successfully.");
		response.put("data", friends);
		return ResponseEntity.ok(response);
	}

	// Retrieve all members of a specific group
   @GetMapping("groups/{groupId}/members")
   public ResponseEntity<Object> getAllMembersOfGroup(@PathVariable("groupId") int groupId) {
       // Call the service method to retrieve the group data
       GroupDTO groupDTO = groupsService.retrieveAllMembersOfGroup(groupId);

       // Prepare the response map
       Map<String, Object> response = new LinkedHashMap<>();
       response.put("status", "success");
       response.put("message", "Group members retrieved successfully.");
       response.put("data", groupDTO);

       return ResponseEntity.ok().body(response);
   }	
	

	// Remove a user from a group (Soft Delete)
	@DeleteMapping("/groups/{groupID}/members/remove/{userId}")
	public ResponseEntity<Object> deleteuserfromGroups(@PathVariable("groupID") int groupID,
	        @PathVariable("userId") int userId) {
	    // Retrieve the group
	    GroupDTO group = groupsService.getGroupByID(groupID);

	    // Check if group exists
	    if (group == null) {
	        throw new GroupsIdNotFoundException("Invalid GroupId: " + groupID);
	    }

	    // Call the service to remove the user from the group (soft deletion)
	    groupsService.removeUserFromGroup(groupID, userId);

	    // Prepare the response
	    Map<String, Object> response = new LinkedHashMap<>();
	    response.put("status", "success");
	    response.put("message", "User successfully removed from the group.");
	    return ResponseEntity.ok(response);
	}

}