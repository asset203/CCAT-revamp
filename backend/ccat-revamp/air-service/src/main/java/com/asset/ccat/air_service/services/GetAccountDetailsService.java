package com.asset.ccat.air_service.services;

import com.asset.ccat.air_service.cache.AIRRequestsCache;
import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.mappers.GetAccountDetailsMapper;
import com.asset.ccat.air_service.models.SubscriberAccountModel;
import com.asset.ccat.air_service.models.requests.SubscriberRequest;
import com.asset.ccat.air_service.parser.AIRParser;
import com.asset.ccat.air_service.proxy.AIRProxy;
import com.asset.ccat.air_service.utils.AIRUtils;
import com.asset.ccat.air_service.utils.ReplacePlaceholderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Mahmoud Shehab
 */
@Component
public class GetAccountDetailsService {

    @Autowired
    AIRRequestsCache aIRRequestsCache;
    @Autowired
    AIRProxy aIRProxy;
    @Autowired
    AIRUtils aIRUtils;
    @Autowired
    AIRParser aIRParser;
    @Autowired
    GetAccountDetailsMapper getAccountDetailsMapper;

    public SubscriberAccountModel getAccountDetails(SubscriberRequest subscriberRequest) throws AIRServiceException, AIRException {
        try {
            HashMap resultMap = getAccountDetailsMap(subscriberRequest);
            return getAccountDetailsMapper.map(subscriberRequest.getMsisdn(), resultMap);
        } catch (AIRServiceException | AIRException ex) {
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Unknown error in getAccountDetails() | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Unknown error in getAccountDetails()", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    public HashMap getAccountDetailsMap(SubscriberRequest subscriberRequest) throws AIRServiceException, AIRException {
        try {
            String xmlRequest = new ReplacePlaceholderBuilder()
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, subscriberRequest.getMsisdn())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, subscriberRequest.getUsername().toLowerCase())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1")
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, aIRUtils.getCurrentFormattedDate())
                    .buildUrl(aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.GET_ACCOUNT_DETAILS));
            CCATLogger.DEBUG_LOGGER.debug(" AIR getAccountDetails request is {}", xmlRequest);

            long startTime = System.currentTimeMillis();
            String result = aIRProxy.sendAIRRequest(xmlRequest);
            HashMap resultMap = aIRParser.parse(result);
            //  ArrayList serviceBits = (ArrayList) map.get(AIRDefines.serviceOfferings);
            aIRUtils.validateAIRResponse(resultMap, "GetAccountDetails", (System.currentTimeMillis() - startTime), subscriberRequest.getMsisdn());
            aIRUtils.validateUCIPResponseCodes((String) resultMap.get(AIRDefines.responseCode));
            //  SubscriberAccountModel accountModel = getAccountDetailsMapper.map(subscriberRequest.getMsisdn(), resultMap);
            return resultMap;
        } catch (AIRServiceException | AIRException ex) {
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception occurred while getting subscriber: ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception occurred while getting subscriber: ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        }
    }

    public Boolean isAccountExists(SubscriberRequest subscriberRequest) throws AIRServiceException, AIRException {

        try {
            getAccountDetailsMap(subscriberRequest);
            return true;
        } catch (AIRException ex) {
            if (AIRDefines.UCIPCodes.SUBSCRIBER_NOT_FOUND.equals(ex.getErrorCode() + "")) {
                return false;
            } else {
                throw ex;
            }
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Unknown error in isAccountExists() | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Unknown error in isAccountExists()", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }
}
