package com.scmd.socialmedia.services;


import java.util.List;
import java.util.Set;

import com.scmd.socialmedia.dto.GroupDTO;
import com.scmd.socialmedia.entity.Groups;
import com.scmd.socialmedia.entity.Messages;
import com.scmd.socialmedia.entity.Users;
 
 
public interface GroupsService 
{
	 List<GroupDTO> getAllGroupsData(); // Retrieve a list of all groups
	 GroupDTO getGroupByID(int groupId); // Retrieve a group by ID and return it as a DTO
	 Groups addGroups(Groups group); // Create a new group
     Groups updateGroupName(String groupName, int groupID); 
	 void deleteGroup(int groupID);
	 void removeUserFromGroup(int groupID, int userID);
	 Groups addmember(int groupId, int userid);
	 List<Groups> userGroups(int userID); // Retrieve all groups a user is a member of. 
	 List<Messages> groupMessages(int groupID);// Retrieve all messages in a group
	 Set<Groups> getGroupsByUserFriends(int userID); // Retrieve all groups where a user's friends are members
	 List<Users> retrieveFriendsOfGroupMembers(int groupID); // Retrieve all friends who are members of a specific group
     GroupDTO retrieveAllMembersOfGroup(int groupId);
  	 Messages sendMessageToGroup(int groupID, int userID, Messages message); // send message to a group
	

}