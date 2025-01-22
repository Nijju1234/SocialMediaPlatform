package com.scmd.socialmedia.entity;
 

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "groups")
public class Groups implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "groupid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int groupID;

    @NotNull(message="Group name should not be null")
    @Column(name = "groupname")
    private String groupName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adminid")
    private Users admin;

    // ManyToMany relationship with Users
    @ManyToMany
    @JoinTable(
        name = "group_users", 
        joinColumns = @JoinColumn(name = "group_id"), 
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<Users> users = new HashSet<>();

    private boolean isDeleted = false;

    @Column(name = "is_user_removed_from_group", nullable = false)
    private boolean isUserRemovedFromGroup = false;

    public Groups() {}

    public Groups(int groupID, String groupName) {
        this.groupID = groupID;
        this.groupName = groupName;
    }

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

    public Users getAdmin() {
        return admin;
    }

    public void setAdmin(Users admin) {
        this.admin = admin;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean isUserRemovedFromGroup() {
        return isUserRemovedFromGroup;
    }

    public void setUserRemovedFromGroup(boolean isUserRemovedFromGroup) {
        this.isUserRemovedFromGroup = isUserRemovedFromGroup;
    }

    // Fixed the getUsers method
    public Set<Users> getUsers() {
        return users;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }

    public List<Messages> getMessages() {
        return null;  // Placeholder, adjust as necessary
    }
}


//@Entity
//@Table(name = "groups")
//public class Groups implements Serializable {
//	/**
//	* 
//	*/
//	private static final long serialVersionUID = 1L;
//	@Id
//	@Column(name = "groupid")
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private int groupID;
//	@NotNull(message="Group name should not be null")
//	@Column(name = "groupname")
//	private String groupName;
//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "adminid")
//	private Users admin;
//	
//	//change
//	@ManyToMany
//	@JoinTable(
//	    name = "group_users", 
//	    joinColumns = @JoinColumn(name = "group_id"), 
//	    inverseJoinColumns = @JoinColumn(name = "user_id"))
//	private Set<Users> users = new HashSet<>();
//
//
//	private boolean isDeleted = false;
//	@Column(name = "is_user_removed_from_group", nullable = false)
//	private boolean isUserRemovedFromGroup = false;
//
//	public Groups() {
//	}
//
//	public Groups(int groupID, String groupName) {
//		this.groupID = groupID;
//		this.groupName = groupName;
//	}
//
//	public int getGroupID() {
//		return groupID;
//	}
//
//	public void setGroupID(int groupID) {
//		this.groupID = groupID;
//	}
//
//	public String getGroupName() {
//		return groupName;
//	}
//
//	public void setGroupName(String groupName) {
//		this.groupName = groupName;
//	}
//
//	public Users getAdmin() {
//		return admin;
//	}
//
//	public void setAdmin(Users admin) {
//		this.admin = admin;
//	}
//
//	public boolean isDeleted() {
//		return isDeleted;
//	}
//
//	public void setDeleted(boolean isDeleted) {
//		this.isDeleted = isDeleted;
//	}
//
//	public boolean isUserRemovedFromGroup() {
//		return isUserRemovedFromGroup;
//	}
//
//	public void setUserRemovedFromGroup(boolean isUserRemovedFromGroup) {
//		this.isUserRemovedFromGroup = isUserRemovedFromGroup;
//	}
//
//	public Collection getUsers() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public List<Messages> getMessages() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
