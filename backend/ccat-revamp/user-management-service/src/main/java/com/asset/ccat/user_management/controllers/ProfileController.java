package com.asset.ccat.user_management.controllers;

import com.asset.ccat.user_management.defines.Defines;
import com.asset.ccat.user_management.defines.ErrorCodes;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.logger.CCATLogger;
import com.asset.ccat.user_management.models.requests.profile.*;
import com.asset.ccat.user_management.models.responses.BaseResponse;
import com.asset.ccat.user_management.models.responses.profile.GetAllProfilesFeaturesResponse;
import com.asset.ccat.user_management.models.responses.profile.GetAllProfilesResponse;
import com.asset.ccat.user_management.models.responses.profile.GetProfileResponse;
import com.asset.ccat.user_management.models.responses.profile.GetProfileUsersResponse;
import com.asset.ccat.user_management.models.users.ProfileModel;
import com.asset.ccat.user_management.services.ProfileService;
import com.asset.ccat.user_management.validators.ProfileValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author marwa.elshawarby
 */
@RestController
@RequestMapping(value = Defines.ContextPaths.PROFILE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileValidator profileValidator;

    @RequestMapping(value = Defines.WEB_ACTIONS.GET, method = RequestMethod.POST)
    public BaseResponse<GetProfileResponse> get(@RequestBody GetProfileRequest getRequest) throws UserManagementException {
        ThreadContext.put("requestId", getRequest.getRequestId());
        ThreadContext.put("sessionId", getRequest.getSessionId());

        CCATLogger.DEBUG_LOGGER.info("Recieved get profile request [" + getRequest + "]");
        Integer profileId = getRequest.getProfileId();
        GetProfileResponse resp = profileService.retrieveProfile(profileId);
        CCATLogger.DEBUG_LOGGER.info("Get profile with ID [" + profileId + "] request finished successfully");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, resp);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.GET_ALL, method = RequestMethod.POST)
    public BaseResponse<GetAllProfilesResponse> getAll(@RequestBody GetAllProfilesRequest getAllRequest) throws UserManagementException {
        ThreadContext.put("requestId", getAllRequest.getRequestId());
        ThreadContext.put("sessionId", getAllRequest.getSessionId());

        CCATLogger.DEBUG_LOGGER.info("Recieved get all profiles request [" + getAllRequest + "]");
        GetAllProfilesResponse resp = profileService.retrieveAllProfiles();
        CCATLogger.DEBUG_LOGGER.info("Get all profiles request finished successfully");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, resp);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.ADD, method = RequestMethod.POST)
    public BaseResponse add(@RequestBody AddProfileRequest addRequest) throws UserManagementException {
        ThreadContext.put("requestId", addRequest.getRequestId());
        ThreadContext.put("sessionId", addRequest.getSessionId());

        CCATLogger.DEBUG_LOGGER.info("Recieved add profile request [" + addRequest + "]");
        ProfileModel profile = addRequest.getProfile();

        profileService.addProfile(profile);
        CCATLogger.DEBUG_LOGGER.info("Add profile request finished successfully");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, null);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.UPDATE, method = RequestMethod.POST)
    public BaseResponse update(@RequestBody UpdateProfileRequest updateRequest) throws UserManagementException {
        ThreadContext.put("requestId", updateRequest.getRequestId());
        ThreadContext.put("sessionId", updateRequest.getSessionId());

        CCATLogger.DEBUG_LOGGER.info("Recieved update profile request [" + updateRequest + "]");
        ProfileModel profile = updateRequest.getProfile();

        // validations
        profileValidator.isProfileExists(profile.getProfileId());

        profileService.updateProfile(profile);
        CCATLogger.DEBUG_LOGGER.info("Update profile request finished successfully");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, null);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.DELETE, method = RequestMethod.POST)
    public BaseResponse delete(@RequestBody DeleteProfileRequest deleteRequest) throws UserManagementException {
        ThreadContext.put("requestId", deleteRequest.getRequestId());
        ThreadContext.put("sessionId", deleteRequest.getSessionId());

        CCATLogger.DEBUG_LOGGER.info("Recieved delete profile request [" + deleteRequest + "]");
        Integer profileId = deleteRequest.getProfileId();

        // validations
        profileValidator.isProfileExists(profileId);       
        profileValidator.profileHasNoChildren(profileId);

        profileService.deleteProfile(profileId);
        CCATLogger.DEBUG_LOGGER.info("Delete profile request finished successfully");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, null);
    }

    @PostMapping( Defines.ContextPaths.PROFILE_USERS+Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetProfileUsersResponse> getProfileUsers(@RequestBody GetProfileFeatureUsersRequest getProfileFeatureUsersRequest) throws UserManagementException {
        ThreadContext.put("requestId", getProfileFeatureUsersRequest.getRequestId());
        ThreadContext.put("sessionId", getProfileFeatureUsersRequest.getSessionId());

        CCATLogger.DEBUG_LOGGER.info("Received get profile users request [" + getProfileFeatureUsersRequest + "]");
        GetProfileUsersResponse response = profileService.retrieveProfileUsers(getProfileFeatureUsersRequest.getProfileId());
        CCATLogger.DEBUG_LOGGER.info("Get profile users request finished successfully");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, response);
    }


    @PostMapping( Defines.ContextPaths.PROFILE_FEATURES+Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllProfilesFeaturesResponse> getAllProfilesFeatures(@RequestBody GetAllProfilesFeaturesRequest getAllProfilesFeaturesRequest) throws UserManagementException {
        ThreadContext.put("requestId", getAllProfilesFeaturesRequest.getRequestId());
        ThreadContext.put("sessionId", getAllProfilesFeaturesRequest.getSessionId());

        CCATLogger.DEBUG_LOGGER.info("Received get all profiles features request [" + getAllProfilesFeaturesRequest + "]");
        GetAllProfilesFeaturesResponse response = profileService.retrieveAllProfilesWithFeatures();
        CCATLogger.DEBUG_LOGGER.info("Get  all profiles features request finished successfully");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, response);
    }



}
