package com.scmd.socialmedia.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Friends")
public class Friends {

    @Id
    @Column(name = "friendshipID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int friendshipID;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "userID1")
    @NotNull(message = "User1 cannot be null")
    private Users user1;

    @ManyToOne
    @JoinColumn(name = "userID2")
    @NotNull(message = "User2 cannot be null")
    private Users user2;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @NotNull(message = "Friendship status cannot be null")
    private FriendsStatus status;

    // Default constructor
    public Friends() {}

    // Constructor with friendshipID and status
    public Friends(int friendshipID, FriendsStatus status) {
        this.friendshipID = friendshipID;
        this.status = status;
    }

    // Getters and setters
    public int getFriendshipID() {
        return friendshipID;
    }

    public void setFriendshipID(int friendshipID) {
        this.friendshipID = friendshipID;
    }

    public Users getUser1() {
        return user1;
    }

    public void setUser1(Users user1) {
        this.user1 = user1;
    }

    public Users getUser2() {
        return user2;
    }

    public void setUser2(Users user2) {
        this.user2 = user2;
    }

    public FriendsStatus getStatus() {
        return status;
    }

    public void setStatus(FriendsStatus status) {
        this.status = status;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
