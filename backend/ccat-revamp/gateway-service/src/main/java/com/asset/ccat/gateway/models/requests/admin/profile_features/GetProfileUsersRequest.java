package com.asset.ccat.gateway.models.requests.admin.profile_features;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author mohamed.metwaly
 */
public class GetProfileUsersRequest extends BaseRequest {
    private Long profileId;

    public GetProfileUsersRequest() {
    }

    public GetProfileUsersRequest(Long profileId) {
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
        return "GetProfileUsersRequest{" +
                "profileId=" + profileId +
                '}';
    }
}
