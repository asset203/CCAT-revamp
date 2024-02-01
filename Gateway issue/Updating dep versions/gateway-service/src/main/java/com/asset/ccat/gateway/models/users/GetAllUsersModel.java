package com.asset.ccat.gateway.models.users;

/**
 *
 * @author nour.ihab
 */
public class GetAllUsersModel {

    private Integer userId;
    private String ntAccount;
    private Integer profileId;
    private String profileName;

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
}
