package com.asset.ccat.gateway.services.admin;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.models.requests.admin.account_groups.AddAccountGroupRequest;
import com.asset.ccat.gateway.models.requests.admin.account_groups.DeleteAccountGroupRequest;
import com.asset.ccat.gateway.models.requests.admin.account_groups.GetAllAccountGroupsRequest;

import com.asset.ccat.gateway.models.requests.admin.account_groups.UpdateAccountGroupRequest;
import com.asset.ccat.gateway.models.responses.admin.account_groups.GetAllAccountGroupsResponse;
import com.asset.ccat.gateway.proxy.admin.AdminAccountGroupsProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mohamed.metwaly
 */
@Service
public class AdminAccountGroupsService {

    @Autowired
    AdminAccountGroupsProxy adminAccountGroupsProxy;

    public GetAllAccountGroupsResponse getAllAccountGroups(GetAllAccountGroupsRequest req)throws GatewayException {
        GetAllAccountGroupsResponse accountGroups = adminAccountGroupsProxy.getAllAccountGroups(req);
        return accountGroups;
    }

    public void updateAccountGroup(UpdateAccountGroupRequest req) throws GatewayException {
        adminAccountGroupsProxy.updateAccountGroup(req);
    }

    public void addAccountGroup(AddAccountGroupRequest request) throws  GatewayException {
        adminAccountGroupsProxy.addAccountGroup(request);
    }
    public void deleteAccountGroup(DeleteAccountGroupRequest request) throws GatewayException {
        adminAccountGroupsProxy.deleteAccountGroup(request);
    }

}
