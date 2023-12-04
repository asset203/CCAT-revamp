package com.asset.ccat.balancedisputemapperservice.models.requests;

/**
 * @author marwa.elshawarby
 */
public class CheckUserPrivilegeRequest extends BaseRequest {

    private Integer profileId;
    private Integer privilegeId;

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public Integer getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Integer privilegeId) {
        this.privilegeId = privilegeId;
    }

    @Override
    public String toString() {
        return "CheckUserPrivilegeRequest{" +
                "profileId=" + profileId +
                ", privilegeId=" + privilegeId +
                '}';
    }
}
