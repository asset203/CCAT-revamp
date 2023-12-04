package com.asset.ccat.procedureservice.dto.users;



import com.asset.ccat.procedureservice.dto.shared.LkFeatureModel;

import java.util.ArrayList;


public class ProfileModel {

    private Integer profileId;
    private String profileName;
    private ArrayList<LkFeatureModel> features;
    private ArrayList<Integer> serviceClassesIDs;
    private ArrayList<ProfileModel> monetaryLimitsArray;
    private Integer sessionLimit;
    private Integer isAdjustmentsLimited;

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

    public ArrayList<LkFeatureModel> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<LkFeatureModel> features) {
        this.features = features;
    }

    public ArrayList<Integer> getServiceClassesIDs() {
        return serviceClassesIDs;
    }

    public void setServiceClassesIDs(ArrayList<Integer> serviceClassesIDs) {
        this.serviceClassesIDs = serviceClassesIDs;
    }

    public ArrayList<ProfileModel> getMonetaryLimitsArray() {
        return monetaryLimitsArray;
    }

    public void setMonetaryLimitsArray(ArrayList<ProfileModel> monetaryLimitsArray) {
        this.monetaryLimitsArray = monetaryLimitsArray;
    }

    public Integer getSessionLimit() {
        return sessionLimit;
    }

    public void setSessionLimit(Integer sessionLimit) {
        this.sessionLimit = sessionLimit;
    }

    public Integer getIsAdjustmentsLimited() {
        return isAdjustmentsLimited;
    }

    public void setIsAdjustmentsLimited(Integer isAdjustmentsLimited) {
        this.isAdjustmentsLimited = isAdjustmentsLimited;
    }

}
