/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.mappers;

import com.asset.ccat.gateway.models.requests.service_class.ServiceClassRequest;
import com.asset.ccat.gateway.models.requests.service_class.UpdateServiceClassRequest;
import org.springframework.stereotype.Component;

/**
 *
 * @author wael.mohamed
 */

@Component("UpdateServiceClassMapper")
public class UpdateServiceClassMapper implements IMapper<UpdateServiceClassRequest, ServiceClassRequest> {

    @Override
    public UpdateServiceClassRequest mapTo(ServiceClassRequest request) {
        UpdateServiceClassRequest updateServiceClassRequest = new UpdateServiceClassRequest();
        updateServiceClassRequest.setToken(request.getToken());
        updateServiceClassRequest.setUsername(request.getUsername());
        updateServiceClassRequest.setRequestId(request.getRequestId());
        updateServiceClassRequest.setSessionId(request.getSessionId());
        updateServiceClassRequest.setMsisdn(request.getMsisdn());
        updateServiceClassRequest.setCurrentServiceClassId(request.getCurrentServiceClass().getCode());
        updateServiceClassRequest.setNewServiceClassId(request.getNewServiceClass().getCode());
        return updateServiceClassRequest;
    }

}
