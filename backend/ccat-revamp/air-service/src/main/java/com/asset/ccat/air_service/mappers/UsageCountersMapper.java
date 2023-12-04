/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.mappers;

import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.models.customer_care.UsageCountersModel;
import com.asset.ccat.air_service.utils.AIRUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author assem.hassan
 */
@Component
public class UsageCountersMapper {

    @Autowired
    AIRUtils aIRUtils;

    public List<UsageCountersModel> map(String msisdn, HashMap map) throws AIRServiceException, AIRException {
        long time = System.currentTimeMillis();
        List<UsageCountersModel> allUsageCounters = null;
        aIRUtils.validateAIRResponse(map, AIRDefines.AIR_COMMAND_KEY.GET_USAGE_COUNTERS, time, msisdn);
        String responseCode = (String) map.get(AIRDefines.responseCode);
        aIRUtils.validateUCIPResponseCodes(responseCode);

        Object obj = map.get(AIRDefines.usageCounterUsageThresholdInformation);
        if (obj != null) {
            allUsageCounters = (List<UsageCountersModel>) obj;
        }
        return allUsageCounters;
    }
}
