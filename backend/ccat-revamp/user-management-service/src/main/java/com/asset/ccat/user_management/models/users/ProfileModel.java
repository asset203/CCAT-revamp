package com.asset.ccat.user_management.models.users;

import com.asset.ccat.user_management.models.shared.LkMonetaryLimitModel;
import com.asset.ccat.user_management.models.shared.LkFeatureModel;

import java.util.List;

/**
 * @author marwa.elshawarby
 */
public class ProfileModel {

    private Integer profileId;
    private String profileName;
    private Integer sessionLimit;
    private List<LkFeatureModel> sysFeatures;
    private List<LkFeatureModel> ccFeatures;
    private List<LkFeatureModel> dssReportsFeatures;
    private List<LkMonetaryLimitModel> monetaryLimits;
    private List<ServiceClassModel> serviceClasses;
    private List<MenuModel> menus;

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

    public Integer getSessionLimit() {
        return sessionLimit;
    }

    public void setSessionLimit(Integer sessionLimit) {
        this.sessionLimit = sessionLimit;
    }

    public List<LkFeatureModel> getSysFeatures() {
        return sysFeatures;
    }

    public void setSysFeatures(List<LkFeatureModel> sysFeatures) {
        this.sysFeatures = sysFeatures;
    }

    public List<LkFeatureModel> getCcFeatures() {
        return ccFeatures;
    }

    public void setCcFeatures(List<LkFeatureModel> ccFeatures) {
        this.ccFeatures = ccFeatures;
    }

    public List<LkFeatureModel> getDssReportsFeatures() {
        return dssReportsFeatures;
    }

    public void setDssReportsFeatures(List<LkFeatureModel> dssReportsFeatures) {
        this.dssReportsFeatures = dssReportsFeatures;
    }

    public List<LkMonetaryLimitModel> getMonetaryLimits() {
        return monetaryLimits;
    }

    public void setMonetaryLimits(List<LkMonetaryLimitModel> monetaryLimits) {
        this.monetaryLimits = monetaryLimits;
    }

    public List<ServiceClassModel> getServiceClasses() {
        return serviceClasses;
    }

    public void setServiceClasses(List<ServiceClassModel> serviceClasses) {
        this.serviceClasses = serviceClasses;
    }

    public List<MenuModel> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuModel> menus) {
        this.menus = menus;
    }
}
