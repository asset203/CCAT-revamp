package com.asset.ccat.gateway.services.admin;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.admin.ExtractProfileModel;
import com.asset.ccat.gateway.models.admin.ExtractUserModel;
import com.asset.ccat.gateway.models.requests.admin.profile_features.GetProfileUsersRequest;
import com.asset.ccat.gateway.models.requests.admin.profile_features.GetProfilesFeaturesRequest;
import com.asset.ccat.gateway.models.requests.admin.user.ExtractUsersProfilesRequest;
import com.asset.ccat.gateway.models.responses.admin.profile_features.GetProfileUsersResponse;
import com.asset.ccat.gateway.models.responses.admin.profile_features.GetProfilesFeaturesResponse;
import com.asset.ccat.gateway.proxy.admin.UserProfileProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mohamed.metwaly
 */

@Service
public class ProfileFeaturesService {

    @Autowired
    private UserProfileProxy userProfileProxy;

    public GetProfileUsersResponse getProfileUsers(GetProfileUsersRequest getProfileUsersRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Start serving get Profile Users ");
        GetProfileUsersResponse payload = new GetProfileUsersResponse(userProfileProxy.getProfileUsers(getProfileUsersRequest));
        CCATLogger.DEBUG_LOGGER.debug("Finished serving get profile users successfully.");
        return payload;
    }

    public GetProfilesFeaturesResponse getProfilesFeatures(GetProfilesFeaturesRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started serving get all Profiles Features.");
        GetProfilesFeaturesResponse getProfilesFeaturesResponse = new GetProfilesFeaturesResponse(userProfileProxy.getProfilesFeatures(request));
        CCATLogger.DEBUG_LOGGER.debug("Finished serving get all Profiles Features successfully");
        return getProfilesFeaturesResponse;
    }
    public ResponseEntity<Resource> exportUsersProfilesReport(ExtractUsersProfilesRequest extractUsersProfilesRequest) throws GatewayException {
        return userProfileProxy.extractUsersProfilesFile(extractUsersProfilesRequest);
    }
}
