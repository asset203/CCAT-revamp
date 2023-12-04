package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.admin.user.*;
import com.asset.ccat.gateway.models.responses.admin.user.GetAllUsersResponse;
import com.asset.ccat.gateway.models.responses.admin.user.GetUserResponse;
import com.asset.ccat.gateway.proxy.admin.UserAccessProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author nour.ihab
 */
@Service
public class UserAccessService {

    @Autowired
    UserAccessProxy userAccessProxy;

    public GetUserResponse getUser(GetUserRequest user) throws GatewayException {
        GetUserResponse userModel = userAccessProxy.getUser(user);
        return userModel;
    }

    public GetAllUsersResponse getAllUsers(GetAllUsersRequest users) throws GatewayException {
        GetAllUsersResponse userModel = userAccessProxy.getAllUsers(users);
        return userModel;
    }

    public void addUser(AddUserRequest addUserRequest) throws GatewayException {
        var users = new GetAllUsersRequest();
        users.setRequestId(addUserRequest.getRequestId());
        users.setSessionId(addUserRequest.getSessionId());
        users.setToken(addUserRequest.getToken());
        users.setUsername(addUserRequest.getUsername());
        GetAllUsersResponse userModel = userAccessProxy.getAllUsers(users);
        boolean contains;
        int i;
        for (i = 0; i < userModel.getUsers().size(); i++) {
            contains = userModel.getUsers().get(i).getNtAccount().equals(addUserRequest.getUser().getNtAccount());
            if (contains) {
                throw new GatewayValidationException(ErrorCodes.WARNING.DUPLICATED_DATA, "ntAccount");
            }
        }
        userAccessProxy.addUser(addUserRequest);
    }

    public void updateUser(UpdateUserRequest user) throws GatewayException {
        userAccessProxy.updateUser(user);
    }

    public void deleteUser(DeleteUserRequest user) throws GatewayException {
        userAccessProxy.deleteUser(user);
    }

    public ResponseEntity<Resource> uploadUsers(FileUploadRequest uploadRequest) throws GatewayException {
        return userAccessProxy.uploadUsers(uploadRequest);
    }
}
