package com.asset.ccat.gateway.models.users;

/**
 * @author marwa.elshawarby
 */
public class ServiceClassModel {

    private Integer profileId;
    private Integer code;
    private String name;

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer Id) {
        this.code = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ServiceClassModel{" +
                "profileId=" + profileId +
                ", code=" + code +
                ", name='" + name + '\'' +
                '}';
    }
}
