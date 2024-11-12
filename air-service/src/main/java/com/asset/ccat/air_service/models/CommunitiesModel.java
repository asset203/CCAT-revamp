package com.asset.ccat.air_service.models;

public class CommunitiesModel {
    private Integer communityId;
    private String communityDescription;

    public CommunitiesModel() {
    }

    public CommunitiesModel(Integer communityId, String communityDescription) {
        this.communityId = communityId;
        this.communityDescription = communityDescription;
    }

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
        return "CommunitiesModel{" +
                "communityId=" + communityId +
                ", communityDescription='" + communityDescription + '\'' +
                '}';
    }
}
