/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.constants.UsageType;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.customer_care.usage_counter.*;
import com.asset.ccat.gateway.models.responses.customer_care.GetAllUsageCountersResponse;
import com.asset.ccat.gateway.proxy.UsageCountersProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author assem.hassan
 */
@Service
public class UsageCountersService {

    @Autowired
    UsageCountersProxy usageCounterProxy;

    public GetAllUsageCountersResponse getAllUsageCounters(GetAllUsageCountersRequest getAllUsageCountersRequest) throws GatewayException {
        return usageCounterProxy.getAllUsageCounters(getAllUsageCountersRequest);
    }

    public void deleteUsageThresholds(DeleteUsageThresholdsRequest deleteUTRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Start serving deleteUsageThresholds request");
        usageCounterProxy.deleteUsageThresholds(deleteUTRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished serving deleteUsageThresholds request");
    }

    public void addUsageCounters(AddUsageCountersRequest addUsageCountersRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Start serving addUsageCounters Request");
        AddOrUpdateUsageRequest requestModel = new AddOrUpdateUsageRequest();
        requestModel.setToken(addUsageCountersRequest.getToken());
        requestModel.setUsername(addUsageCountersRequest.getUsername());
        requestModel.setRequestId(addUsageCountersRequest.getRequestId());
        requestModel.setSessionId(addUsageCountersRequest.getSessionId());

        requestModel.setId(addUsageCountersRequest.getId());
        requestModel.setMsisdn(addUsageCountersRequest.getMsisdn());

        if (addUsageCountersRequest.getUsageTypeId().equals(UsageType.COUNTER_VALUE.id)) {
            requestModel.setCounterValue(addUsageCountersRequest.getValue());
        } else {
            requestModel.setMonetaryValue1(addUsageCountersRequest.getValue());
        }

        usageCounterProxy.updateUsageCountersAndThresholds(requestModel);

        CCATLogger.DEBUG_LOGGER.info("Finished serving addUsageCounters Request successfully");
    }

    public void addUsageCountersAndThresholds(AddUsageCountersAndThresholdsRequest addUsageCountersAndThresholdsRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Start serving addUsageCountersAndThresholds Request");
        AddOrUpdateUsageRequest requestModel = new AddOrUpdateUsageRequest();
        requestModel.setToken(addUsageCountersAndThresholdsRequest.getToken());
        requestModel.setUsername(addUsageCountersAndThresholdsRequest.getUsername());
        requestModel.setRequestId(addUsageCountersAndThresholdsRequest.getRequestId());
        requestModel.setSessionId(addUsageCountersAndThresholdsRequest.getSessionId());

        requestModel.setId(addUsageCountersAndThresholdsRequest.getId());
        requestModel.setMsisdn(addUsageCountersAndThresholdsRequest.getMsisdn());

        if (addUsageCountersAndThresholdsRequest.getUsageTypeId().equals(UsageType.COUNTER_VALUE.id)) {
            requestModel.setCounterValue(addUsageCountersAndThresholdsRequest.getValue());
        } else {
            requestModel.setMonetaryValue1(addUsageCountersAndThresholdsRequest.getValue());
        }

        requestModel.setUsageThresholdInformation(addUsageCountersAndThresholdsRequest.getThresholds());

        usageCounterProxy.updateUsageCountersAndThresholds(requestModel);

        CCATLogger.DEBUG_LOGGER.info("Finished serving addUsageCountersAndThresholds Request successfully");
    }

    public void updateUsageCounters(UpdateUsageCountersRequest updateUsageCountersRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Start serving updateUsageCounters Request");
        AddOrUpdateUsageRequest requestModel = new AddOrUpdateUsageRequest();
        requestModel.setToken(updateUsageCountersRequest.getToken());
        requestModel.setUsername(updateUsageCountersRequest.getUsername());
        requestModel.setRequestId(updateUsageCountersRequest.getRequestId());
        requestModel.setSessionId(updateUsageCountersRequest.getSessionId());

        requestModel.setId(updateUsageCountersRequest.getId());
        requestModel.setMsisdn(updateUsageCountersRequest.getMsisdn());
        requestModel.setCounterValue(updateUsageCountersRequest.getCounterValue());
        requestModel.setMonetaryValue1(updateUsageCountersRequest.getMonetaryValue1());

        usageCounterProxy.updateUsageCountersAndThresholds(requestModel);

        CCATLogger.DEBUG_LOGGER.info("Finished serving updateUsageCounters Request successfully");
    }

    public void updateUsageCountersAndThresholds(UpdateUsageCountersAndThresholdsRequest updateUsageCountersAndThresholdsRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Start serving updateUsageCountersAndThresholds Request");
        AddOrUpdateUsageRequest requestModel = new AddOrUpdateUsageRequest();
        requestModel.setToken(updateUsageCountersAndThresholdsRequest.getToken());
        requestModel.setUsername(updateUsageCountersAndThresholdsRequest.getUsername());
        requestModel.setRequestId(updateUsageCountersAndThresholdsRequest.getRequestId());
        requestModel.setSessionId(updateUsageCountersAndThresholdsRequest.getSessionId());

        requestModel.setId(updateUsageCountersAndThresholdsRequest.getId());
        requestModel.setMsisdn(updateUsageCountersAndThresholdsRequest.getMsisdn());
        requestModel.setCounterValue(updateUsageCountersAndThresholdsRequest.getCounterValue());
        requestModel.setMonetaryValue1(updateUsageCountersAndThresholdsRequest.getMonetaryValue1());
        requestModel.setUsageThresholdInformation(updateUsageCountersAndThresholdsRequest.getThresholds());

        usageCounterProxy.updateUsageCountersAndThresholds(requestModel);

        CCATLogger.DEBUG_LOGGER.info("Finished serving updateUsageCountersAndThresholds Request successfully");
    }
}
