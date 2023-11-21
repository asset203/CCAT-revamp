package com.asset.ccat.gateway.proxy.admin;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.admin.ExtractProfileModel;
import com.asset.ccat.gateway.models.admin.ExtractUserModel;
import com.asset.ccat.gateway.models.requests.admin.profile.AddProfileRequest;
import com.asset.ccat.gateway.models.requests.admin.profile.DeleteProfileRequest;
import com.asset.ccat.gateway.models.requests.admin.profile.GetAllProfilesRequest;
import com.asset.ccat.gateway.models.requests.admin.profile.GetProfileRequest;
import com.asset.ccat.gateway.models.requests.admin.profile.UpdateProfileRequest;
import com.asset.ccat.gateway.models.requests.admin.profile_features.GetProfileUsersRequest;
import com.asset.ccat.gateway.models.requests.admin.profile_features.GetProfilesFeaturesRequest;
import com.asset.ccat.gateway.models.requests.admin.user.ExtractUsersProfilesRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.profile.GetAllProfilesResponse;
import com.asset.ccat.gateway.models.responses.admin.profile.GetProfileResponse;
import com.asset.ccat.gateway.models.users.UserModel;
import com.asset.ccat.gateway.models.responses.admin.profile_features.GetProfileUsersResponse;
import com.asset.ccat.gateway.models.responses.admin.profile_features.GetProfilesFeaturesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

/**
 * @author nour.ihab
 */
@Component
public class UserProfileProxy {

    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;

    @LogExecutionTime
    public GetProfileResponse getProfile(GetProfileRequest getProfileRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getProfile - call user management serivce");
        GetProfileResponse profile = null;
        BaseResponse<GetProfileResponse> response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "profileId: " + getProfileRequest.getProfileId()
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The User Management....");
            Mono<BaseResponse<GetProfileResponse>> res = webClient.post()
                    .uri(properties.getUserManagementUrls()
                            + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                            + Defines.ContextPaths.PROFILE
                            + Defines.WEB_ACTIONS.GET)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(getProfileRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetProfileResponse>>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    profile = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while Retriving Profile " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Retriving Profile " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is ["
                    + "statusMessage" + response.getStatusMessage()
                    + ",statusCode" + response.getStatusCode()
                    + ",payload:" + response.getPayload()
                    + ",profileName:" + response.getPayload().getProfile().getProfileName()
                    + ",profileId:" + response.getPayload().getProfile().getProfileId()
                    + ",sessionLimit:" + response.getPayload().getProfile().getSessionLimit()
                    + ",sysFeatures:" + response.getPayload().getProfile().getCcFeatures()
                    + ",payload:" + response.getPayload().getProfile().getSysFeatures()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving Profile ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving Profile", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "user-management-service[" + properties.getUserManagementUrls() + "]");
        }
        return profile;
    }

    @LogExecutionTime
    public GetAllProfilesResponse getAllProfiles(GetAllProfilesRequest getAllProfilesRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllProfiles - call user management serivce");
        GetAllProfilesResponse getAllProfilesResponse = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + getAllProfilesRequest + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The User Management....");
            Mono<BaseResponse<GetAllProfilesResponse>> res = webClient.post()
                    .uri(properties.getUserManagementUrls()
                            + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                            + Defines.ContextPaths.PROFILE
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(getAllProfilesRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetAllProfilesResponse>>() {
                    }).log();
            BaseResponse<GetAllProfilesResponse> response = res.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    getAllProfilesResponse = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving Profiles " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Retriving Profiles " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            for (var profile : getAllProfilesResponse.getProfilesList()) {
                CCATLogger.INTERFACE_LOGGER.info("response is [" + "ccFeatures: " + profile.getCcFeatures()
                        + ", profileName: " + profile.getProfileName()
                        + ", sysFeatures: " + profile.getSysFeatures()
                        + ", profileId: " + profile.getProfileId()
                        + ", sessionLimit: " + profile.getSessionLimit()
                        + "]");
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving Profiles ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving Profiles ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "user-management-service[" + properties.getUserManagementUrls() + "]");
        }
        return getAllProfilesResponse;
    }

    @LogExecutionTime
    public BaseResponse addProfile(AddProfileRequest addProfileRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting addProfile - call user management serivce");
        BaseResponse response = null;
        try {

            CCATLogger.INTERFACE_LOGGER.info("request is [" + "profileName: " + addProfileRequest.getProfile().getProfileName()
                    + ", sessionLimit: " + addProfileRequest.getProfile().getSessionLimit()
                    + (addProfileRequest.getProfile().getSysFeatures() == null ? "" : ", sysFeatures " + (addProfileRequest.getProfile().getSysFeatures().toString()))
                    + (addProfileRequest.getProfile().getCcFeatures() == null ? "" : ", ccFeatures " + (addProfileRequest.getProfile().getCcFeatures().toString()))
                    + (addProfileRequest.getProfile().getDssReportsFeatures() == null ? "" : ", dssReportsFeatures " + (addProfileRequest.getProfile().getDssReportsFeatures().toString()))
                    + (addProfileRequest.getProfile().getMonetaryLimits() == null ? "" : ", monetaryLimits " + (addProfileRequest.getProfile().getMonetaryLimits().toString()))
                    + (addProfileRequest.getProfile().getServiceClasses() == null ? "" : ", serviceClasses " + (addProfileRequest.getProfile().getServiceClasses().toString()))
                    + "]");

            CCATLogger.DEBUG_LOGGER.debug("Calling The User Management....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getUserManagementUrls()
                            + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                            + Defines.ContextPaths.PROFILE
                            + Defines.WEB_ACTIONS.ADD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(addProfileRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = res.block();

            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Adding Profile  " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Adding Profile  " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Adding Profile  ");
            CCATLogger.ERROR_LOGGER.error("Error while Adding Profile ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "user-management-service[" + properties.getUserManagementUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse updateProfile(UpdateProfileRequest updateProfileRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting updateProfile - call user management serivce");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "profileName: " + updateProfileRequest.getProfile().getProfileName()
                    + ", sessionLimit: " + updateProfileRequest.getProfile().getSessionLimit()
                    + (updateProfileRequest.getProfile().getSysFeatures() == null ? "" : ", sysFeatures " + (updateProfileRequest.getProfile().getSysFeatures().toString()))
                    + (updateProfileRequest.getProfile().getCcFeatures() == null ? "" : ", ccFeatures " + (updateProfileRequest.getProfile().getCcFeatures().toString()))
                    + (updateProfileRequest.getProfile().getDssReportsFeatures() == null ? "" : ", dssReportsFeatures " + (updateProfileRequest.getProfile().getDssReportsFeatures().toString()))
                    + (updateProfileRequest.getProfile().getMonetaryLimits() == null ? "" : ", monetaryLimits " + (updateProfileRequest.getProfile().getMonetaryLimits().toString()))
                    + (updateProfileRequest.getProfile().getServiceClasses() == null ? "" : ", serviceClasses " + (updateProfileRequest.getProfile().getServiceClasses().toString()))
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The User Management....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getUserManagementUrls()
                            + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                            + Defines.ContextPaths.PROFILE
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(updateProfileRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Updating Profile " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Retrieving Profile " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Updating Profile ");
            CCATLogger.ERROR_LOGGER.error("Error while Updating Profile", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "user-management-service[" + properties.getUserManagementUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse deleteProfile(DeleteProfileRequest deleteProfileRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting deleteProfile - call user management serivce");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "profileId: " + deleteProfileRequest.getProfileId() + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The User Management....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getUserManagementUrls()
                            + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                            + Defines.ContextPaths.PROFILE
                            + Defines.WEB_ACTIONS.DELETE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(deleteProfileRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Deleting Profile " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Retriving Profile " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Deleting Profile ");
            CCATLogger.ERROR_LOGGER.error("Error while Deleting Profile", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "user-management-service[" + properties.getUserManagementUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public List<ExtractUserModel> getProfileUsers(GetProfileUsersRequest getProfileUsersRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getProfileUsers - call user management service");
        BaseResponse<GetProfileUsersResponse> response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("Get profile users request [" + getProfileUsersRequest + "]");
            String URI = properties.getUserManagementUrls()
                    + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                    + Defines.ContextPaths.PROFILE
                    + Defines.ContextPaths.PROFILE_USERS
                    + Defines.WEB_ACTIONS.GET_ALL;

            CCATLogger.DEBUG_LOGGER.debug("Calling The User Management.... With URI  : "+URI);
            Mono<BaseResponse<GetProfileUsersResponse>> res = webClient
                    .post()
                    .uri(URI)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(getProfileUsersRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetProfileUsersResponse>>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while getting profile users" + response);
                    CCATLogger.DEBUG_LOGGER.error("rror while getting profile users " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while getting profile users ");
            CCATLogger.ERROR_LOGGER.error("Error while getting profile users", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "user-management-service[" + properties.getUserManagementUrls() + "]");
        }
        return response.getPayload().getProfileUsers();
    }

    @LogExecutionTime
    public List<ExtractProfileModel> getProfilesFeatures(GetProfilesFeaturesRequest getProfilesFeaturesRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getProfilesFeatures - call user management service");
        BaseResponse<GetProfilesFeaturesResponse> response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("Get all profiles features request [" + getProfilesFeaturesRequest + "]");
            String URI = properties.getUserManagementUrls()
                    + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                    + Defines.ContextPaths.PROFILE
                    + Defines.ContextPaths.PROFILE_FEATURES
                    + Defines.WEB_ACTIONS.GET_ALL;

            CCATLogger.DEBUG_LOGGER.debug("Calling The User Management.... With URI  : "+URI);
            Mono<BaseResponse<GetProfilesFeaturesResponse>> res = webClient
                    .post()
                    .uri(URI)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(getProfilesFeaturesRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetProfilesFeaturesResponse>>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while getting all profiles features" + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while getting all profiles features " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while getting all profiles features ");
            CCATLogger.ERROR_LOGGER.error("Error while getting all profiles features", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "user-management-service[" + properties.getUserManagementUrls() + "]");
        }
        return response.getPayload().getProfiles();
    }

    @LogExecutionTime
    public ResponseEntity<Resource> extractUsersProfilesFile(ExtractUsersProfilesRequest extractUsersProfilesRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting Exporting Users Profiles - call user management service");
        ResponseEntity<Resource> response = null;
        try {
            String URI = properties.getUserManagementUrls()
                    + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                    + Defines.ContextPaths.USER
                    + Defines.ContextPaths.USERS_PROFILES
                    + Defines.WEB_ACTIONS.GET_ALL;
            CCATLogger.DEBUG_LOGGER.debug("Calling The User Management.... With URI : "+URI);
            Mono<ResponseEntity<Resource>> res = webClient.post()
                    .uri(URI)
                    .header(HttpHeaders.CONTENT_TYPE,  MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(extractUsersProfilesRequest))
                    .retrieve()
                    .toEntity(Resource.class);

            response = res.block();
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Extracting Users Profiles Report File ");
            CCATLogger.ERROR_LOGGER.error("Error while Extracting Users Profiles Report File", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "user-management-service[" + properties.getUserManagementUrls() + "]");
        }
        return response;
    }
}
