package com.scmd.socialmedia.serviceImplementation;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scmd.socialmedia.dto.GroupDTO;
import com.scmd.socialmedia.entity.Friends;
import com.scmd.socialmedia.entity.FriendsStatus;
import com.scmd.socialmedia.entity.Groups;
import com.scmd.socialmedia.entity.Messages;
import com.scmd.socialmedia.entity.Users;
import com.scmd.socialmedia.exception.FriendsNotFoundException;
import com.scmd.socialmedia.exception.GroupIdAlreadyExistException;
import com.scmd.socialmedia.exception.GroupNotFoundException;
import com.scmd.socialmedia.exception.GroupsIdNotFoundException;
import com.scmd.socialmedia.exception.InvalidGroupsBodyException;
import com.scmd.socialmedia.exception.UserIdNotFoundException;
import com.scmd.socialmedia.exception.UserNotFoundInGroupException;
import com.scmd.socialmedia.exception.UserNotInGroupException;
import com.scmd.socialmedia.repositories.GroupsRepository;
import com.scmd.socialmedia.repositories.MessagesRepository;
import com.scmd.socialmedia.repositories.UsersRepository;
import com.scmd.socialmedia.services.GroupsService;
 
@Service
public class GroupsServiceImplementation implements GroupsService
{
	@Autowired
	GroupsRepository groupsRepository;
	@Autowired
	UsersRepository usersRepository;
	
	@Autowired
	MessagesRepository messagesRepository;
 
	
	// Retrieve a list of all groups
    @Override
    public List<GroupDTO> getAllGroupsData() {
        List<Groups> groups = groupsRepository.findAll();
        return groups.stream()
                .map(group -> {
                    GroupDTO groupDTO = new GroupDTO();
                    groupDTO.setGroupID(group.getGroupID());
                    groupDTO.setGroupName(group.getGroupName());
                    groupDTO.setAdminID(group.getAdmin().getUserID());
                    return groupDTO;
                })
                .collect(Collectors.toList());
    }
 
    // Retrieve a group by its ID
    @Override
    public GroupDTO getGroupByID(int groupId) {
        Groups group = groupsRepository.findById(groupId).orElse(null);
        if (group == null) {
            throw new GroupsIdNotFoundException("Invalid GroupId : " + groupId); // Custom exception if not found
        }
 
        // Convert Groups entity to GroupDTO
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setGroupID(group.getGroupID());
        groupDTO.setGroupName(group.getGroupName());
        groupDTO.setAdminID(group.getAdmin().getUserID()); // Set the admin's user ID
        groupDTO.setUserRemovedFromGroup(false); // Assuming this is false by default, you can modify it as needed.
 
        return groupDTO;
    }
    
		//create a new group
		public Groups addGroups(Groups group) {
		    // Check if group ID already exists
		    int id = group.getGroupID();
		    if (groupsRepository.existsById(id)) {
		        throw new GroupIdAlreadyExistException("Group already exists with ID " + id);
		    }
 
		    // Validate groupName and admin
		    String groupName = group.getGroupName();
		    Users admin = group.getAdmin();
		    
		 // Step 1: Persist the Users entity (admin)
		    Users adminUser = new Users();
		    adminUser.setUsername(group.getAdmin().getUsername());
		    adminUser.setEmail(group.getAdmin().getEmail());
		    adminUser.setPassword(group.getAdmin().getPassword());
		    usersRepository.save(adminUser);  // Ensure the admin is saved before using it in Group
 
		    // Step 2: Create and persist the Groups entity
		    group.setGroupName(group.getGroupName());
		    group.setAdmin(adminUser);  // Assign the already persisted admin
 
 
		    // Save the group (this will also save the admin if cascading is enabled)
		    return groupsRepository.save(group);
		}
 
 
		// Update an existing group by its ID.
		public Groups updateGroupName(String groupName, int groupID)
		{
			Groups grp = groupsRepository.findById(groupID).orElse(null);
			if (grp == null)
			{
				throw new GroupNotFoundException("Group with id " + groupID + " does not exists");
			}
			grp.setGroupName(groupName);
			groupsRepository.save(grp);
			return grp;
		}
		
		
		// delete group by its id
		public void deleteGroup(int groupID) {
	        // Fetch the group from the repository
	        Groups group = groupsRepository.findById(groupID).orElse(null);
 
	        if (group == null) {
	            throw new GroupNotFoundException("Group with id: " + groupID + " does not exist");
	        }
 
	        // Mark the group as deleted (soft delete)
	        group.setDeleted(true);
 
	        // Save the updated group back to the database
	        groupsRepository.save(group);
	    }
 
		
		// remove user from group
//		change............................................................................
//		public void removeUserFromGroup(int groupID, int userID) {
//		    // Assuming userID corresponds to the admin (you can modify this based on your logic)
//		    Users user = usersRepository.findById(userID).orElseThrow(() -> new UserNotFoundInGroupException("User not found"));
//		    Optional<Groups> groupOptional = groupsRepository.findByGroupIDAndAdminAndIsUserRemovedFromGroupFalse(groupID, user);
//		    
//		    if (!groupOptional.isPresent()) {
//		        throw new GroupNotFoundException("Group or User not found in the group.");
//		    }
// 
//		    Groups group = groupOptional.get();
//		    // Soft delete the user from the group
//		    group.setUserRemovedFromGroup(true);
//		    groupsRepository.save(group);
//		}
		public void removeUserFromGroup(int groupID, int userID) {
		    // Fetch the user from the users repository
		    Users user = usersRepository.findById(userID)
		            .orElseThrow(() -> new UserNotFoundInGroupException("User not found with ID: " + userID));

		    // Fetch the group from the groups repository
		    Groups group = groupsRepository.findById(groupID)
		            .orElseThrow(() -> new GroupNotFoundException("Group with ID: " + groupID + " not found"));

		    // Check if the user is part of the group
		    boolean isUserInGroup = group.getUsers().contains(user);

		    if (!isUserInGroup) {
		        throw new UserNotInGroupException("User with ID: " + userID + " is not a member of the group.");
		    }

		    // Remove the user from the group
		    group.getUsers().remove(user);  // Remove the user from the group's users set

		    // Save the updated group back to the database
		    groupsRepository.save(group);
		}


 
		
		// Add a user as a member to a group. or Join a user to a group
		public Groups addmember(int groupId, int userid)
		{
			Groups group = groupsRepository.findById(groupId).orElse(null);
			Users users = usersRepository.findById(userid).orElse(null);
			if (users == null && users == null)
			{
				throw new UserIdNotFoundException("User is not found with id " + userid);
			}
			group.setAdmin(users);
			return groupsRepository.save(group);
		}
 
		// Retrieve all groups a user is a member of.
		public List<Groups> userGroups(int userID)
		{
			Users user = usersRepository.findById(userID).get();
			List<Groups> groups = user.getAdminGroups();
			if (groups.isEmpty())
			{
				throw new GroupNotFoundException("No groups found for user with Id: " + userID);
			}
			return groups;
		}

        // Retreive all messages in a group
		public List<Messages> groupMessages(int groupID) {
		    Groups group = groupsRepository.findById(groupID).orElse(null);
		    if (group == null) {
		        throw new GroupNotFoundException("Group with id: " + groupID + " does not exists");
		    }
		    if (group.getAdmin() == null) {
		        throw new InvalidGroupsBodyException("Admin is not set for this group.");
		    }
		    List<Messages> messages = group.getAdmin().getSentMessages();
		    System.out.println("Before returning result:---------------------- "+messages);
		    messages.addAll(group.getAdmin().getReceivedMessages());
		    
		    return messages;
		}
 
		// send message to a group
		public Messages sendMessageToGroup(int groupID, int userID, Messages message)
		{
			Groups group = groupsRepository.findById(groupID).get();
			if (group == null)
			{
				throw new GroupNotFoundException("Group with id: " + groupID + " does not exists");
			}
			Messages mes = new Messages();
			mes.setMessageID(message.getMessageID());
			Users user = usersRepository.findById(userID).get();
			if (user.getUserID() != group.getAdmin().getUserID())
			{
				throw new UserNotInGroupException("no user found with Id: " + userID);
			}
			mes.setSender(user);
			mes.setReceiver(group.getAdmin());
			mes.setMessageText(message.getMessageText());
			mes.setTimestamp(new Timestamp(System.currentTimeMillis()));
			messagesRepository.save(mes);
			return mes;
		}
		
	// Retrieve all groups where a user's friends are members
 
////		public Set<Groups> getGroupsByUserFriends(int userID)
////		{
////			Users user = usersRepository.findById(userID).get();
////			List<FriendsDTO> friends = user.getUserID1();
////			friends.addAll(user.getUserID2());
////			Set<Groups> groupsWithFriends = new HashSet<>();
////			for (Friends friend : friends) {
////				if (friend.getStatus() == FriendsStatus.ACCEPTED)
////				{
////					if (friend.getUser1().getUserID() != userID)
////					{
////						groupsWithFriends.addAll(friend.getUser1().getAdminGroups());
////					}
////					if (friend.getUser2().getUserID() != userID)
////					{
////						groupsWithFriends.addAll(friend.getUser2().getAdminGroups());
////					}
////				}
////			}
////			groupsWithFriends.remove(user.getAdminGroups().get(0));
////			{
////				return groupsWithFriends;
////			}
////		}
// 
//		// Retrieve all friends who are members of a specific group
		public List<Users> retrieveFriendsOfGroupMembers(int groupID)
		{
			List<Users> friends = groupsRepository.findFriendsOfGroupMembers(groupID);
			if (friends.isEmpty())
			{
				throw new FriendsNotFoundException("Friends are not found with GroupID: " + groupID);
			}
			return friends;
		}
		
		
//		// Retrieve all members of a specific group
		 @Override
		    public GroupDTO retrieveAllMembersOfGroup(int groupId) {
		        // Fetch the group by groupId to ensure the group exists
		        Groups group = groupsRepository.findById(groupId)
		                .orElseThrow(() -> new GroupNotFoundException("Group not found with ID: " + groupId));
 
		        // Fetch all the users that belong to this group
		        List<Users> usersInGroup = groupsRepository.findUsersByGroupID(groupId);
 
		        if (usersInGroup == null || usersInGroup.isEmpty()) {
		            throw new UserNotFoundInGroupException("No users found in group with ID: " + groupId);
		        }
 
		        // Create the GroupDTO with group information
		        GroupDTO groupDTO = new GroupDTO();
		        groupDTO.setGroupID(group.getGroupID());
		        groupDTO.setGroupName(group.getGroupName());
		        groupDTO.setAdminID(group.getAdmin().getUserID());
		    
		        groupDTO.setUserRemovedFromGroup(false); // Or whatever logic you want here
 
		        return groupDTO;  // Return the DTO with the group information
		 }

@Override
public Set<Groups> getGroupsByUserFriends(int userID) {
	Users user = usersRepository.findById(userID).get();
	List<Friends> friends = user.getUserID1();
	friends.addAll(user.getUserID2());
	Set<Groups> groupsWithFriends = new HashSet<>();
	for (Friends friend : friends) {
	    if (friend.getStatus() == FriendsStatus.accepted) {
	        if (friend.getUser1().getUserID() != userID) {
	            groupsWithFriends.addAll(friend.getUser1().getAdminGroups());
	        }
	        if (friend.getUser2().getUserID() != userID) {
	            groupsWithFriends.addAll(friend.getUser2().getAdminGroups());
	        }
	    }
	}
	groupsWithFriends.remove(user.getAdminGroups().get(0));
	{
	    return groupsWithFriends;
	}

}
		
		
}