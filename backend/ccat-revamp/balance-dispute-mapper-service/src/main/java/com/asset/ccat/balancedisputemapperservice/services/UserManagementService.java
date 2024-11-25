package com.asset.ccat.balancedisputemapperservice.services;

import com.asset.ccat.balancedisputemapperservice.exceptions.BalanceDisputeException;
import com.asset.ccat.balancedisputemapperservice.loggers.CCATLogger;
import com.asset.ccat.balancedisputemapperservice.models.requests.BaseRequest;
import com.asset.ccat.balancedisputemapperservice.models.requests.CheckUserPrivilegeRequest;
import com.asset.ccat.balancedisputemapperservice.proxy.LookupProxy;
import com.asset.ccat.balancedisputemapperservice.proxy.UserManagmentProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author marwa.elshawarby
 */
@Service
public class UserManagementService {


    private final UserManagmentProxy userManagmentProxy;

    public UserManagementService(UserManagmentProxy userManagmentProxy) {
        this.userManagmentProxy = userManagmentProxy;
    }

    public Boolean checkUserPrivilege(BaseRequest request, Integer profileId, Integer privilegeId) throws BalanceDisputeException {
        CheckUserPrivilegeRequest checkUserPrivilegeRequest = new CheckUserPrivilegeRequest();
        checkUserPrivilegeRequest.setRequestId(request.getRequestId());
        checkUserPrivilegeRequest.setSessionId(request.getSessionId());
        checkUserPrivilegeRequest.setUsername(request.getUsername());
        checkUserPrivilegeRequest.setToken(request.getToken());
        checkUserPrivilegeRequest.setProfileId(profileId);
        checkUserPrivilegeRequest.setPrivilegeId(privilegeId);

        CCATLogger.DEBUG_LOGGER.debug("Calling User management service with request >> " + checkUserPrivilegeRequest);
        return userManagmentProxy.userHasPrivilege(checkUserPrivilegeRequest);
    }
}
