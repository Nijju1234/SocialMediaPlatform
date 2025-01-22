package com.scmd.socialmedia.dto;


import org.hibernate.validator.internal.engine.groups.Group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scmd.socialmedia.entity.Groups;
import com.scmd.socialmedia.entity.Users;
import com.scmd.socialmedia.repositories.UsersRepository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GroupDTO {

   private int groupID;

   @NotBlank(message = "Group name is required")
   private String groupName;

   @NotNull(message = "Admin ID is required")
   private int adminID;


   @JsonIgnore
   private boolean isUserRemovedFromGroup = false;

   // Getters and Setters

   public int getGroupID() {
       return groupID;
   }

   public void setGroupID(int groupID) {
       this.groupID = groupID;
   }

   public String getGroupName() {
       return groupName;
   }

   public void setGroupName(String groupName) {
       this.groupName = groupName;
   }

   public int getAdminID() {
       return adminID;
   }

   public void setAdminID(int adminID) {
       this.adminID = adminID;
   }

   public boolean isUserRemovedFromGroup() {
       return isUserRemovedFromGroup;
   }

   public void setUserRemovedFromGroup(boolean isUserRemovedFromGroup) {
       this.isUserRemovedFromGroup = isUserRemovedFromGroup;
   }

   // Converts this DTO to the Entity Group
   public Groups toEntity(UsersRepository usersRepository) {
       Groups group = new Groups();
       group.setGroupID(this.groupID);
       group.setGroupName(this.groupName);

       // Fetch the Users entity using the adminID
       Users admin = usersRepository.findById(this.adminID).orElseThrow(() ->
               new IllegalArgumentException("Admin user with ID " + this.adminID + " not found"));

       // Set the fetched Users entity as the admin
       group.setAdmin(admin);


       return group;
   }
}
