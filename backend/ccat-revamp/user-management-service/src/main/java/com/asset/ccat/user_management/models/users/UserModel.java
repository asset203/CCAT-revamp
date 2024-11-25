package com.asset.ccat.user_management.models.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;

/**
 *
 * @author Mahmoud Shehab
 */
public class UserModel {

    private Integer userId;
    private String ntAccount;
    private String password;
    private String profileName;
    private String email;
    private Date expiryDate;
    private Boolean director;
    private Integer profileId;
    private String machineName;
    private String themeId;
    private UserProfileModel profileModel;
    private Integer sessionCounter;
    private Long rebateLimit;
    private Long debitLimit;

    // properties used internally by the engine
    @JsonIgnore
    private String status;
    @JsonIgnore
    private String statusMessage;

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

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
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

    public UserProfileModel getProfileModel() {
        return profileModel;
    }

    public void setProfileModel(UserProfileModel profileModel) {
        this.profileModel = profileModel;
    }

    public Integer getSessionCounter() {
        return sessionCounter;
    }

    public void setSessionCounter(Integer sessionCounter) {
        this.sessionCounter = sessionCounter;
    }

    public Long getRebateLimit() {
        return rebateLimit;
    }

    public void setRebateLimit(Long rebateLimit) {
        this.rebateLimit = rebateLimit;
    }

    public Long getDebitLimit() {
        return debitLimit;
    }

    public void setDebitLimit(Long debitLimit) {
        this.debitLimit = debitLimit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userId=" + userId +
                ", ntAccount='" + ntAccount + '\'' +
                ", profileName='" + profileName + '\'' +
                ", email='" + email + '\'' +
                ", expiryDate=" + expiryDate +
                ", director=" + director +
                ", profileId=" + profileId +
                ", machineName='" + machineName + '\'' +
                ", themeId='" + themeId + '\'' +
                ", profileModel=" + profileModel +
                ", sessionCounter=" + sessionCounter +
                ", rebateLimit=" + rebateLimit +
                ", debitLimit=" + debitLimit +
                ", status='" + status + '\'' +
                ", statusMessage='" + statusMessage + '\'' +
                '}';
    }
}
