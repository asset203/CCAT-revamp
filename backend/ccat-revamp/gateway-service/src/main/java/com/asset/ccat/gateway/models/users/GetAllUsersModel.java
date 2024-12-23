package com.asset.ccat.gateway.models.users;

import java.util.Date;

/**
 *
 * @author nour.ihab
 */
public class GetAllUsersModel {

    private Integer userId;
    private String ntAccount;
    private Integer profileId;
    private String profileName;
    private Date creationDate;
    private Date lastLogin;
    private Date modificationDate;

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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    @Override
    public String toString() {
        return "GetAllUsersModel{" +
                "userId=" + userId +
                ", ntAccount='" + ntAccount + '\'' +
                ", profileId=" + profileId +
                ", profileName='" + profileName + '\'' +
                ", creationDate=" + creationDate +
                ", lastLogin=" + lastLogin +
                ", modificationDate=" + modificationDate +
                '}';
    }
}
