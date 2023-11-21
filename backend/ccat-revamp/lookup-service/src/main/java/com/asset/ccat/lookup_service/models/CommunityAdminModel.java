package com.asset.ccat.lookup_service.models;

public class CommunityAdminModel {

    private Integer communityId;
    private String communityDescription;

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public String getCommunityDescription() {
        return communityDescription;
    }

    public void setCommunityDescription(String communityDescription) {
        this.communityDescription = communityDescription;
    }

    @Override
    public String toString() {
        return "CommunityAdminModel{" +
                "communityId=" + communityId +
                ", communityDescription='" + communityDescription + '\'' +
                '}';
    }
}
