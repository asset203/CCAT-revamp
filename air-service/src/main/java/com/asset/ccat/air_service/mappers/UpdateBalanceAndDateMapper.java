/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.mappers;

import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.utils.AIRUtils;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
public class UpdateBalanceAndDateMapper {

    @Autowired
    AIRUtils aIRUtils;

    public void map(String msisdn, HashMap map) throws AIRServiceException, AIRException {
        long t1 = System.currentTimeMillis();
        long time = (System.currentTimeMillis() - t1);
        aIRUtils.validateAIRResponse(map, "updateBalanceAndDates", time, "");
        String responseCode = (String) map.get(AIRDefines.responseCode);
        aIRUtils.validateUCIPResponseCodes(responseCode);
    }

}
