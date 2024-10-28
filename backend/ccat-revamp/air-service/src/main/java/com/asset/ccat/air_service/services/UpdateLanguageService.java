package com.asset.ccat.air_service.services;

import com.asset.ccat.air_service.cache.AIRRequestsCache;
import com.asset.ccat.air_service.configurations.Properties;
import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.mappers.UpdateLanguageMapper;
import com.asset.ccat.air_service.models.requests.UpdateLanguageRequest;
import com.asset.ccat.air_service.parser.AIRParser;
import com.asset.ccat.air_service.proxy.AIRProxy;
import com.asset.ccat.air_service.utils.AIRUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author wael.mohamed
 */
@Component
public class UpdateLanguageService {

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
    UpdateLanguageMapper updateLanguageMapper;


    public void updateLanguage(UpdateLanguageRequest languageRequest) throws AIRServiceException, AIRException {
        try {
            String xmlRequest = aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.UPDATE_LANGUAGE);
            xmlRequest = xmlRequest.replace(AIRDefines.UPDATE_LANGUAGE_PLACEHOLDER.LANGUAGE_ID_NEW, languageRequest.getLanguageId().toString());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, languageRequest.getMsisdn());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1");
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, aIRUtils.getCurrentFormattedDate());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, languageRequest.getUsername().toLowerCase());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, properties.getOriginNodeType());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME, properties.getOriginHostName());
            CCATLogger.DEBUG_LOGGER.debug(" AIR update language request is " + xmlRequest);
            String result = aIRProxy.sendAIRRequest(xmlRequest);
            HashMap resultMap = aIRParser.parse(result);
            updateLanguageMapper.map(languageRequest.getMsisdn(), resultMap);
            CCATLogger.DEBUG_LOGGER.debug("UpdateLanguageService -> updateLanguage() Ended successfully.");
        } catch (AIRServiceException | AIRException ex) {
            CCATLogger.DEBUG_LOGGER.debug("UpdateLanguageService -> updateLanguage() Ended with AIRException.");
            throw ex;
        } catch (IOException | SAXException ex) {
            CCATLogger.DEBUG_LOGGER.debug("UpdateLanguageService -> updateLanguage() Ended with IOException | SAXException.");
            CCATLogger.DEBUG_LOGGER.info(" Error while parsing response " + ex);
            CCATLogger.ERROR_LOGGER.error(" Error while parsing response ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_WHILE_PARSING_REQUEST);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("UpdateLanguageService -> updateLanguage() Ended with Exception.");
            CCATLogger.DEBUG_LOGGER.info(" Unknown Error occured in updateLanguage() " + ex);
            CCATLogger.ERROR_LOGGER.error(" Unknown Error occured in updateLanguage() ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }
}
