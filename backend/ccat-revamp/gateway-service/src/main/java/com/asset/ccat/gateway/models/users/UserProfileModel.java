/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.users;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mahmoud Shehab
 */
public class UserProfileModel {

    private Integer profileId;
    private String profileName;
    private ArrayList<LkFeatureModel> features;
    private ArrayList<String> authorizedUrls;
    private ArrayList<Integer> serviceClassesIDs;
    private ArrayList<UserProfileModel> monetaryLimitsArray;
    private Integer sessionLimit;
    private Integer isAdjustmentsLimited;
    private List<MenuModel> menus;
    private String profileSOB;


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

    public ArrayList<UserProfileModel> getMonetaryLimitsArray() {
        return monetaryLimitsArray;
    }

    public void setMonetaryLimitsArray(ArrayList<UserProfileModel> monetaryLimitsArray) {
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

    public ArrayList<String> getAuthorizedUrls() {
        return authorizedUrls;
    }

    public void setAuthorizedUrls(ArrayList<String> authorizedUrls) {
        this.authorizedUrls = authorizedUrls;
    }

    public List<MenuModel> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuModel> menus) {
        this.menus = menus;
    }

    public String getProfileSOB() {
        return profileSOB;
    }

    public void setProfileSOB(String profileSOB) {
        this.profileSOB = profileSOB;
    }
}
