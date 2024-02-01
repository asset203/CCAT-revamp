/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.mappers;

import com.asset.ccat.gateway.models.requests.service_class.ServiceClassConversionRequest;
import com.asset.ccat.gateway.models.requests.service_class.ServiceClassRequest;
import org.springframework.stereotype.Component;

/**
 *
 * @author wael.mohamed
 */
@Component("ServiceClassConversionMapper")
public class ServiceClassConversionMapper implements IMapper<ServiceClassConversionRequest, ServiceClassRequest> {

    @Override
    public ServiceClassConversionRequest mapTo(ServiceClassRequest request) {
        ServiceClassConversionRequest conversionRequest = new ServiceClassConversionRequest();
        conversionRequest.setToken(request.getToken());
        conversionRequest.setUsername(request.getUsername());
        conversionRequest.setRequestId(request.getRequestId());
        conversionRequest.setSessionId(request.getSessionId());
        conversionRequest.setMsisdn(request.getMsisdn());
        conversionRequest.setId(request.getNewServiceClass().getCode());
        conversionRequest.setCiPackageName(request.getNewServiceClass().getCiPackageName());
        return conversionRequest;
    }
}
