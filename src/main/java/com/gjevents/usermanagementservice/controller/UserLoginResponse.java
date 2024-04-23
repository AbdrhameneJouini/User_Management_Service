package com.gjevents.usermanagementservice.controller;

import com.gjevents.usermanagementservice.model.User;

public class UserLoginResponse {
    private String userData;

    private String responseState;


    private Long tempUserId;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getTempUserId() {
        return tempUserId;
    }

    public void setTempUserId(Long tempUserId) {
        this.tempUserId = tempUserId;
    }

    public String getResponseState() {
        return responseState;
    }
    public void setResponseState(String responseStat) {
        this.responseState = responseStat;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }


}