package com.asset.ccat.air_service.services;

import com.asset.ccat.air_service.cache.AIRRequestsCache;
import com.asset.ccat.air_service.configurations.Properties;
import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.mappers.GetCommunitiesMapper;
import com.asset.ccat.air_service.models.CommunitiesModel;
import com.asset.ccat.air_service.models.responses.customer_care.GetCommunitiesResponse;
import com.asset.ccat.air_service.parser.AIRParser;
import com.asset.ccat.air_service.proxy.AIRProxy;
import com.asset.ccat.air_service.utils.AIRUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Component
public class CommunitiesService {

    @Autowired
    private AIRRequestsCache airRequestsCache;
    @Autowired
    private Properties properties;
    @Autowired
    private AIRUtils airUtils;
    @Autowired
    private AIRProxy airProxy;
    @Autowired
    private AIRParser airParser;
    @Autowired
    private GetCommunitiesMapper getCommunitiesMapper;


    public void updateCommunities(String msisdn, ArrayList<CommunitiesModel> currentList,
                                  ArrayList<CommunitiesModel> newList, String username) throws AIRException, AIRServiceException {
        try {
            String newL = buildCommunitiesNewList(newList);
            String currentL = buildCommunitiesNewList(currentList);
            String xmlRequest = buildUpdateCommunitiesXml(msisdn, currentL, newL, username);
            CCATLogger.DEBUG_LOGGER.debug("Update Communities Air request is : " + xmlRequest);
            long t1 = System.currentTimeMillis();
            String airResponse = airProxy.sendAIRRequest(xmlRequest);
            long t2 = System.currentTimeMillis();
            CCATLogger.DEBUG_LOGGER.debug("Update Communities Air response is : " + airResponse);
            CCATLogger.DEBUG_LOGGER.debug("Parsing air response");
            HashMap responseMap = airParser.parse(airResponse);
            CCATLogger.DEBUG_LOGGER.debug("Validating air response code");
            airUtils.validateAIRResponse(responseMap, "Update-Communities", t2 - t1, msisdn);
            String responseCode = (String) responseMap.get(AIRDefines.responseCode);
            airUtils.validateACIPResponseCodes(responseCode);
        } catch (AIRException | AIRServiceException ex) {
            throw ex;
        } catch (SAXException | IOException ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to parse air response | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Failed to parse air response", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Unknown error in updateCommunities() | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Unknown error in updateCommunities()", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    private String buildCommunitiesNewList(ArrayList<CommunitiesModel> newList) {
        StringBuilder communityNewXML = new StringBuilder();
        for (int i = 0; i < newList.size(); i++) {
            String communityItem = AIRDefines.AIR_TAGS.TAG_STRUCT_1MEMBER;
            String communityID = AIRDefines.AIR_TAGS.TAG_MEMBER_I4;
            communityID = communityID.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.communityID);
            communityID = communityID.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, String.valueOf(newList.get(i).getCommunityId()));
            communityItem = communityItem.replace("$MEMBER_1$", communityID);
            communityNewXML.append(communityItem);
        }
        return communityNewXML.toString();
    }

    private String buildUpdateCommunitiesXml(String msisdn, String currentList, String newL, String username) {
        String xmlRequest = airRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.UPDATE_COMMUNITIES);
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, username.toLowerCase());
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, properties.getOriginNodeType());
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME, properties.getOriginHostName());
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1");
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, airUtils.getCurrentFormattedDate());
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, msisdn);
        xmlRequest = xmlRequest.replace(AIRDefines.UPDATE_COMMUNITIES.COMMUNITY_INFORMATION_NEW, newL);
        xmlRequest = xmlRequest.replace(AIRDefines.UPDATE_COMMUNITIES.COMMUNITY_INFORMATION_CURRENT, currentList);

        return xmlRequest;
    }

    public GetCommunitiesResponse getCommunities(String msisdn, String username) throws AIRServiceException {
        // TODO Auto-generated method stub
        try {
            String xmlRequest = buildGetSelectedCommunitiesXml(msisdn, username);
            CCATLogger.DEBUG_LOGGER.debug("Get Communities list Air request is : " + xmlRequest);
            long t1 = System.currentTimeMillis();
            String airResponse = airProxy.sendAIRRequest(xmlRequest);
            long t2 = System.currentTimeMillis();
            CCATLogger.DEBUG_LOGGER.debug("Get Communities list Air response is : " + airResponse);
            CCATLogger.DEBUG_LOGGER.debug("Parsing air response");
            HashMap responseMap = airParser.parse(airResponse);
            CCATLogger.DEBUG_LOGGER.debug("Validating air response code");
            airUtils.validateAIRResponse(responseMap, "Get-Communities", t2 - t1, msisdn);
            String responseCode = (String) responseMap.get(AIRDefines.responseCode);
            airUtils.validateUCIPResponseCodes(responseCode);
            CCATLogger.DEBUG_LOGGER.debug("Mapping Communities Response");
            GetCommunitiesResponse response = getCommunitiesMapper.map(msisdn, responseMap);
            CCATLogger.DEBUG_LOGGER.info("Finished serving get communities request successfully");
            return response;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Unknown error in getSelectedCommunities() | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Unknown error in getSelectedCommunities()", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    private String buildGetSelectedCommunitiesXml(String msisdn, String username) {
        // TODO Auto-generated method stub
        String xmlRequest = airRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.GET_COMMUNITIES_LIST);
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1");
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, airUtils.getCurrentFormattedDate());
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, msisdn);
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME, properties.getOriginHostName());
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, properties.getOriginNodeType());
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, username.toLowerCase());

        return xmlRequest;
    }
}
