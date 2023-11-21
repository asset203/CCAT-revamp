package com.asset.ccat.user_management.models.dtoWrappers;

public class ProfileWrapper {
    private String profileName;
    private Integer profileId;

    public ProfileWrapper(String profileName, Integer profileId) {
        this.profileName = profileName;
        this.profileId = profileId;
    }

    public String getProfileName() {
        return profileName;
    }

    public ProfileWrapper() {
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
