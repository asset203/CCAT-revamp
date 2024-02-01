package com.asset.ccat.gateway.models.responses.admin.profile_features;

import com.asset.ccat.gateway.models.admin.ExtractUserModel;

import java.util.List;

/**
 * @author mohamed.metwaly
 */
public class GetProfileUsersResponse {
    private List<ExtractUserModel> profileUsers;

    public List<ExtractUserModel> getProfileUsers() {
        return profileUsers;
    }

    public void setProfileUsers(List<ExtractUserModel> profileUsers) {
        this.profileUsers = profileUsers;
    }

    public GetProfileUsersResponse() {
    }

    public GetProfileUsersResponse(List<ExtractUserModel> profileUsers) {
        this.profileUsers = profileUsers;
    }

}
