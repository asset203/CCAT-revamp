/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.mappers;

import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.AccumulatorModel;
import com.asset.ccat.air_service.models.ServiceClassModel;
import com.asset.ccat.air_service.services.LookupsService;
import com.asset.ccat.air_service.utils.AIRUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
public class GetAccumulatorsMapper {

    @Autowired
    LookupsService lookupsService;

    @Autowired
    AIRUtils aIRUtils;

    public List<AccumulatorModel> map(String msisdn, HashMap map) throws AIRServiceException, AIRException {
        long t1 = System.currentTimeMillis();
        long time = (System.currentTimeMillis() - t1);
        aIRUtils.validateAIRResponse(map, "getBalance", time, "user.getNTAccount()");
        String responseCode = (String) map.get(AIRDefines.responseCode);
        aIRUtils.validateUCIPResponseCodes(responseCode);

        int scId = Integer.parseInt((String) map.get(AIRDefines.serviceClassCurrent));
        ServiceClassModel serviceClassModel = lookupsService.getServiceClasses().get(scId);
        CCATLogger.DEBUG_LOGGER.info("returned service id " + scId + " and service model is " + serviceClassModel);
        HashMap accumlatorsMap = new HashMap();
        if (serviceClassModel != null) {
            accumlatorsMap = serviceClassModel.getAccumlators();
        }

        ArrayList<AccumulatorModel> accumulatorInfo = (ArrayList<AccumulatorModel>) map.get(AIRDefines.accumulatorInformation);
        if (accumulatorInfo == null) {
            accumulatorInfo = new ArrayList<AccumulatorModel>();
        }
        for (int i = 0; i < accumulatorInfo.size(); i++) {
            AccumulatorModel accumulatorModel = accumulatorInfo.get(i);
            String desc = (String) accumlatorsMap.get(Integer.parseInt(accumulatorModel.getId()));
            accumulatorModel.setDescription(desc);
            //accumulatorModel.setResetDate(aIRUtils.formatfromAirToCCDate(accumulatorModel.getResetDate()));
            //accumulatorInfo.set(i, accumulatorModel);
        }
        return accumulatorInfo;
    }
}
