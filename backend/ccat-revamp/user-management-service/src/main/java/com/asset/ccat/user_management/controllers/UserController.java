package com.asset.ccat.user_management.controllers;

import com.asset.ccat.user_management.defines.Defines;
import com.asset.ccat.user_management.defines.ErrorCodes;
import com.asset.ccat.user_management.exceptions.FilesException;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.file.handler.UsersFileHandler;
import com.asset.ccat.user_management.logger.CCATLogger;
import com.asset.ccat.user_management.models.requests.ExtractUsesProfilesRequest;
import com.asset.ccat.user_management.models.requests.user.*;
import com.asset.ccat.user_management.models.responses.BaseResponse;
import com.asset.ccat.user_management.models.responses.user.AddUserResponse;
import com.asset.ccat.user_management.models.responses.user.DeleteUserResponse;
import com.asset.ccat.user_management.models.responses.user.GetAllUsersResponse;
import com.asset.ccat.user_management.models.responses.user.GetUserResponse;
import com.asset.ccat.user_management.models.responses.user.UpdateUserResponse;
import com.asset.ccat.user_management.models.users.UserModel;
import com.asset.ccat.user_management.services.UserService;
import com.asset.ccat.user_management.validators.UserValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author marwa.elshawarby
 */
@RestController
@RequestMapping(value = Defines.ContextPaths.USER)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UsersFileHandler usersFileHandler;

    @RequestMapping(value = Defines.WEB_ACTIONS.GET, method = RequestMethod.POST)
    public BaseResponse<GetUserResponse> get(@RequestBody GetUserRequest getRequest) throws UserManagementException {
        ThreadContext.put("requestId", getRequest.getRequestId());
        ThreadContext.put("sessionId", getRequest.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Received get user request [" + getRequest + "]");
        GetUserResponse response = userService.retrieveUser(getRequest.getUserId());
        CCATLogger.DEBUG_LOGGER.info("Finished getting user with ID [" + getRequest.getUserId() + "] request");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, response);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.GET_ALL, method = RequestMethod.POST)
    public BaseResponse<GetAllUsersResponse> getAll(@RequestBody GetAllUsersRequest getAllRequest) throws UserManagementException {
        ThreadContext.put("requestId", getAllRequest.getRequestId());
        ThreadContext.put("sessionId", getAllRequest.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Received get all users request [" + getAllRequest + "]");
        GetAllUsersResponse response = userService.retrieveUsers();
        CCATLogger.DEBUG_LOGGER.info("Finished get all users request");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, response);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.ADD, method = RequestMethod.POST)
    public BaseResponse<AddUserResponse> add(@RequestBody AddUserRequest addRequest) throws UserManagementException {
        ThreadContext.put("requestId", addRequest.getRequestId());
        ThreadContext.put("sessionId", addRequest.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Received add new user request [" + addRequest + "]");
        UserModel user = addRequest.getUser();

        //Validations
        userValidator.isProfileExists(user.getProfileId());

        userService.addUser(user);
        CCATLogger.DEBUG_LOGGER.info("Finished adding new user request");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, null);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.UPDATE, method = RequestMethod.POST)
    public BaseResponse<UpdateUserResponse> update(@RequestBody UpdateUserRequest updateRequest) throws UserManagementException {
        ThreadContext.put("requestId", updateRequest.getRequestId());
        ThreadContext.put("sessionId", updateRequest.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Received update user request [" + updateRequest + "]");
        UserModel user = updateRequest.getUser();

        // validations
        userService.validateUserExistence(user.getUserId());

        userService.updateUser(user,
                updateRequest.getResetDebitLimit(),
                updateRequest.getResetRebateLimit(),
                updateRequest.getResetSessionCount());
        CCATLogger.DEBUG_LOGGER.info("Finished update user [" + updateRequest.getUser().getUserId() + "] request");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, null);

    }

    @RequestMapping(value = Defines.WEB_ACTIONS.DELETE, method = RequestMethod.POST)
    public BaseResponse<DeleteUserResponse> delete(@RequestBody DeleteUserRequest deleteRequest) throws UserManagementException {
        ThreadContext.put("requestId", deleteRequest.getRequestId());
        ThreadContext.put("sessionId", deleteRequest.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Received delete user request [" + deleteRequest + "]");

        //validations
        userService.validateUserExistence(deleteRequest.getUserId());

        userService.deleteUser(deleteRequest.getUserId());
        CCATLogger.DEBUG_LOGGER.info("Finished deleting user with ID [" + deleteRequest.getUserId() + "] request");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, null);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.UPLOAD, method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Resource> upload(@ModelAttribute FileUploadRequest uploadRequest) throws UserManagementException {
        ThreadContext.put("requestId", uploadRequest.getRequestId());
        ThreadContext.put("sessionId", uploadRequest.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Received upload users request [" + uploadRequest.getFileName() + "]");

        byte[] summaryContent = userService.uploadUsers(uploadRequest.getFile(), uploadRequest.getFileExt(), uploadRequest.getOperationType());
        ByteArrayResource resource = new ByteArrayResource(summaryContent);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=summary.csv");

        CCATLogger.DEBUG_LOGGER.info("Finished uploading users request");
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(summaryContent.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @RequestMapping(value = Defines.ContextPaths.USERS_PROFILES + Defines.WEB_ACTIONS.GET_ALL, method = RequestMethod.POST)
    public ResponseEntity<Resource> extractAllUsersProfiles(@RequestBody ExtractUsesProfilesRequest baseRequest) throws UserManagementException, FilesException {
        ThreadContext.put("requestId", baseRequest.getRequestId());
        ThreadContext.put("sessionId", baseRequest.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Received extract AllUsers Profiles request [" + baseRequest + "]");

        byte[] usersProfilesContent = userService.extractAllUsersProfiles();
        ByteArrayResource resource = new ByteArrayResource(usersProfilesContent);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=usersProfilesContent.csv");

        CCATLogger.DEBUG_LOGGER.info("End Calling  extract AllUsers Profiles Service Successfully ");
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(usersProfilesContent.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }

    @RequestMapping(value = Defines.ContextPaths.USER_PRIVILEGE + Defines.WEB_ACTIONS.CHECK, method = RequestMethod.POST)
    public BaseResponse<Boolean> checkUserPrivilege(@RequestBody CheckUserPrivilegeRequest request) throws UserManagementException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Received CheckUserPrivilegeRequest request [" + request + "]");
        CCATLogger.DEBUG_LOGGER.info("Start Calling CheckUserPrivilegeRequest Service");
        boolean check = userService.checkUserPrivilege(request.getProfileId(), request.getPrivilegeId());
        CCATLogger.DEBUG_LOGGER.info("End Calling  CheckUserPrivilegeRequest Successfully ");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, check);

    }
}
