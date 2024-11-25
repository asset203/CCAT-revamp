package com.asset.ccat.user_management.models.responses.profile;

import com.asset.ccat.user_management.models.responses.BaseResponse;
import com.asset.ccat.user_management.models.shared.UserExtractModel;

import java.util.List;

public class GetProfileUsersResponse  {
    private List<UserExtractModel> profileUsers;

    public GetProfileUsersResponse(List<UserExtractModel> profileUsers) {
        this.profileUsers = profileUsers;
    }

    public List<UserExtractModel> getProfileUsers() {
        return profileUsers;
    }

    public void setProfileUsers(List<UserExtractModel> profileUsers) {
        this.profileUsers = profileUsers;
    }
}
