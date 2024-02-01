/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.services.admin;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.admin.DisconnectionCodeModel;
import com.asset.ccat.gateway.models.requests.admin.disconnection_code.CreateDisconnectionCodeRequest;
import com.asset.ccat.gateway.models.requests.admin.disconnection_code.DeleteDisconnectionCodeRequest;
import com.asset.ccat.gateway.models.requests.admin.disconnection_code.GetAllDisconnectionCodesRequest;
import com.asset.ccat.gateway.models.requests.admin.disconnection_code.UpdateDisconnectionCodeRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.disconnection_codes.GetAllDisconnectionCodesResponse;
import com.asset.ccat.gateway.proxy.admin.DisconnectionCodeProxy;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author wael.mohamed
 */
@Service
public class DisconnectionCodeService {

    @Autowired
    private DisconnectionCodeProxy codeProxy;

    public GetAllDisconnectionCodesResponse getAllDisconnectionCodes(GetAllDisconnectionCodesRequest request) throws GatewayException {
        List<DisconnectionCodeModel> codes = codeProxy.getAllDisconnectionCodes(request);
        return new GetAllDisconnectionCodesResponse(codes);
    }

    public BaseResponse createDisconnectionCode(CreateDisconnectionCodeRequest request) throws GatewayException {
        var getAllDisconnectionCodesRequest = new GetAllDisconnectionCodesRequest();
        getAllDisconnectionCodesRequest.setRequestId(request.getRequestId());
        getAllDisconnectionCodesRequest.setSessionId(request.getSessionId());
        getAllDisconnectionCodesRequest.setUsername(request.getUsername());
        getAllDisconnectionCodesRequest.setToken(request.getToken());
        List<DisconnectionCodeModel> codes = codeProxy.getAllDisconnectionCodes(getAllDisconnectionCodesRequest);
        boolean contains;
        int i;
        for (i = 0; i < codes.size(); i++) {
            contains = codes.get(i).getCode().equals(request.getCode());
            if (contains) {
                throw new GatewayValidationException(ErrorCodes.WARNING.DUPLICATED_DATA, "codeId");
            }
        }
        return codeProxy.createDisconnectionCode(request);
    }

    public BaseResponse updateDisconnectionCode(UpdateDisconnectionCodeRequest request) throws GatewayException {

        return codeProxy.updateDisconnectionCode(request);
    }

    public BaseResponse deleteDisconnectionCode(DeleteDisconnectionCodeRequest request) throws GatewayException {
        return codeProxy.deleteDisconnectionCode(request);
    }
}
