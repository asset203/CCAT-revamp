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
    public ServiceClassConversionRequest mapTo(ServiceClassRequest newServiceClassRequest) {
        ServiceClassConversionRequest conversionRequest = new ServiceClassConversionRequest();
        conversionRequest.setToken(newServiceClassRequest.getToken());
        conversionRequest.setUsername(newServiceClassRequest.getUsername());
        conversionRequest.setRequestId(newServiceClassRequest.getRequestId());
        conversionRequest.setSessionId(newServiceClassRequest.getSessionId());
        conversionRequest.setMsisdn(newServiceClassRequest.getMsisdn());
        conversionRequest.setId(newServiceClassRequest.getNewServiceClass().getCode());
        conversionRequest.setCiPackageName(newServiceClassRequest.getNewServiceClass().getCiPackageName());
        conversionRequest.setCiServiceName(newServiceClassRequest.getNewServiceClass().getCiServiceName());
        return conversionRequest;
    }
}
