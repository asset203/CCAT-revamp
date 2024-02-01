package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.models.requests.admin.profile.AddProfileRequest;
import com.asset.ccat.gateway.models.requests.admin.user.AddUserRequest;
import com.asset.ccat.gateway.models.requests.admin.profile.DeleteProfileRequest;
import com.asset.ccat.gateway.models.requests.admin.user.DeleteUserRequest;
import com.asset.ccat.gateway.models.requests.admin.profile.GetAllProfilesRequest;
import com.asset.ccat.gateway.models.requests.admin.profile.GetProfileRequest;
import com.asset.ccat.gateway.models.requests.admin.user.GetUserRequest;
import com.asset.ccat.gateway.models.requests.admin.profile.UpdateProfileRequest;
import com.asset.ccat.gateway.models.requests.admin.user.UpdateUserRequest;
import com.asset.ccat.gateway.models.responses.admin.profile.GetAllProfilesResponse;
import com.asset.ccat.gateway.models.responses.admin.profile.GetProfileResponse;
import com.asset.ccat.gateway.models.responses.admin.user.GetUserResponse;
import com.asset.ccat.gateway.proxy.admin.UserProfileProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nour.ihab
 */
@Service
public class UserProfileService {

    @Autowired
    UserProfileProxy userProfileProxy;

    public GetAllProfilesResponse getAllProfiles(GetAllProfilesRequest profiles) throws GatewayException {
        GetAllProfilesResponse getAllProfilesResponse = userProfileProxy.getAllProfiles(profiles);
        return getAllProfilesResponse;
    }

    public void addProfile(AddProfileRequest addProfileRequest) throws GatewayException {
        userProfileProxy.addProfile(addProfileRequest);
    }

    public GetProfileResponse getPorfile(GetProfileRequest getProfileRequest) throws GatewayException {
        GetProfileResponse getProfileResponse = userProfileProxy.getProfile(getProfileRequest);
        return getProfileResponse;
    }

    public void updateProfile(UpdateProfileRequest updateProfileRequest) throws GatewayException {
        userProfileProxy.updateProfile(updateProfileRequest);
    }

    public void deleteProfile(DeleteProfileRequest deleteProfileRequest) throws GatewayException {
        userProfileProxy.deleteProfile(deleteProfileRequest);
    }


}
