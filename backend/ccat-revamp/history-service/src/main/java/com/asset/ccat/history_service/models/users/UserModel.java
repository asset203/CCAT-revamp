package com.asset.ccat.history_service.models.users;

import com.asset.ccat.history_service.models.requests.BaseRequest;
import java.util.Date;

/**
 *
 * @author Mahmoud Shehab
 */
public class UserModel extends BaseRequest {

    private Integer userId;
    private String ntAccount;
    private String password;
    private String userDisplayName;
    private String email;
    private Date expiryDate;
    private Boolean director;
    private Integer profileId;
    private String machineName;
    private String themeId;
    private ProfileModel profileModel;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNtAccount() {
        return ntAccount;
    }

    public void setNtAccount(String ntAccount) {
        this.ntAccount = ntAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Boolean getDirector() {
        return director;
    }

    public void setDirector(Boolean director) {
        this.director = director;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    public ProfileModel getProfileModel() {
        return profileModel;
    }

    public void setProfileModel(ProfileModel profileModel) {
        this.profileModel = profileModel;
    }

}
