package com.asset.ccat.gateway.services.admin;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.models.requests.admin.account_groups_bits_desc.GetAllAccountGroupsBitsDescRequest;
import com.asset.ccat.gateway.models.requests.admin.account_groups_bits_desc.UpdateAccountGroupsBitsDescRequest;
import com.asset.ccat.gateway.models.responses.admin.account_groups_bits_desc.GetAllAccountGroupsBitsDescResponse;
import com.asset.ccat.gateway.proxy.admin.AccountGroupsBitsDescProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountGroupsBitsDescService {
    @Autowired
    AccountGroupsBitsDescProxy accountGroupsBitsDescProxy;


    public GetAllAccountGroupsBitsDescResponse getAllAccountGroupsBitsDesc(GetAllAccountGroupsBitsDescRequest getAllAccountGroupsBitsDescRequest) throws GatewayException {
        GetAllAccountGroupsBitsDescResponse response = accountGroupsBitsDescProxy.getAllAccountGroupsBitsDesc(getAllAccountGroupsBitsDescRequest);
        return response;
    }

    public void updateAccountGroupsBitsDesc(UpdateAccountGroupsBitsDescRequest req) throws GatewayException {
        accountGroupsBitsDescProxy.updateAccountGroupBitDesc(req);
    }
}
