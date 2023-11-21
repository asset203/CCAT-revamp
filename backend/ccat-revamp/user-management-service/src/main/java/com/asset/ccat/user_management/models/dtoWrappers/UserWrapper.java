package com.asset.ccat.user_management.models.dtoWrappers;

public class UserWrapper {
    private String userName;
    private Integer userId;

    public UserWrapper(String userName, Integer userId) {
        this.userName = userName;
        this.userId = userId;
    }

    public UserWrapper() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
