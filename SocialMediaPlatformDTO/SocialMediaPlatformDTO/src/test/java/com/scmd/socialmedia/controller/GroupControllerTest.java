package com.scmd.socialmedia.controller;



import com.scmd.socialmedia.dto.GroupDTO;
import com.scmd.socialmedia.entity.Groups;
import com.scmd.socialmedia.entity.Messages;
import com.scmd.socialmedia.entity.Users;
import com.scmd.socialmedia.exception.GroupIdNotFoundException;
import com.scmd.socialmedia.services.GroupsService;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
 
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
 
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
 
class GroupsControllerTest {
 
    @Mock
    private GroupsService groupsService;
 
    @InjectMocks
    private GroupsController groupsController;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    @Test
    void testGetAllGroupsData() {
        List<GroupDTO> groupDTOs = Arrays.asList(new GroupDTO(), new GroupDTO());
        when(groupsService.getAllGroupsData()).thenReturn(groupDTOs);
 
        ResponseEntity<Object> response = groupsController.getAllGroupsData();
 
        assert(response.getStatusCode()).equals(HttpStatus.OK);
        assert(response.getBody() instanceof Map);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assert(responseBody.get("status")).equals("success");
        assert(responseBody.get("message")).equals("Groups retrieved successfully.");
        assert(((List<GroupDTO>) responseBody.get("data")).size() == 2);
    }
 
    @Test
    void testGetGroupDataById() {
        GroupDTO groupDTO = new GroupDTO();
        when(groupsService.getGroupByID(anyInt())).thenReturn(groupDTO);
 
        ResponseEntity<Object> response = groupsController.getGroupDataById(1);
 
        assert(response.getStatusCode()).equals(HttpStatus.OK);
        assert(response.getBody() instanceof Map);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assert(responseBody.get("status")).equals("success");
        assert(responseBody.get("message")).equals("Group details retrieved successfully.");
        assert(responseBody.get("data")).equals(groupDTO);
    }
 
    @Test
    void testAddGroup() {
        Groups group = new Groups();
        Groups savedGroup = new Groups();
        when(groupsService.addGroups(any(Groups.class))).thenReturn(savedGroup);
 
        ResponseEntity<Object> response = groupsController.addData(group);
 
        assert(response.getStatusCode()).equals(HttpStatus.CREATED);
        assert(response.getBody() instanceof Map);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assert(responseBody.get("status")).equals("success");
        assert(responseBody.get("message")).equals("Groups created successfully.");
        assert(responseBody.get("data")).equals(savedGroup);
    }
 
    @Test
    void testUpdateGroupName() {
        // Create a group and updated group with the same data
        Groups group = new Groups();
        group.setGroupName("Old Group Name"); // assuming group name is a field in the Groups entity
        
        Groups updatedGroup = new Groups();
        updatedGroup.setGroupName("New Group Name");
 
        // Mock the service call
        when(groupsService.updateGroupName(anyString(), anyInt())).thenReturn(updatedGroup);
 
        // Call the controller method
        ResponseEntity<Object> response = groupsController.updateGroupName(group, 1);
 
        // Assertions
        assert(response.getStatusCode()).equals(HttpStatus.ACCEPTED);
        assert(response.getBody() instanceof Map);
 
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assert(responseBody.get("status")).equals("success");
        assert(responseBody.get("message")).equals("Group updated successfully");
        
    }
 
 
    @Test
    void testDeleteGroup() {
        doNothing().when(groupsService).deleteGroup(anyInt());
 
        ResponseEntity<Object> response = groupsController.deleteGroup(1);
 
        assert(response.getStatusCode()).equals(HttpStatus.OK);
        assert(response.getBody() instanceof Map);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assert(responseBody.get("status")).equals("success");
        assert(responseBody.get("message")).equals("Group marked as deleted successfully");
    }
 
    @Test
    void testRemoveUserFromGroup() {
        doNothing().when(groupsService).removeUserFromGroup(anyInt(), anyInt());
 
        ResponseEntity<Object> response = groupsController.removeUserFromGroup(1, 1);
 
        assert(response.getStatusCode()).equals(HttpStatus.OK);
        assert(response.getBody() instanceof Map);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assert(responseBody.get("status")).equals("success");
        assert(responseBody.get("message")).equals("User successfully left the group");
    }
 
    @Test
    void testJoinUserToGroup() {
        Groups group = new Groups();
        when(groupsService.addmember(anyInt(), anyInt())).thenReturn(group);
 
        ResponseEntity<Object> response = groupsController.joinusertogroup(1, 1);
 
        assert(response.getStatusCode()).equals(HttpStatus.CREATED);
        assert(response.getBody() instanceof Map);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assert(responseBody.get("status")).equals("success");
        assert(responseBody.get("message")).equals("User successfully joined the group.");
        assert(responseBody.get("data")).equals(group);
    }
 
    @Test
    void testGetGroupsOfUser() {
        List<Groups> groups = Arrays.asList(new Groups(), new Groups());
        when(groupsService.userGroups(anyInt())).thenReturn(groups);
 
        ResponseEntity<Object> response = groupsController.getGroupsOfUser(1);
 
        assert(response.getStatusCode()).equals(HttpStatus.OK);
        assert(response.getBody() instanceof Map);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assert(responseBody.get("status")).equals("success");
        assert(responseBody.get("message")).equals("User's groups retrieved successfully");
        assert(((List<Groups>) responseBody.get("data")).size() == 2);
    }
 
    @Test
    void testDeleteUserFromGroup() {
        doNothing().when(groupsService).removeUserFromGroup(anyInt(), anyInt());
        when(groupsService.getGroupByID(anyInt())).thenReturn(new GroupDTO());
 
        ResponseEntity<Object> response = groupsController.deleteuserfromGroups(1, 1);
 
        assert(response.getStatusCode()).equals(HttpStatus.OK);
        assert(response.getBody() instanceof Map);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assert(responseBody.get("status")).equals("success");
        assert(responseBody.get("message")).equals("User successfully removed from the group.");
    }
 
    @Test
    void testDeleteUserFromGroup_GroupNotFound() {
        when(groupsService.getGroupByID(anyInt())).thenReturn(null);
 
        try {
            groupsController.deleteuserfromGroups(999, 1);
        } catch (Exception e) {
            assert(e instanceof GroupIdNotFoundException);
        }
    }
}






