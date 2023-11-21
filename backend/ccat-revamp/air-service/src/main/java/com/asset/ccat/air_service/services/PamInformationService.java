package com.asset.ccat.air_service.services;

import com.asset.ccat.air_service.cache.AIRRequestsCache;
import com.asset.ccat.air_service.configurations.Properties;
import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.mappers.AddPamInformationMapper;
import com.asset.ccat.air_service.models.requests.pam_info.AddPamInformationRequest;
import com.asset.ccat.air_service.models.requests.pam_info.DeletePamInformationRequest;
import com.asset.ccat.air_service.models.requests.pam_info.EvaluatePamInformationRequest;
import com.asset.ccat.air_service.parser.AIRParser;
import com.asset.ccat.air_service.proxy.AIRProxy;
import com.asset.ccat.air_service.proxy.LookupProxy;
import com.asset.ccat.air_service.utils.AIRUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author wael.mohamed
 */
@Service
public class PamInformationService {

    @Autowired
    LookupProxy configServerProxy;
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
    AddPamInformationMapper pamInformationMapper;


    public void addPamInformation(AddPamInformationRequest pamInfoRequest) throws AIRServiceException, AIRException {
        String pamInfoXML = "";//for add 4 elements
        try {
            String xmlRequest = aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.ADD_PAM_INFORMATION);
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1");
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, aIRUtils.getCurrentFormattedDate());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME, properties.getOriginHostName());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, properties.getOriginNodeType());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, pamInfoRequest.getUsername());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, pamInfoRequest.getMsisdn());
            //replace $pamInformationList$ with TAG_ARRAY_DATA
            xmlRequest = xmlRequest.replace(AIRDefines.ADD_PAM_INFORMATION.PAM_INFORMATION_LIST, AIRDefines.AIR_TAGS.TAG_ARRAY_DATA);
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.pamInformationList);
            //1
            pamInfoXML += AIRDefines.AIR_TAGS.TAG_STRUCT_1MEMBER;
            pamInfoXML = pamInfoXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_1, AIRDefines.AIR_TAGS.TAG_MEMBER_I4 + AIRDefines.AIR_TAGS.TAG_MEMBER_1);
            pamInfoXML = pamInfoXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.pamServiceID);
            pamInfoXML = pamInfoXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, String.valueOf(pamInfoRequest.getPamId()));
            //2
            pamInfoXML = pamInfoXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_1, AIRDefines.AIR_TAGS.TAG_MEMBER_I4 + AIRDefines.AIR_TAGS.TAG_MEMBER_1);
            pamInfoXML = pamInfoXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.pamClassID);
            pamInfoXML = pamInfoXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, String.valueOf(pamInfoRequest.getPamClassId()));
            //3
            pamInfoXML = pamInfoXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_1, AIRDefines.AIR_TAGS.TAG_MEMBER_I4 + AIRDefines.AIR_TAGS.TAG_MEMBER_1);
            pamInfoXML = pamInfoXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.scheduleID);
            pamInfoXML = pamInfoXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, String.valueOf(pamInfoRequest.getPamScheduleId()));
            //4
            if (Objects.nonNull(pamInfoRequest.getCurrentPamPeriodId())) {
                pamInfoXML = pamInfoXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_1, AIRDefines.AIR_TAGS.TAG_MEMBER_STRING + AIRDefines.AIR_TAGS.TAG_MEMBER_1);
                pamInfoXML = pamInfoXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.currentPamPeriod);
                pamInfoXML = pamInfoXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, String.valueOf(pamInfoRequest.getCurrentPamPeriodId()));
            }
            //5
            if (Objects.nonNull(pamInfoRequest.getDeferredToDate())) {
                pamInfoXML = pamInfoXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_1, AIRDefines.AIR_TAGS.TAG_MEMBER_DATE + AIRDefines.AIR_TAGS.TAG_MEMBER_1);
                pamInfoXML = pamInfoXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.deferredToDate);
                pamInfoXML = pamInfoXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, aIRUtils.formatNewAIR(pamInfoRequest.getDeferredToDate()));
            }
            //6
            if (Objects.nonNull(pamInfoRequest.getPamServicePriorityId())) {
                pamInfoXML = pamInfoXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_1, AIRDefines.AIR_TAGS.TAG_MEMBER_I4);
                pamInfoXML = pamInfoXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.pamServicePriority);
                pamInfoXML = pamInfoXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, String.valueOf(pamInfoRequest.getPamServicePriorityId()));
            }
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, pamInfoXML);
            long t1 = System.currentTimeMillis();
            CCATLogger.DEBUG_LOGGER.debug(" AIR addPamInformation request is " + xmlRequest);
            String result = aIRProxy.sendAIRRequest(xmlRequest);
            CCATLogger.DEBUG_LOGGER.debug(" AIR addPamInformation response is " + result);
            HashMap resultMap = aIRParser.parse(result);
            pamInformationMapper.map(pamInfoRequest.getMsisdn(), resultMap);
        } catch (IOException | SAXException ex) {
            CCATLogger.DEBUG_LOGGER.debug("addPamInformation Ended with IOException | SAXException.");
            CCATLogger.DEBUG_LOGGER.error(" Error while parsing response " + ex);
            CCATLogger.ERROR_LOGGER.error(" Error while parsing response ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        } catch (AIRServiceException | AIRException ex) {
            CCATLogger.DEBUG_LOGGER.info("addPamInformation Ended with AIRException.");
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("addPamInformation Ended with Exception.");
            CCATLogger.DEBUG_LOGGER.error("Unknown error in addPamInformation() | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Unknown error in addPamInformation()", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    public void evaluatePamInfo(EvaluatePamInformationRequest evaluatePamRequest) throws AIRServiceException, AIRException {
        try {
            String xmlRequest = aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.RUN_PAM_INFORMATION);
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1");
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, aIRUtils.getCurrentFormattedDate());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME, properties.getOriginHostName());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, properties.getOriginNodeType());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, evaluatePamRequest.getUsername());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, evaluatePamRequest.getMsisdn());
            xmlRequest = xmlRequest.replace(AIRDefines.RUN_PAM_INFORMATION.PAM_SERVICE_ID, String.valueOf(evaluatePamRequest.getPamId()));
            long t1 = System.currentTimeMillis();
            CCATLogger.DEBUG_LOGGER.debug(" AIR evaluatePamInfo request is " + xmlRequest);
            String result = aIRProxy.sendAIRRequest(xmlRequest);
            CCATLogger.DEBUG_LOGGER.debug(" AIR evaluatePamInfo response is " + result);
            HashMap resultMap = aIRParser.parse(result);
            pamInformationMapper.mapEvaluate(evaluatePamRequest.getMsisdn(), resultMap);
        } catch (IOException | SAXException ex) {
            CCATLogger.DEBUG_LOGGER.debug("evaluatePamInfo Ended with IOException | SAXException.");
            CCATLogger.DEBUG_LOGGER.error(" Error while parsing response with error: " + ex.getMessage());
            CCATLogger.ERROR_LOGGER.error(" Error while parsing response ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_WHILE_PARSING_REQUEST);
        } catch (AIRServiceException | AIRException ex) {
            CCATLogger.DEBUG_LOGGER.debug("evaluatePamInfo Ended with AIRException.");
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("evaluatePamInfo Ended with Exception.");
            CCATLogger.DEBUG_LOGGER.error("Unknown error in evaluatePamInfo() | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Unknown error in evaluatePamInfo()", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    public void deletePamInfo(DeletePamInformationRequest deletePamRequest) throws AIRServiceException, AIRException {

        String pamInfoXML = "";
        try {
            String xmlRequest = aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.DELETE_PAM_INFORMATION);
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1");
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, aIRUtils.getCurrentFormattedDate());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME, properties.getOriginHostName());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, properties.getOriginNodeType());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, deletePamRequest.getUsername());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, deletePamRequest.getMsisdn());
            String pamInfoListXML = AIRDefines.AIR_TAGS.TAG_ARRAY_DATA;
            pamInfoListXML = pamInfoListXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.pamInformationList);
            pamInfoXML += AIRDefines.AIR_TAGS.TAG_STRUCT_1MEMBER;
            pamInfoXML = pamInfoXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_1, AIRDefines.AIR_TAGS.TAG_MEMBER_I4);
            pamInfoXML = pamInfoXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.pamServiceID);
            pamInfoXML = pamInfoXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, String.valueOf(deletePamRequest.getPamId()));
            pamInfoListXML = pamInfoListXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, pamInfoXML);
            xmlRequest = xmlRequest.replace(AIRDefines.ADD_PAM_INFORMATION.PAM_INFORMATION_LIST, pamInfoListXML);
            long t1 = System.currentTimeMillis();
            CCATLogger.DEBUG_LOGGER.debug(" AIR deletePamInfo request is " + xmlRequest);
            String result = aIRProxy.sendAIRRequest(xmlRequest);
            CCATLogger.DEBUG_LOGGER.debug(" AIR deletePamInfo response is " + result);
            HashMap resultMap = aIRParser.parse(result);
            pamInformationMapper.mapEvaluate(deletePamRequest.getMsisdn(), resultMap);
        } catch (IOException | SAXException ex) {
            CCATLogger.DEBUG_LOGGER.debug("deletePamInfo Ended with IOException | SAXException.");
            CCATLogger.DEBUG_LOGGER.error(" Error while parsing response " + ex);
            CCATLogger.ERROR_LOGGER.error(" Error while parsing response ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        } catch (AIRServiceException | AIRException ex) {
            CCATLogger.DEBUG_LOGGER.debug("deletePamInfo Ended with AIRException.");
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("deletePamInfo Ended with Exception.");
            CCATLogger.DEBUG_LOGGER.error("Unknown error in deletePamInfo() | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Unknown error in deletePamInfo()", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }

    }
}
