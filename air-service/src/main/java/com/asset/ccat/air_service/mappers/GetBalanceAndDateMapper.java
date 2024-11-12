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
import com.asset.ccat.air_service.models.DedicatedAccount;
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
public class GetBalanceAndDateMapper {

    @Autowired
    LookupsService lookupsService;

    @Autowired
    AIRUtils aIRUtils;

    public List<DedicatedAccount> map(String msisdn, HashMap map) throws AIRServiceException, AIRException {
        long t1 = System.currentTimeMillis();
        long time = (System.currentTimeMillis() - t1);
        aIRUtils.validateAIRResponse(map, "getBalance", time, "user.getNTAccount()");
        String responseCode = (String) map.get(AIRDefines.responseCode);
        aIRUtils.validateUCIPResponseCodes(responseCode);

        int scId = Integer.parseInt((String) map.get(AIRDefines.serviceClassCurrent));
        ServiceClassModel serviceClassModel = lookupsService.getServiceClasses().get(scId);
        CCATLogger.DEBUG_LOGGER.info("returned service id " + scId + " and service model is " + serviceClassModel);
        HashMap dedicatedAccountsMap = new HashMap();
        if (serviceClassModel != null) {
            dedicatedAccountsMap = serviceClassModel.getDedAccounts();
        }

        ArrayList<DedicatedAccount> primeDedicatedAccounts = (ArrayList<DedicatedAccount>) map.get(AIRDefines.primeDedicatedAccountInformation);
        if (primeDedicatedAccounts == null) {
            primeDedicatedAccounts = new ArrayList<>();
        } else {
            for (DedicatedAccount dedicatedAccount : primeDedicatedAccounts) {
                DedicatedAccount cachedDedAccount = (DedicatedAccount) dedicatedAccountsMap.get(Integer.parseInt(dedicatedAccount.getId()));
                if (cachedDedAccount != null) {
                    dedicatedAccount.setDescription((cachedDedAccount.getDescription() == null) ? "" : cachedDedAccount.getDescription());
                    if (Integer.parseInt(dedicatedAccount.getId()) < 10) {
                        dedicatedAccount.setDescription2((cachedDedAccount.getDescription() == null) ? "0" + dedicatedAccount.getId() : cachedDedAccount.getDescription());
                    } else {
                        dedicatedAccount.setDescription2((cachedDedAccount.getDescription() == null) ? String.valueOf(dedicatedAccount.getId()) : cachedDedAccount.getDescription());
                    }
                    dedicatedAccount.setRatingFactor(cachedDedAccount.getRatingFactor());
                }

            }
        }
        return primeDedicatedAccounts;
    }
}
