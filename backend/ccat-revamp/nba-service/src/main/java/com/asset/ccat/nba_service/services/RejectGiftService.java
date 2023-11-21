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
import com.asset.ccat.nba_service.models.requests.RejectGiftRequest;
import com.asset.ccat.nba_service.proxy.db.StoredProcedureProxy;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mahmoud Shehab
 */
@Service
public class RejectGiftService {

    @Autowired
    Properties properties;

    @Autowired
    StoredProcedureProxy storedProcedureProxy;

    public void rejectService(RejectGiftRequest rejectGiftRequest) throws NBAException {
        HashMap hashMap = prepareRequest(rejectGiftRequest);
        HashMap resultMap = storedProcedureProxy.callStoredProcedure(hashMap);
        parseResponse(resultMap);
    }

    private HashMap prepareRequest(RejectGiftRequest rejectGiftRequest) {
        HashMap<String, Object> map = new HashMap();
        MapSqlParameterSource paramaters = new MapSqlParameterSource();
        map.put(Defines.SP_CONSTANTS.SP_NAME, properties.getRejectOfferSpName());

        paramaters.addValue(Defines.SP_CONSTANTS.MSISDN_KEY, Defines.NBA_DEFINES.MSISDN_PREFIX + rejectGiftRequest.getMsisdn());
        paramaters.addValue(Defines.SP_CONSTANTS.AGENT_ID_KEY, rejectGiftRequest.getUsername());
        paramaters.addValue(Defines.SP_CONSTANTS.CHANNEL_ID_KEY, Integer.parseInt(properties.getChanCcat()));
        paramaters.addValue(Defines.SP_CONSTANTS.GIFT_SEQ_ID_KEY, rejectGiftRequest.getGiftSeqId());
        map.put("input", paramaters);
        return map;
    }

    private void parseResponse(HashMap resultMap) throws NBAException {
        if (resultMap == null || resultMap.isEmpty()) {
            throw new NBAException(ErrorCodes.ERROR.NO_DATA_FOUND);
        }
        String errorCode = String.valueOf(resultMap.get(Defines.SP_CONSTANTS.ERR_CODE_KEY.toLowerCase()));
        String desc = String.valueOf(resultMap.get(Defines.SP_CONSTANTS.ERR_DESC_KEY.toLowerCase()));
        CCATLogger.DEBUG_LOGGER.info("Error Code when call NBA is " + errorCode);
        validateErrorCode(errorCode, desc);
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
