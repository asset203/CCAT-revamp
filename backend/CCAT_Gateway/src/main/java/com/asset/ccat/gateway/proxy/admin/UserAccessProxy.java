package com.asset.ccat.gateway.proxy.admin;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.user.*;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.user.GetAllUsersResponse;
import com.asset.ccat.gateway.models.responses.admin.user.GetUserResponse;
import com.asset.ccat.gateway.models.users.GetAllUsersModel;
import com.asset.ccat.gateway.models.users.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 *
 * @author nour.ihab
 */
@Component
public class UserAccessProxy {

    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;

    @LogExecutionTime
    public BaseResponse addUser(AddUserRequest addUserRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting addUser - call user management serivce");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "ntAccount: " + addUserRequest.getUser().getNtAccount()
                    + ",profileId: " + addUserRequest.getUser().getProfileId()
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The User Management....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getUserManagementUrls()
                            + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                            + Defines.ContextPaths.USER
                            + Defines.WEB_ACTIONS.ADD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(addUserRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = res.block();

            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Adding User " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Adding User " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is ["
                    + "statusMessage" + response.getStatusMessage()
                    + ",statusCode" + response.getStatusCode()
                    + ",payload:" + response.getPayload() + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Adding User ");
            CCATLogger.ERROR_LOGGER.error("Error while Adding User", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "user-management-service[" + properties.getUserManagementUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public GetUserResponse getUser(GetUserRequest getUserRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getUser - call user management serivce");
        GetUserResponse user = null;
        BaseResponse<GetUserResponse> response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "userId: " + getUserRequest.getUserId()
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The User Management....");
            Mono<BaseResponse<GetUserResponse>> res = webClient.post()
                    .uri(properties.getUserManagementUrls()
                            + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                            + Defines.ContextPaths.USER
                            + Defines.WEB_ACTIONS.GET)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(getUserRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetUserResponse>>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    user = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while Retriving User " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Retriving User " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is ["
                    + "statusMessage" + response.getStatusMessage()
                    + ",statusCode" + response.getStatusCode()
                    + ",ntAccount:" + response.getPayload().getUser().getNtAccount()
                    + ",userId:" + response.getPayload().getUser().getUserId()
                    + ",profileId:" + response.getPayload().getUser().getProfileId()
                    + ",sessionCounter:" + response.getPayload().getUser().getSessionCounter()
                    + ",rebateLimit:" + response.getPayload().getUser().getRebateLimit()
                    + ",debitLimit:" + response.getPayload().getUser().getDebitLimit()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving User ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving User", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "user-management-service[" + properties.getUserManagementUrls() + "]");
        }
        return user;
    }

    @LogExecutionTime
    public GetAllUsersResponse getAllUsers(GetAllUsersRequest getAllUsersRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllUsers - call user management serivce");
        GetAllUsersResponse getAllUsersResponse = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + getAllUsersRequest + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The User Management....");
            Mono<BaseResponse<GetAllUsersResponse>> res = webClient.post()
                    .uri(properties.getUserManagementUrls()
                            + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                            + Defines.ContextPaths.USER
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(getAllUsersRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetAllUsersResponse>>() {
                    }).log();
            BaseResponse<GetAllUsersResponse> response = res.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    getAllUsersResponse = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving Users " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Retriving User " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            for (var user : getAllUsersResponse.getUsers()) {
                CCATLogger.INTERFACE_LOGGER.info("response is [" + "ntAccount: " + user.getNtAccount()
                        + ", profileName: " + user.getProfileName()
                        + ", userId: " + user.getUserId()
                        + ", profileId: " + user.getProfileId()
                        + "]");
            }

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving Users ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving Users ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "user-management-service[" + properties.getUserManagementUrls() + "]");
        }
        return getAllUsersResponse;
    }

    @LogExecutionTime
    public BaseResponse updateUser(UpdateUserRequest updateUserRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting updateUser - call user management serivce");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "userId: " + updateUserRequest.getUser().getUserId()
                    + ", ntAccount: " + updateUserRequest.getUser().getNtAccount()
                    + ", profileId: " + updateUserRequest.getUser().getProfileId()
                    + ", billingAccount: " + updateUserRequest.getUser().getMachineName()
                    + ", resetDebitLimit: " + updateUserRequest.getUser().getDebitLimit()
                    + ", resetRebateLimit: " + updateUserRequest.getUser().getRebateLimit()
                    + ", resetSessionCounter: " + updateUserRequest.getUser().getSessionCounter()
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The User Management....");
            Mono<BaseResponse<UserModel[]>> res = webClient.post()
                    .uri(properties.getUserManagementUrls() + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                            + Defines.ContextPaths.USER + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(updateUserRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<UserModel[]>>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Updating User " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Retriving User " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Updating User ");
            CCATLogger.ERROR_LOGGER.error("Error while Updating User", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "user-management-service[" + properties.getUserManagementUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse deleteUser(DeleteUserRequest deleteUserRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting deleteUser - call user management serivce");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "userId: " + deleteUserRequest.getUserId() + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The User Management....");
            Mono<BaseResponse<UserModel[]>> res = webClient.post()
                    .uri(properties.getUserManagementUrls()
                            + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                            + Defines.ContextPaths.USER
                            + Defines.WEB_ACTIONS.DELETE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(deleteUserRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<UserModel[]>>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Deleting User " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Retriving User " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Deleting User ");
            CCATLogger.ERROR_LOGGER.error("Error while Deleting User", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "user-management-service[" + properties.getUserManagementUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public ResponseEntity<Resource> uploadUsers(FileUploadRequest uploadRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting uploading users - call user management serivce");
        ResponseEntity<Resource> response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "fileExtenstion: " + uploadRequest.getFileExt()
                    + ", fileName: " + uploadRequest.getFileName()
                    + ", operationType: " + uploadRequest.getOperationType()
                    + ", file: " + uploadRequest.getFile()
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The User Management....");
            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder.part("file", uploadRequest.getFile().getResource());
            builder.part("fileName", uploadRequest.getFileName());
            builder.part("fileExt", uploadRequest.getFileExt());
            builder.part("requestId", uploadRequest.getRequestId());
            builder.part("sessionId", uploadRequest.getSessionId());
            builder.part("token", uploadRequest.getToken());
            builder.part("operationType", uploadRequest.getOperationType());
            Mono<ResponseEntity<Resource>> res = webClient.post()
                    .uri(properties.getUserManagementUrls()
                            + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                            + Defines.ContextPaths.USER
                            + Defines.WEB_ACTIONS.UPLOAD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .retrieve()
                    .toEntity(Resource.class);

            response = res.block();
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while uploading users ");
            CCATLogger.ERROR_LOGGER.error("Error while uploading users", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "user-management-service[" + properties.getUserManagementUrls() + "]");
        }
        return response;
    }


}
