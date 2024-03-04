package com.gjevents.usermanagementservice.controller;

public class UserLoginResponse {
    private String userData;

    private String responseState;


    private Long tempUserId;

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