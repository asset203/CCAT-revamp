package com.asset.ccat.air_service.services;

import com.asset.ccat.air_service.cache.AIRRequestsCache;
import com.asset.ccat.air_service.configurations.Properties;
import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.mappers.UpdateBalanceAndDateMapper;
import com.asset.ccat.air_service.models.requests.UpdateBalanceAndDateRequest;
import com.asset.ccat.air_service.models.requests.UpdateLimitRequest;
import com.asset.ccat.air_service.parsers.AIRParser;
import com.asset.ccat.air_service.proxy.AIRProxy;
import com.asset.ccat.air_service.utils.AIRUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Mahmoud Shehab
 */
@Component
public class UpdateBalanceAndDateService {

    @Autowired
    Properties properties;
    @Autowired
    AIRRequestsCache aIRRequestsCache;
    @Autowired
    AIRProxy aIRProxy;
    @Autowired
    AIRUtils aIRUtils;
    @Autowired
    AIRParser aIRParser;
    @Autowired
    UpdateBalanceAndDateMapper balanceAndDateMapper;
    @Autowired
    private UserLimitsService userLimitsService;


    public void updateBalanceAndDate(UpdateBalanceAndDateRequest balanceAndDateRequest) throws AIRServiceException, AIRException {
        try {
            String feesPT = aIRUtils.amountInPT(balanceAndDateRequest.getAdjustmentAmount());
            String adjustmentAmountRelative = setAmountTag(feesPT, balanceAndDateRequest.getAdjustmentMethod());
            String xmlRequest = aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.UPDATE_BALANCE_AND_DATES);
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, balanceAndDateRequest.getMsisdn());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID,
                    balanceAndDateRequest.getUsername().toLowerCase());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1");
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, aIRUtils.getCurrentFormattedDate());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, properties.getOriginNodeType());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME, properties.getOriginHostName());
            xmlRequest = xmlRequest.replace(AIRDefines.UPDATE_BALANCE_PLACEHOLDER.ADJUSTMENT_AMOUNT_RELATIVE,
                    adjustmentAmountRelative);
            xmlRequest = xmlRequest.replace(AIRDefines.UPDATE_BALANCE_PLACEHOLDER.EXTERNAL_DATA_1,
                    balanceAndDateRequest.getTransactionType());
            xmlRequest = xmlRequest.replace(AIRDefines.UPDATE_BALANCE_PLACEHOLDER.EXTERNAL_DATA_2,
                    balanceAndDateRequest.getTransactionCode());
            xmlRequest = xmlRequest.replace(AIRDefines.UPDATE_BALANCE_PLACEHOLDER.SUPERVESION_FEE_PERIOD, "");
            xmlRequest = xmlRequest.replace(AIRDefines.UPDATE_BALANCE_PLACEHOLDER.SERVICE_FEE_PERIOD, "");

            CCATLogger.DEBUG_LOGGER.debug(" AIR update balance and date request is " + xmlRequest);
            String result = aIRProxy.sendAIRRequest(xmlRequest);
            HashMap resultMap = aIRParser.parse(result);
            balanceAndDateMapper.map(balanceAndDateRequest.getMsisdn(), resultMap);
            CCATLogger.DEBUG_LOGGER.debug("UpdateBalanceAndDateService -> updateBalanceAndDate() : Starting Update User Limits ");
            UpdateLimitRequest updateLimitRequest = new UpdateLimitRequest(balanceAndDateRequest.getUserId(),
                    balanceAndDateRequest.getAdjustmentMethod(),
                    balanceAndDateRequest.getAdjustmentAmount(),0.0f);
            updateLimitRequest.setToken(balanceAndDateRequest.getToken());
            updateLimitRequest.setRequestId(balanceAndDateRequest.getRequestId());
            updateLimitRequest.setSessionId(balanceAndDateRequest.getSessionId());
            userLimitsService.updateLimits(updateLimitRequest);
            CCATLogger.DEBUG_LOGGER.debug("UpdateBalanceAndDateService -> updateBalanceAndDate() : Ending Update User Limits ");
        } catch (AIRServiceException | AIRException ex) {
            throw ex;
        } catch (IOException | SAXException ex) {
            CCATLogger.DEBUG_LOGGER.info(" Error while parsing response " + ex);
            CCATLogger.ERROR_LOGGER.error(" Error while parsing response ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Unknown error in updateBalanceAndDate() | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Unknown error in updateBalanceAndDate()", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    private String setAmountTag(String feesPT, Integer adjustmentMethod) {
        String adjustmentAmountRelative = "";
        Boolean doReplacement = false;
        if (feesPT != null && !feesPT.equals("") && !feesPT.equals("0")) {
            if (adjustmentMethod.equals(AIRDefines.UPDATE_BALANCE_SUBTRACT)) {
                feesPT = "-" + feesPT;
                doReplacement = true;
            } else if (adjustmentMethod.equals(AIRDefines.UPDATE_BALANCE_ADD)) {
                doReplacement = true;
            }
            if (doReplacement) {
                adjustmentAmountRelative = AIRDefines.AIR_TAGS.TAG_MEMBER_STRING;
                adjustmentAmountRelative = adjustmentAmountRelative.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.adjustmentAmountRelative);
                adjustmentAmountRelative = adjustmentAmountRelative.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, feesPT);
            }
        }
        return adjustmentAmountRelative;
    }
}