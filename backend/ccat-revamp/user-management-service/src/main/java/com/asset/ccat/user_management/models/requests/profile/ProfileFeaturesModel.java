package com.asset.ccat.user_management.models.requests.profile;

import java.util.List;

public class ProfileFeaturesModel {
    private Integer profileId;
    private String profileName;
    private List<Integer> features;

    public ProfileFeaturesModel() {
    }

    public ProfileFeaturesModel(Integer profileId, String profileName, List<Integer> features) {
        this.profileId = profileId;
        this.profileName = profileName;
        this.features = features;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public List<Integer> getFeatures() {
        return features;
    }

    public void setFeatures(List<Integer> features) {
        this.features = features;
    }

    @Override
    public String toString() {
        return "ProfileFeaturesModel{" +
                "profileId=" + profileId +
                ", profileName='" + profileName + '\'' +
                ", features=" + features +
                '}';
    }
}
