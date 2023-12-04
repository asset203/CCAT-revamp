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
import com.asset.ccat.nba_service.models.requests.AcceptGiftRequest;
import com.asset.ccat.nba_service.models.requests.SendGiftRequest;
import com.asset.ccat.nba_service.proxy.http.HttpProxy;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mahmoud Shehab
 */
@Service
public class SendSmsGiftService {

    @Autowired
    Properties properties;

    @Autowired
    HttpProxy httpProxy;

    public void sendGift(SendGiftRequest sendGiftRequest) throws NBAException {
        String url = prepareRequest(sendGiftRequest);
        String result = httpProxy.sendNBARequest(url);
        parseResponse(result);
    }

    private String prepareRequest(SendGiftRequest sendGiftRequest) {
        String sendSMSUrl = properties.getSendSmsUrl();

        sendSMSUrl = sendSMSUrl
                .replace(Defines.HTTP_CONSTANTS.PROMO_ID_PH, properties.getPromoId());
        sendSMSUrl = sendSMSUrl
                .replace(Defines.HTTP_CONSTANTS.WLIST_ID_PH, properties.getWlistId());
        sendSMSUrl = sendSMSUrl
                .replace(Defines.HTTP_CONSTANTS.MSISDN_PH, sendGiftRequest.getMsisdn());
        sendSMSUrl = sendSMSUrl
                .replace(Defines.HTTP_CONSTANTS.TRIGGER_ID_PH, properties.getTriggerId());
        sendSMSUrl = sendSMSUrl
                .replace(Defines.HTTP_CONSTANTS.PARAM1_PH,
                        sendGiftRequest.getGiftSeqId());
        sendSMSUrl = sendSMSUrl
                .replace(Defines.HTTP_CONSTANTS.AGENT_ID_PH, sendGiftRequest.getUsername());

        return sendSMSUrl;
    }

    private void parseResponse(String result) throws NBAException {
        if (result == null || result.isEmpty()) {
            throw new NBAException(ErrorCodes.ERROR.NO_DATA_FOUND);
        }
        List<String> pairs = new ArrayList<>(10);
        String[] parameters = result.split(";");
        String errorCode = "";
        String errorMessgae = "";
        for (String param : parameters) {
            String[] pair = param.split("=");
            if (pair.length > 1) {
                pairs.add(pair[1]);
            }
        }
        if (pairs.size() > 1) {
            errorCode = pairs.get(0);
            errorMessgae = pairs.get(1);
        }
        CCATLogger.DEBUG_LOGGER.info("Error Code when call NBA is " + errorCode);
        validateErrorCode(errorCode, errorMessgae);
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
