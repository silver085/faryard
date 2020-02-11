package com.faryard.api.DTO;

import java.util.ArrayList;
import java.util.List;

public class UserProfileDTO {
    private String userId;
    private String email;
    private String username;
    private List<String> roles;
    private String userPicture;
    private List<UserNotificationDTO> notifications = new ArrayList<>();
    private List<UserNodeDTO> myNodes = new ArrayList<>();


    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public List<UserNotificationDTO> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<UserNotificationDTO> notifications) {
        this.notifications = notifications;
    }

    public List<UserNodeDTO> getMyNodes() {
        return myNodes;
    }

    public void setMyNodes(List<UserNodeDTO> myNodes) {
        this.myNodes = myNodes;
    }
}
