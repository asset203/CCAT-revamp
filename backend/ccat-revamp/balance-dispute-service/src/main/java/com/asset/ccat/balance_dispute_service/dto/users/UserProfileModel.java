package com.asset.ccat.balance_dispute_service.dto.users;


import com.asset.ccat.balance_dispute_service.dto.shared.LkFeatureModel;

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
    private Integer sessionLimit;
    private Integer isAdjustmentsLimited;
    private List<MenuModel> menus;

    private String profileSOB;

    public UserProfileModel() {
    }

    public UserProfileModel(Integer profileId, String profileName, Integer sessionLimit) {
        this.profileId = profileId;
        this.profileName = profileName;
        this.sessionLimit = sessionLimit;
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

    public ArrayList<LkFeatureModel> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<LkFeatureModel> features) {
        this.features = features;
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
