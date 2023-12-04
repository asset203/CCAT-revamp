/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.nba_service.services;

import com.asset.ccat.nba_service.configurations.Properties;
import com.asset.ccat.nba_service.defines.Defines;
import com.asset.ccat.nba_service.defines.Defines.SP_CONSTANTS;
import com.asset.ccat.nba_service.defines.ErrorCodes;
import com.asset.ccat.nba_service.exceptions.NBAException;
import com.asset.ccat.nba_service.logger.CCATLogger;
import com.asset.ccat.nba_service.models.requests.GiftModel;
import com.asset.ccat.nba_service.models.requests.SubscriberRequest;
import com.asset.ccat.nba_service.proxy.db.StoredProcedureProxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedCaseInsensitiveMap;

/**
 *
 * @author Mahmoud Shehab
 */
@Service
public class GetNBAGiftsService {

    @Autowired
    Properties properties;

    @Autowired
    StoredProcedureProxy storedProcedureProxy;

    public List<GiftModel> getAllGifts(SubscriberRequest subscriberRequest) throws NBAException {
        HashMap hashMap = prepareRequest(subscriberRequest);
        HashMap resultMap = storedProcedureProxy.callStoredProcedure(hashMap);
        List<GiftModel> resultList = parseResponse(resultMap);
        return resultList;
    }

    private HashMap prepareRequest(SubscriberRequest subscriberRequest) {
        HashMap<String, Object> map = new HashMap();
        MapSqlParameterSource paramaters = new MapSqlParameterSource();
        map.put(Defines.SP_CONSTANTS.SP_NAME, properties.getGetOffersSpName());

        paramaters.addValue(Defines.SP_CONSTANTS.MSISDN_KEY, Defines.NBA_DEFINES.MSISDN_PREFIX + subscriberRequest.getMsisdn());
        paramaters.addValue(Defines.SP_CONSTANTS.IN_PARAM_1, subscriberRequest.getUsername());
        paramaters.addValue(Defines.SP_CONSTANTS.CHANNEL_ID_KEY, Integer.parseInt(properties.getChanCcat()));
        paramaters.addValue(Defines.SP_CONSTANTS.TRIGGER_ID_KEY, Integer.parseInt(properties.getGetPromoListTriggerId()));
        for (int i = 2; i <= 20; i++) {
            paramaters.addValue(Defines.SP_CONSTANTS.IN_PARAM_KEY + i, null);
        }
        map.put("input", paramaters);
        return map;
    }

    private List<GiftModel> parseResponse(HashMap resultMap) throws NBAException {
        List<GiftModel> giftsList = new ArrayList<>();

        if (resultMap == null || resultMap.isEmpty()) {
            throw new NBAException(ErrorCodes.ERROR.NO_DATA_FOUND);
        }
        String errorCode = String.valueOf(resultMap.get(Defines.SP_CONSTANTS.ERR_CODE_KEY.toLowerCase()));
        String desc = String.valueOf(resultMap.get(Defines.SP_CONSTANTS.ERR_DESC_KEY.toLowerCase()));
        CCATLogger.DEBUG_LOGGER.info("Error Code when call NBA is " + errorCode);
        validateErrorCode(errorCode, desc);
        Object resultGifts = resultMap.get(SP_CONSTANTS.GIFT_KEY.toLowerCase());
        if (resultGifts != null) {
            ArrayList<LinkedCaseInsensitiveMap> list = (ArrayList<LinkedCaseInsensitiveMap>) resultGifts;
            for (LinkedCaseInsensitiveMap giftMap : list) {
                CCATLogger.DEBUG_LOGGER.info("Gift response is [" + giftMap.keySet() + "]");
                GiftModel gift = new GiftModel();
                String id = (String) giftMap.get(Defines.GIFT_MODEL_ATTR.GIFT_ID);
                String giftSeqId = (String) giftMap.get(Defines.GIFT_MODEL_ATTR.GIFT_SEQ_ID);
                String description = (String) giftMap.get(Defines.GIFT_MODEL_ATTR.OFFER_DESC_AR);
                String status = (String) giftMap.get(Defines.GIFT_MODEL_ATTR.STATUS);
                String assignDate = (String) giftMap.get(Defines.GIFT_MODEL_ATTR.ASSIGN_DATE);
                String expiryDate = (String) giftMap.get(Defines.GIFT_MODEL_ATTR.REDEMPTION_EXP_DATE);
                String giftNoDays = (String) giftMap.get(Defines.GIFT_MODEL_ATTR.GIFT_VALIDITY);
                String shortCode = (String) giftMap.get(Defines.GIFT_MODEL_ATTR.GIFT_SHORT_CODE);
                String giftUnits = (String) giftMap.get(Defines.GIFT_MODEL_ATTR.GIFT_QUOTA);
                String giftFees = (String) giftMap.get(Defines.GIFT_MODEL_ATTR.GIFT_FEES);
                String incentive = (String) giftMap.get(Defines.GIFT_MODEL_ATTR.INCENTIVE);
                String totalIncentive = (String) giftMap.get(Defines.GIFT_MODEL_ATTR.REDEEM_ACCUMULATED_INCENTIVE);
                String totalPendingIncentive = (String) giftMap.get(Defines.GIFT_MODEL_ATTR.GRACE_ACCUMULATED_INCENTIVE);
                String salesScripts1 = (String) giftMap.get(Defines.GIFT_MODEL_ATTR.SALES_SCRIPT_1);
                String salesScripts3 = (String) giftMap.get(Defines.GIFT_MODEL_ATTR.SALES_SCRIPT_3);

                gift.setGiftId(id);
                gift.setGiftSeqId(giftSeqId);
                gift.setGiftDescription(description);
                gift.setGiftStatus(status);
                gift.setIsRejectedGift("3".equals(status));
                gift.setTimeOfAssignment(assignDate);
                gift.setTimeOfAssignmentExpiry(expiryDate);
                gift.setGiftNoDays(giftNoDays);
                gift.setGiftShortCode(shortCode);
                gift.setGiftUnits(giftUnits);
                gift.setGiftFees(giftFees);
                gift.setIncentive(incentive);
                gift.setTotalIncentive(totalIncentive);
                gift.setTotalPendingInsentive(totalPendingIncentive);
                gift.setSalesScript1(salesScripts1);
                gift.setSalesScript3(salesScripts3);
                giftsList.add(gift);
            }
        }
        return giftsList;
    }

    private void validateErrorCode(String errorCode, String desc) throws NBAException {
        if (!SP_CONSTANTS.REPLY_SUCESS.equals(errorCode)) {
            if (!SP_CONSTANTS.REPLY_NO_GIFTS_RETURNED.equals(errorCode)) {
                throw new NBAException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.VALIDATION);
            } else {
                throw new NBAException(ErrorCodes.ERROR.NBA_ERROR, " with code " + errorCode + " and description " + desc);
            }
        }
    }
}
