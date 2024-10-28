package com.asset.ccat.air_service.services;

import com.asset.ccat.air_service.cache.AIRRequestsCache;
import com.asset.ccat.air_service.configurations.Properties;
import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.mappers.GetFamilyAndFriendsMapper;
import com.asset.ccat.air_service.models.FamilyAndFriendsModel;
import com.asset.ccat.air_service.models.RestrictionModel;
import com.asset.ccat.air_service.models.requests.SubscriberRequest;
import com.asset.ccat.air_service.models.requests.customer_care.family_and_friends.AddFamilyAndFriendsRequest;
import com.asset.ccat.air_service.models.requests.customer_care.family_and_friends.DeleteFamilyAndFriendsRequest;
import com.asset.ccat.air_service.models.requests.customer_care.family_and_friends.UpdateFamilyAndFriendsRequest;
import com.asset.ccat.air_service.parser.AIRParser;
import com.asset.ccat.air_service.proxy.AIRProxy;
import com.asset.ccat.air_service.utils.AIRUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Component
public class FamilyAndFriendsService {

    @Autowired
    GetFamilyAndFriendsMapper getFamilyAndFriendsMapper;
    @Autowired
    private AIRProxy airProxy;
    @Autowired
    private AIRParser airParser;
    @Autowired
    private AIRUtils airUtils;
    @Autowired
    private Properties properties;
    @Autowired
    private AIRRequestsCache aIRRequestsCache;
    @Autowired
    private LookupsService lookupsService;

    public List<FamilyAndFriendsModel> getSubscriberFAFList(SubscriberRequest subscriberRequest) throws AIRException, AIRServiceException {

        try {
            CCATLogger.DEBUG_LOGGER.debug("Start building get family and friends list request");
            String xmlRequest = buildGetFAFListXml(subscriberRequest, AIRDefines.FAMILY_AND_FRIENDS.FAF_OWNER_SUB_ID);
            CCATLogger.DEBUG_LOGGER.debug("Sending a Get-family-and-friends list to the air with request : {}", xmlRequest);
            long t1 = System.currentTimeMillis();
            String airResponse = airProxy.sendAIRRequest(xmlRequest);
            long t2 = System.currentTimeMillis();
            CCATLogger.DEBUG_LOGGER.debug("Parsing air response");
            HashMap responseMap = airParser.parse(airResponse);
            CCATLogger.DEBUG_LOGGER.debug("Validating air response code");
            airUtils.validateAIRResponse(responseMap, "get-FAF-list", t2 - t1, subscriberRequest.getMsisdn());
            String responseCode = (String) responseMap.get(AIRDefines.responseCode);
            airUtils.validateUCIPResponseCodes(responseCode);
            CCATLogger.DEBUG_LOGGER.debug("Mapping Family and Friends List");
            return getFamilyAndFriendsMapper.map(subscriberRequest.getMsisdn(),
                    responseMap, AIRDefines.FAMILY_AND_FRIENDS.FAF_OWNER_SUB_ID);
        } catch (AIRException | AIRServiceException ex) {
            throw ex;
        } catch (SAXException | IOException ex) {
            CCATLogger.DEBUG_LOGGER.error("SAXException | IOException occurred while parsing air response", ex);
            CCATLogger.ERROR_LOGGER.error("SAXException | IOException occurred while parsing air response", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception occurred while parsing air response", ex);
            CCATLogger.ERROR_LOGGER.error("Exception occurred while parsing air response", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    public void addSubscriberFAFList(AddFamilyAndFriendsRequest addFamilyAndFriendsRequest) throws AIRException, AIRServiceException {
        CCATLogger.DEBUG_LOGGER.debug("Start validating if number is black listed");
        List<RestrictionModel> blackList = lookupsService.getFafBlackList();
        if (isNumberInFafList(addFamilyAndFriendsRequest.getFamilyAndFriendsNumber(), blackList)) {
            throw new AIRServiceException(ErrorCodes.ERROR.NUMBER_IS_BLACK_LISTED);
        }
        CCATLogger.DEBUG_LOGGER.debug("Start validating if number is not white listed");
        List<RestrictionModel> whiteList = lookupsService.getFafWhiteList();
        if (!isNumberInFafList(addFamilyAndFriendsRequest.getFamilyAndFriendsNumber(), whiteList)) {
            throw new AIRServiceException(ErrorCodes.ERROR.NUMBER_IS_NOT_WHITE_LISTED);
        }
        doFafAction(addFamilyAndFriendsRequest.getMsisdn(),
                addFamilyAndFriendsRequest.getUsername(),
                addFamilyAndFriendsRequest.getFamilyAndFriendsNumber(),
                addFamilyAndFriendsRequest.getFamilyAndFriendsPlanId(),
                AIRDefines.FAMILY_AND_FRIENDS.FAF_ADD,
                AIRDefines.FAMILY_AND_FRIENDS.FAF_OWNER_SUB
        );
    }

    public void updateSubscriberFAFList(UpdateFamilyAndFriendsRequest updateFamilyAndFriendsRequest) throws AIRException, AIRServiceException {
        doFafAction(updateFamilyAndFriendsRequest.getMsisdn(),
                updateFamilyAndFriendsRequest.getUsername(),
                updateFamilyAndFriendsRequest.getFamilyAndFriendsNumber(),
                updateFamilyAndFriendsRequest.getFamilyAndFriendsPlanId(),
                AIRDefines.FAMILY_AND_FRIENDS.FAF_UPDATE,
                AIRDefines.FAMILY_AND_FRIENDS.FAF_OWNER_SUB
        );
    }

    public void deleteSubscriberFAFList(DeleteFamilyAndFriendsRequest deleteFamilyAndFriendsRequest) throws AIRException, AIRServiceException {
        doFafAction(deleteFamilyAndFriendsRequest.getMsisdn(),
                deleteFamilyAndFriendsRequest.getUsername(),
                deleteFamilyAndFriendsRequest.getFamilyAndFriendsNumber(),
                deleteFamilyAndFriendsRequest.getFamilyAndFriendsPlanId(),
                AIRDefines.FAMILY_AND_FRIENDS.FAF_DELETE,
                AIRDefines.FAMILY_AND_FRIENDS.FAF_OWNER_SUB
        );
    }

    private void doFafAction(String msisdn, String username,
                             String subscriberFAFNumber, Integer subscriberFAFPlanId,
                             String action, String owner) throws AIRException, AIRServiceException {
        try {
            String xmlRequest = buildUpdateFAFListXml(msisdn, username, subscriberFAFNumber, Integer.toString(subscriberFAFPlanId), action, owner);
            CCATLogger.DEBUG_LOGGER.debug("Update family and friends list air request is : " + xmlRequest);
            long t1 = System.currentTimeMillis();
            String airResponse = airProxy.sendAIRRequest(xmlRequest);
            long t2 = System.currentTimeMillis();
            CCATLogger.DEBUG_LOGGER.debug("Parsing air response");
            HashMap responseMap = airParser.parse(airResponse);
            CCATLogger.DEBUG_LOGGER.debug("Validating air response code");
            airUtils.validateAIRResponse(responseMap, "update-FAF-list", t2 - t1, msisdn);
            String responseCode = (String) responseMap.get(AIRDefines.responseCode);
            airUtils.validateUCIPResponseCodes(responseCode);
            CCATLogger.DEBUG_LOGGER.debug("Ended FamilyAndFriendsService - doFafAction()");
        } catch (AIRException | AIRServiceException ex) {
            throw ex;
        } catch (SAXException | IOException ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to parse air response | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Failed to parse air response", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Unknown error in doFafAction() | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Unknown error in doFafAction()", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    private String buildGetFAFListXml(SubscriberRequest subscriberRequest, String owner) {
        String xmlRequest = aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.GET_FAF_LIST);
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, subscriberRequest.getUsername().toLowerCase());
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1");
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, airUtils.getCurrentFormattedDate());
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, properties.getOriginNodeType());
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME, properties.getOriginHostName());
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, subscriberRequest.getMsisdn());
        xmlRequest = xmlRequest.replace(AIRDefines.FAMILY_AND_FRIENDS.PLACEHOLDERS.REQUESTED_OWNER, owner);

        return xmlRequest;
    }

    private String buildUpdateFAFListXml(String msisdn, String username,
                                         String subscriberFAFNumber, String subscriberFAFPlanId,
                                         String action, String owner) {
        String xmlRequest = aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.UPDATE_FAF_LIST);
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, username.toLowerCase());
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1");
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, airUtils.getCurrentFormattedDate());
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, properties.getOriginNodeType());
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME, properties.getOriginHostName());
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, msisdn);
        xmlRequest = xmlRequest.replace(AIRDefines.FAMILY_AND_FRIENDS.PLACEHOLDERS.OWNER, owner);
        xmlRequest = xmlRequest.replace(AIRDefines.FAMILY_AND_FRIENDS.PLACEHOLDERS.FAF_NUMBER, subscriberFAFNumber);
        xmlRequest = xmlRequest.replace(AIRDefines.FAMILY_AND_FRIENDS.PLACEHOLDERS.FAF_INDICATOR, subscriberFAFPlanId);
        xmlRequest = xmlRequest.replace(AIRDefines.FAMILY_AND_FRIENDS.PLACEHOLDERS.FAF_ACTION, action);

        return xmlRequest;
    }

    private boolean isNumberInFafList(String fafNumber, List<RestrictionModel> fafNumberPattersList) throws AIRServiceException {
        for (RestrictionModel patternModel : fafNumberPattersList) {
            if (Objects.nonNull(patternModel.getNumberPattern())
                    && !patternModel.getNumberPattern().isBlank()
                    && airUtils.isNumberValid(fafNumber, patternModel.getNumberPattern())) {
                return true;
            }
        }
        return false;
    }
}
