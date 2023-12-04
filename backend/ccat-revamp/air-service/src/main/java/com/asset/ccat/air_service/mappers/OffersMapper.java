/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.mappers;

import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.models.OfferModel;
import com.asset.ccat.air_service.utils.AIRUtils;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author wael.mohamed
 */
@Component
public class OffersMapper {

    @Autowired
    AIRUtils aIRUtils;

    public List<OfferModel> mapGetOffer(String msisdn, HashMap map) throws AIRServiceException , AIRException{
        long time = System.currentTimeMillis();
        aIRUtils.validateAIRResponse(map, AIRDefines.AIR_COMMAND_KEY.GET_OFFERS, time, "user.getNTAccount()");
        String responseCode = (String) map.get(AIRDefines.responseCode);
        aIRUtils.validateUCIPResponseCodes(responseCode);
        List<OfferModel> offers = (List<OfferModel>) map.get(AIRDefines.offerInformation);
        return offers;
    }

    public void mapDeleteOffer(String msisdn, HashMap map) throws AIRServiceException , AIRException{
        long time = System.currentTimeMillis();
        aIRUtils.validateAIRResponse(map, AIRDefines.AIR_COMMAND_KEY.DELETE_OFFER, time, "user.getNTAccount()");
        String responseCode = (String) map.get(AIRDefines.responseCode);
        aIRUtils.validateUCIPResponseCodes(responseCode);
    }

    public void mapAddAndUpdateOffer(String msisdn, HashMap map) throws AIRServiceException, AIRException {
        long time = System.currentTimeMillis();
        try {
            aIRUtils.validateAIRResponse(map, AIRDefines.AIR_COMMAND_KEY.GET_OFFERS, time, "user.getNTAccount()");
            String responseCode = (String) map.get(AIRDefines.responseCode);
            aIRUtils.validateUCIPResponseCodes(responseCode);
        } catch (AIRServiceException ex) {
            throw ex;
        }
    }
}
