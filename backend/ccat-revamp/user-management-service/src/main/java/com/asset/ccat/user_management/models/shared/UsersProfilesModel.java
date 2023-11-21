package com.asset.ccat.user_management.models.shared;


public class UsersProfilesModel {
    private String userName;
    private Integer userId;

    private String profileName;
    private Integer profileId;

    public UsersProfilesModel() {
    }

    public UsersProfilesModel(String userName, Integer userId, String profileName, Integer profileId) {
        this.userName = userName;
        this.userId = userId;
        this.profileName = profileName;
        this.profileId = profileId;
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

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }
}
