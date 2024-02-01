package com.asset.ccat.gateway.models.admin;

import java.util.List;

/**
 * @author mohamed.metwaly
 */
public class ExtractProfileModel {
    private Long profileId;
    private String profileName;
    private List<Long> features;

    public ExtractProfileModel(Long profileId, String profileName, List<Long> features) {
        this.profileId = profileId;
        this.profileName = profileName;
        this.features = features;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public List<Long> getFeatures() {
        return features;
    }

    public void setFeatures(List<Long> features) {
        this.features = features;
    }

    public ExtractProfileModel() {
    }

    @Override
    public String toString() {
        return "ExtractProfileModel{" +
                "profileId=" + profileId +
                ", profileName='" + profileName + '\'' +
                ", features=" + features +
                '}';
    }
}
