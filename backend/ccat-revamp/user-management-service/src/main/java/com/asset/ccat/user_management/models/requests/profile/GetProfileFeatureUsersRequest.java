package com.asset.ccat.user_management.models.requests.profile;

import com.asset.ccat.user_management.models.requests.BaseRequest;

public class GetProfileFeatureUsersRequest extends BaseRequest {
    private Long profileId;

    public GetProfileFeatureUsersRequest() {
    }

    public GetProfileFeatureUsersRequest(Long profileId) {
        this.profileId = profileId;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    @Override
    public String toString() {
        return "GetProfileFeatureUsersRequest{" +
                "profileId=" + profileId +
                '}';
    }
}
