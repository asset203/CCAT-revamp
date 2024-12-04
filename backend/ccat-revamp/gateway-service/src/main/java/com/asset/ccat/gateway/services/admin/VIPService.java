package com.asset.ccat.gateway.services.admin;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.models.admin.vip.*;
import com.asset.ccat.gateway.models.requests.admin.vip.VIPRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.proxy.admin.vip.VIPLookupProxy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VIPService {
    private final VIPLookupProxy vipProxy;

    public VIPService(VIPLookupProxy vipProxy) {
        this.vipProxy = vipProxy;
    }

    public BaseResponse<VIPListsResponse> getAllVIPs(VIPRequest vipRequest) throws GatewayException {
         return vipProxy.getVIPs(vipRequest);
    }

    public void addVIPMsisdn(VIPRequest vipRequest) throws GatewayException {
        vipProxy.addVIPMsisdn(vipRequest);
    }

    public void deleteVIPMsisdn(VIPRequest vipRequest) throws GatewayException {
        vipProxy.deleteVIPMsisdn(vipRequest);
    }

    public void syncVIPPages(VIPUpdatePagesRequest vipRequest) throws GatewayException {
        vipProxy.syncVIPPages(vipRequest);
    }
}
