package com.asset.ccat.air_service.services;

import com.asset.ccat.air_service.cache.AIRRequestsCache;
import com.asset.ccat.air_service.configurations.Properties;
import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.mappers.UsageCountersMapper;
import com.asset.ccat.air_service.models.customer_care.UsageCountersModel;
import com.asset.ccat.air_service.models.customer_care.UsageThresholdInformationModel;
import com.asset.ccat.air_service.models.requests.customer_care.DeleteUsageThresholdsRequest;
import com.asset.ccat.air_service.models.requests.customer_care.GetAllUsageCountersRequest;
import com.asset.ccat.air_service.models.requests.customer_care.UpdateUsageCountersRequest;
import com.asset.ccat.air_service.models.responses.customer_care.GetAllUsageCountersResponse;
import com.asset.ccat.air_service.parsers.AIRParser;
import com.asset.ccat.air_service.proxy.AIRProxy;
import com.asset.ccat.air_service.utils.AIRUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author Assem.Hassan
 */
@Component
public class UsageCountersService {

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
    UsageCountersMapper usageCountersMapper;


    public GetAllUsageCountersResponse getUsageCounters(GetAllUsageCountersRequest getAllUsageCountersRequest) throws AIRServiceException, AIRException {
        try {
            String xmlRequest = aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.GET_USAGE_COUNTERS);
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, getAllUsageCountersRequest.getMsisdn());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, getAllUsageCountersRequest.getUsername().toLowerCase());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1");
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, aIRUtils.getCurrentFormattedDate());
            CCATLogger.DEBUG_LOGGER.debug(" AIR getAllUsageCounters request is " + xmlRequest);
            String result = aIRProxy.sendAIRRequest(xmlRequest);
            HashMap resultMap = aIRParser.parse(result);
            List<UsageCountersModel> list = usageCountersMapper.map(getAllUsageCountersRequest.getMsisdn(), resultMap);

            return new GetAllUsageCountersResponse(list);
        } catch (AIRServiceException | AIRException ex) {
            throw ex;
        } catch (SAXException | IOException ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to parse air response | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Failed to parse air response", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Unknown error in getAccumulators() | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Unknown error in getAccumulators()", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    public void deleteUsageThresholds(DeleteUsageThresholdsRequest deleteUTRequest) throws AIRServiceException, AIRException {
        try {
            //build request
            CCATLogger.DEBUG_LOGGER.debug("Building delete usage thresholds air request");
            String airRequest = buildDeleteUTXml(deleteUTRequest);
            CCATLogger.DEBUG_LOGGER.debug("Delete usage thresholds air request is [" + airRequest + "]");
            //call air
            long t1 = System.currentTimeMillis();
            String airResponse = aIRProxy.sendAIRRequest(airRequest);
            long t2 = System.currentTimeMillis();
            //parsing air response
            CCATLogger.DEBUG_LOGGER.debug("Parsing air response");
            HashMap responseMap = aIRParser.parse(airResponse);
            //validating response code
            CCATLogger.DEBUG_LOGGER.debug("Validating air response code");
            aIRUtils.validateAIRResponse(responseMap, "Delete Usage Thresholds", t2 - t1, "msidn");
            String responseCode = (String) responseMap.get(AIRDefines.responseCode);
            aIRUtils.validateACIPResponseCodes(responseCode);
        } catch (AIRServiceException | AIRException ex) {
            throw ex;
        } catch (SAXException | IOException ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to parse air response | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Failed to parse air response", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Unknown error in deleteUsageThresholds() | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Unknown error in deleteUsageThresholds()", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    public void addAndUpdateUsageCounters(UpdateUsageCountersRequest updateUsageCountersRequest) throws AIRServiceException {
        try {
            //build request
            CCATLogger.DEBUG_LOGGER.debug("Building UpdateUsageCounters air request");
            String airRequest = buildUpdateUsageCountersXml(updateUsageCountersRequest);
            CCATLogger.DEBUG_LOGGER.debug("UpdateUsageCounters air request is\n{}", airRequest);
            //call air
            long t1 = System.currentTimeMillis();
            String result = aIRProxy.sendAIRRequest(airRequest);
            long t2 = System.currentTimeMillis();
            //parsing air response
            CCATLogger.DEBUG_LOGGER.debug("Parsing air response");
            HashMap responseMap = aIRParser.parse(result);
            //validating response code
            CCATLogger.DEBUG_LOGGER.debug("Validating air response code");
            aIRUtils.validateAIRResponse(responseMap, "Update-Usage-Counters", t2 - t1, updateUsageCountersRequest.getMsisdn());
            String responseCode = (String) responseMap.get(AIRDefines.responseCode);
            aIRUtils.validateACIPResponseCodes(responseCode);
        } catch (AIRServiceException ex) {
            throw ex;
        } catch (IOException | SAXException ex) {
            CCATLogger.DEBUG_LOGGER.info(" Error while parsing response " + ex);
            CCATLogger.ERROR_LOGGER.error(" Error while parsing response ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Unknown error in addAndUpdateUsageCounters() | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Unknown error in addAndUpdateUsageCounters()", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    public String buildUpdateUsageCountersXml(UpdateUsageCountersRequest updateUsageCountersRequest) {
        String usageThresholdValueType = "";
        String updateUsageCountersXml = aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.UPDATE_USAGE_COUNTERS);
        // set basic request fields
        updateUsageCountersXml = updateUsageCountersXml.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID,
                updateUsageCountersRequest.getUsername().toLowerCase());
        updateUsageCountersXml = updateUsageCountersXml.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE,
                properties.getOriginNodeType());
        updateUsageCountersXml = updateUsageCountersXml.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME,
                properties.getOriginHostName());
        updateUsageCountersXml = updateUsageCountersXml.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID,
                "1");
        updateUsageCountersXml = updateUsageCountersXml.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP,
                aIRUtils.getCurrentFormattedDate());

        // set update usage required fields
        updateUsageCountersXml = updateUsageCountersXml.replace(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER,
                updateUsageCountersRequest.getMsisdn());
        updateUsageCountersXml = updateUsageCountersXml.replace(AIRDefines.UPDATE_USAGE_COUNTERS.USAGE_COUNTER_ID,
                String.valueOf(updateUsageCountersRequest.getId()));
        if (Objects.nonNull(updateUsageCountersRequest.getCounterValue())) {
            updateUsageCountersXml = updateUsageCountersXml.replace(AIRDefines.UPDATE_USAGE_COUNTERS.USAGE_COUNTER_VALUE_KEY,
                    AIRDefines.USAGE_COUNTER_VALUE_NEW);
            updateUsageCountersXml = updateUsageCountersXml.replace(AIRDefines.UPDATE_USAGE_COUNTERS.USAGE_COUNTER_VALUE,
                    updateUsageCountersRequest.getCounterValue());
            usageThresholdValueType += AIRDefines.usageThresholdValueNew;
        } else if (Objects.nonNull(updateUsageCountersRequest.getMonetaryValue1())) {
            updateUsageCountersXml = updateUsageCountersXml.replace(AIRDefines.UPDATE_USAGE_COUNTERS.USAGE_COUNTER_VALUE_KEY,
                    AIRDefines.USAGE_COUNTER_MONETARY_VALUE_NEW);
            updateUsageCountersXml = updateUsageCountersXml.replace(AIRDefines.UPDATE_USAGE_COUNTERS.USAGE_COUNTER_VALUE,
                    updateUsageCountersRequest.getMonetaryValue1());
            usageThresholdValueType += AIRDefines.usageThresholdMonetaryValueNew;
        }

        // Usage threshold information list - mandatory field
        String usageThresholdUpdatePlaceHolder = "";
        if (Objects.nonNull(updateUsageCountersRequest.getUsageThresholdInformation())) {
            StringBuilder thresholdInformationListXml = new StringBuilder();
            for (UsageThresholdInformationModel usageThreshold
                    : updateUsageCountersRequest.getUsageThresholdInformation()) {
                String usageThresholdID = String.valueOf(usageThreshold.getUsageThresholdID());
                // Usage_Threshold_ID
                String usageThresholdIDXML = AIRDefines.AIR_TAGS.TAG_MEMBER_I4;
                usageThresholdIDXML = usageThresholdIDXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.usageThresholdID);
                usageThresholdIDXML = usageThresholdIDXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, usageThresholdID);
                // Usage_Threshold_Value
                String usageThresholdValueXML = AIRDefines.AIR_TAGS.TAG_MEMBER_STRING;
                usageThresholdValueXML = usageThresholdValueXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, usageThresholdValueType);
                usageThresholdValueXML = usageThresholdValueXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, usageThreshold.getUsageThresholdValue());
                // Struct containing the previous 2 members
                String thresholdInformationItemXML = AIRDefines.AIR_TAGS.TAG_STRUCT_2MEMBERS;
                thresholdInformationItemXML = thresholdInformationItemXML.replace("$MEMBER_1$", usageThresholdIDXML);
                thresholdInformationItemXML = thresholdInformationItemXML.replace("$MEMBER_2$", usageThresholdValueXML);
                thresholdInformationListXml.append(thresholdInformationItemXML);
            }
            // Array containing the thresholdInformation list
            usageThresholdUpdatePlaceHolder += AIRDefines.AIR_TAGS.TAG_ARRAY_DATA;
            usageThresholdUpdatePlaceHolder = usageThresholdUpdatePlaceHolder.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.usageThresholdUpdateInformation);
            usageThresholdUpdatePlaceHolder = usageThresholdUpdatePlaceHolder.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, thresholdInformationListXml.toString());
        }
        // add list to request xml
        updateUsageCountersXml = updateUsageCountersXml.replace(AIRDefines.UPDATE_USAGE_COUNTERS.USAGE_THRESHOLD_INFORMATION,
                usageThresholdUpdatePlaceHolder);

        return updateUsageCountersXml;
    }

    private String buildDeleteUTXml(DeleteUsageThresholdsRequest deleteUTRequest) {
        String deleteThresholdsXml = aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.DELETE_USAGE_THRESHOLDS);
        // set basic request fields
        deleteThresholdsXml = deleteThresholdsXml.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID,
                deleteUTRequest.getUsername().toLowerCase());
        deleteThresholdsXml = deleteThresholdsXml.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1");
        deleteThresholdsXml = deleteThresholdsXml.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, aIRUtils.getCurrentFormattedDate());
        deleteThresholdsXml = deleteThresholdsXml.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, properties.getOriginNodeType());
        deleteThresholdsXml = deleteThresholdsXml.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME, properties.getOriginHostName());
        // set msisdn
        String subscriberMsisdn = deleteUTRequest.getMsisdn();
        deleteThresholdsXml = deleteThresholdsXml.replace(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, subscriberMsisdn);
        // set thresholds
        StringBuilder thresholdsXml = new StringBuilder();
        for (Integer thresholdId : deleteUTRequest.getUsageThresholdsIds()) {
            String thresholdMember = AIRDefines.AIR_TAGS.TAG_MEMBER_I4;
            thresholdMember = thresholdMember.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.usageThresholdID);
            thresholdMember = thresholdMember.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, thresholdId.toString());
            String thresholdStruct = AIRDefines.AIR_TAGS.TAG_STRUCT_1MEMBER;
            thresholdStruct = thresholdStruct.replace("$MEMBER_1$", thresholdMember);
            thresholdsXml.append(thresholdStruct);
        }
        deleteThresholdsXml = deleteThresholdsXml.replace("$usageThresholds$", thresholdsXml.toString());

        return deleteThresholdsXml;
    }
}
