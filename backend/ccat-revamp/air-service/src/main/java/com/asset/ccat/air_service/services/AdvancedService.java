package com.asset.ccat.air_service.services;

import com.asset.ccat.air_service.cache.AIRRequestsCache;
import com.asset.ccat.air_service.configurations.Properties;
import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.ServiceClassModel;
import com.asset.ccat.air_service.models.ServiceOfferingPlan;
import com.asset.ccat.air_service.models.SubscriberAccountModel;
import com.asset.ccat.air_service.models.requests.SubscriberRequest;
import com.asset.ccat.air_service.models.requests.advanced.DisconnectSubscriberRequest;
import com.asset.ccat.air_service.models.requests.advanced.InstallSubscriberRequest;
import com.asset.ccat.air_service.models.shared.AIRServer;
import com.asset.ccat.air_service.parser.AIRParser;
import com.asset.ccat.air_service.proxy.AIRProxy;
import com.asset.ccat.air_service.utils.AIRUtils;
import com.asset.ccat.air_service.utils.ReplacePlaceholderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

/**
 * @author marwa.elshawarby
 */
@Component
public class AdvancedService {

    @Autowired
    private AIRProxy airProxy;
    @Autowired
    private AIRParser airParser;
    @Autowired
    private AIRUtils airUtils;
    @Autowired
    private Properties properties;
    @Autowired
    private LookupsService lookupsService;
    @Autowired
    private AIRRequestsCache aIRRequestsCache;
    @Autowired
    private GetAccountDetailsService getAccountDetailsService;
    @Autowired
    private BarringService barringService;


    public void installSubscriber(InstallSubscriberRequest installSubscriber) throws AIRServiceException, AIRException {

        try {
            //get account details to validate that user is not already subscribed
            SubscriberRequest accountDetailsRequest = new SubscriberRequest();
            accountDetailsRequest.setMsisdn(installSubscriber.getSubscriberMsisdn());
            accountDetailsRequest.setUsername(installSubscriber.getUsername());
            if (Boolean.TRUE.equals(getAccountDetailsService.isAccountExists(accountDetailsRequest))) {
                throw new AIRServiceException(ErrorCodes.ERROR.SUBSCRIBER_ALREADY_INSTALLED);
            }
            // set language to default if not set
            if (installSubscriber.getLanguageId() == null) {
                installSubscriber.setLanguageId(1); // Arabic by default
            }
            ServiceOfferingPlan serviceOfferingPlan = null;
            if (Objects.nonNull(installSubscriber.getServiceOfferingId())) {
                CCATLogger.DEBUG_LOGGER.debug("Getting service offering plan with bits");
                serviceOfferingPlan = lookupsService.getServiceOfferingPlansWithBits()
                        .get(installSubscriber.getServiceOfferingId());
            }

            AIRServer aIRServer = lookupsService.getAirServer();
            List<String> caps = splitString(aIRServer.getCapabilityValue());

            CCATLogger.DEBUG_LOGGER.debug("Building install subscriber air request");
            String airRequest = buildInstallSubscriberXml(installSubscriber, serviceOfferingPlan, caps);
            CCATLogger.DEBUG_LOGGER.debug("Install subscriber air request is [{}]", airRequest);

            long t1 = System.currentTimeMillis();
            String airResponse = airProxy.sendAIRRequest(airRequest);
            long t2 = System.currentTimeMillis();

            CCATLogger.DEBUG_LOGGER.debug("Parsing air response");
            HashMap responseMap = airParser.parse(airResponse);

            CCATLogger.DEBUG_LOGGER.debug("Validating air response code");
            airUtils.validateAIRResponse(responseMap, "Install-Subscriber", t2 - t1, installSubscriber.getSubscriberMsisdn());
            String responseCode = (String) responseMap.get(AIRDefines.responseCode);
            airUtils.validateACIPResponseCodes(responseCode);

        } catch (AIRServiceException | AIRException ex) {
            throw ex;
        } catch (SAXException | IOException ex) {
            CCATLogger.DEBUG_LOGGER.error("SAXException | IOException while parsing air response. ", ex);
            CCATLogger.ERROR_LOGGER.error("SAXException | IOException while parsing air response. ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception while parsing air response. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception while parsing air response. ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    public void disconnectSubscriber(DisconnectSubscriberRequest disconnectSubscriberRequest, Integer profileId) throws AIRServiceException, AIRException, ParserConfigurationException {
        if (disconnectSubscriberRequest.getIsBatch()) {
            //used in case of batch disconnection
            //performs different validations before disconnection than in case of single subscriber
            disconnectBatchSubscriber(disconnectSubscriberRequest);
        } else {
            disconnectSingleSubscriber(disconnectSubscriberRequest, profileId);
        }
    }
    private List<String> splitString(String input){
        if (input == null || input.isEmpty())
            return List.of();
        return Arrays.asList(input.split(","));
    }
    private void disconnectBatchSubscriber(DisconnectSubscriberRequest disconnectSubscriberRequest) throws AIRServiceException, AIRException {
        try {
            // get account details
            CCATLogger.DEBUG_LOGGER.debug("Getting account details for [" + disconnectSubscriberRequest.getSubscriberMsisdn() + "]");
            SubscriberRequest accountDetailsRequest = new SubscriberRequest();
            accountDetailsRequest.setMsisdn(disconnectSubscriberRequest.getSubscriberMsisdn());
            accountDetailsRequest.setUsername(disconnectSubscriberRequest.getUsername());
            SubscriberAccountModel accountModel = getAccountDetailsService.getAccountDetails(accountDetailsRequest);

            if (Objects.nonNull(disconnectSubscriberRequest.getValidateDisconnection())
                    && disconnectSubscriberRequest.getValidateDisconnection()) {

                CCATLogger.DEBUG_LOGGER.debug("Start validating subscriber [" + disconnectSubscriberRequest.getSubscriberMsisdn() + "] before disconnection");
                if (!Objects.equals(accountModel.getServiceClass().getCode(), properties.getSdDisconnectScCode())
                        && accountModel.getBalance() != 0) {
                    CCATLogger.DEBUG_LOGGER.error("Validating subscriber [" + disconnectSubscriberRequest.getSubscriberMsisdn() + "] before disconnection failed "
                            + "| S/C is not SD_Disconnect and Balance is not zero");
                    throw new AIRServiceException(ErrorCodes.ERROR.BID_DISCONNECT_ERR_SC_AND_BAL);
                }

                if (accountModel.getBalance() != 0) {
                    CCATLogger.DEBUG_LOGGER.error("Validating subscriber [" + disconnectSubscriberRequest.getSubscriberMsisdn() + "] before disconnection failed "
                            + "| Balance is not zero");
                    throw new AIRServiceException(ErrorCodes.ERROR.BID_DISCONNECT_ERR_BAL);
                }
                if (!Objects.equals(accountModel.getServiceClass().getCode(), properties.getSdDisconnectScCode())) {
                    CCATLogger.DEBUG_LOGGER.error("Validating subscriber [" + disconnectSubscriberRequest.getSubscriberMsisdn() + "] before disconnection failed "
                            + "| S/C is not SD_Disconnect");
                    throw new AIRServiceException(ErrorCodes.ERROR.BID_DISCONNECT_ERR_SC);
                }
                CCATLogger.DEBUG_LOGGER.debug("Validating subscriber [" + disconnectSubscriberRequest.getSubscriberMsisdn() + "] before disconnection succeeded");
            }
            //remove temporary block
            SubscriberRequest subscriberRequest = new SubscriberRequest();
            subscriberRequest.setRequestId(disconnectSubscriberRequest.getRequestId());
            subscriberRequest.setMsisdn(disconnectSubscriberRequest.getSubscriberMsisdn());
            subscriberRequest.setUsername(disconnectSubscriberRequest.getUsername());
            subscriberRequest.setSessionId(disconnectSubscriberRequest.getSessionId());
            subscriberRequest.setToken(disconnectSubscriberRequest.getToken());
            barringService.unbarTemporaryBlock(subscriberRequest, false);
            //build request
            CCATLogger.DEBUG_LOGGER.debug("Building disconnect subscriber air request");
            String airRequest = buildDisconnectSubscriberXml(disconnectSubscriberRequest);
            CCATLogger.DEBUG_LOGGER.debug("Disconnect subscriber air request is [" + airRequest + "]");
            //call air
            long t1 = System.currentTimeMillis();
            String airResponse = airProxy.sendAIRRequest(airRequest);
            long t2 = System.currentTimeMillis();
            //parsing air response
            CCATLogger.DEBUG_LOGGER.debug("Parsing air response");
            HashMap responseMap = airParser.parse(airResponse);
            //validating response code
            CCATLogger.DEBUG_LOGGER.debug("Validating air response code");
            airUtils.validateAIRResponse(responseMap, "Disconnect-Subscriber", (t2 - t1), disconnectSubscriberRequest.getSubscriberMsisdn());
            String responseCode = (String) responseMap.get(AIRDefines.responseCode);
            airUtils.validateACIPResponseCodes(responseCode);
            // disconnect charging inteface >> PENDING !!
        } catch (AIRServiceException | AIRException ex) {
            throw ex;
        } catch (SAXException | IOException ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to parse air response | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Failed to parse air response", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Unknown error in disconnectSingleSubscriber() | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Unknown error in disconnectSingleSubscriber()", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    private void disconnectSingleSubscriber(DisconnectSubscriberRequest disconnectSubscriberRequest, Integer profileId) throws AIRServiceException, AIRException, ParserConfigurationException {
        try {
            // get profile service classes
            CCATLogger.DEBUG_LOGGER.debug("Retrieving profile [" + profileId + "] service classes ");
            List<ServiceClassModel> profileServiceClasses = lookupsService.getProfilesServiceClasses().get(profileId);
            // get account details
            CCATLogger.DEBUG_LOGGER.debug("Getting account details for [" + disconnectSubscriberRequest.getSubscriberMsisdn() + "]");
            SubscriberRequest accountDetailsRequest = new SubscriberRequest();
            accountDetailsRequest.setMsisdn(disconnectSubscriberRequest.getSubscriberMsisdn());
            accountDetailsRequest.setUsername(disconnectSubscriberRequest.getUsername());
            SubscriberAccountModel accountModel = getAccountDetailsService.getAccountDetails(accountDetailsRequest);
            // check if account service class exists on profile
            CCATLogger.DEBUG_LOGGER.debug("Checking if account service class exists on profile ");
            boolean isServiceClassNotExists = true;
            if (accountModel.getServiceClass() != null) {
                for (ServiceClassModel sc : profileServiceClasses) {
                    if (Objects.equals(accountModel.getServiceClass().getCode(), sc.getCode())) {
                        isServiceClassNotExists = false;
                        break;
                    }
                }
            }
            if (isServiceClassNotExists) {
                CCATLogger.DEBUG_LOGGER.error("Account service class does not exist on profile ");
                throw new AIRServiceException(ErrorCodes.ERROR.SERVICE_CLASS_NOT_EXIST_IN_PROFILE);
            }
            //build request
            CCATLogger.DEBUG_LOGGER.debug("Building disconnect subscriber air request");
            String airRequest = buildDisconnectSubscriberXml(disconnectSubscriberRequest);
            CCATLogger.DEBUG_LOGGER.debug("Disconnect subscriber air request is [" + airRequest + "]");
            //call air
            long t1 = System.currentTimeMillis();
            String airResponse = airProxy.sendAIRRequest(airRequest);
            long t2 = System.currentTimeMillis();
            //parsing air response
            CCATLogger.DEBUG_LOGGER.debug("Parsing air response");
            HashMap responseMap = airParser.parse(airResponse);
            //validating response code
            CCATLogger.DEBUG_LOGGER.debug("Validating air response code");
            airUtils.validateAIRResponse(responseMap, "Disconnect-Subscriber", (t2 - t1), disconnectSubscriberRequest.getSubscriberMsisdn());
            String responseCode = (String) responseMap.get(AIRDefines.responseCode);
            airUtils.validateACIPResponseCodes(responseCode);
            // disconnect charging inteface >> PENDING !!
        } catch (AIRServiceException | AIRException ex) {
            throw ex;
        } catch (SAXException | IOException ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to parse air response | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Failed to parse air response", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        }
    }

    private String buildDisconnectSubscriberXml(DisconnectSubscriberRequest disconnectSubscriber) {

        String disconnectionCodeXML = "";
        String disconnectSubscriberXml = aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.DELETE_SUBSCRIBER);
        // set basic request fields
        disconnectSubscriberXml = disconnectSubscriberXml.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID,
                disconnectSubscriber.getUsername().toLowerCase());
        disconnectSubscriberXml = disconnectSubscriberXml.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1");
        disconnectSubscriberXml = disconnectSubscriberXml.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, airUtils.getCurrentFormattedDate());
        disconnectSubscriberXml = disconnectSubscriberXml.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, properties.getOriginNodeType());
        disconnectSubscriberXml = disconnectSubscriberXml.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME, properties.getOriginHostName());
        //set mandatory fields
        String subscriberMsisdn = disconnectSubscriber.getSubscriberMsisdn();
        disconnectSubscriberXml = disconnectSubscriberXml.replace(AIRDefines.INSTALL_SUBSCRIBER.SUBSCRIBER_NUMBER, subscriberMsisdn);
        // set optional fields
        if (Objects.nonNull(disconnectSubscriber.getDisconnectReasonId())) {
            disconnectionCodeXML = AIRDefines.AIR_TAGS.TAG_MEMBER_I4;
            disconnectionCodeXML = disconnectionCodeXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.deleteReasonCode);
            disconnectionCodeXML = disconnectionCodeXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, String.valueOf(disconnectSubscriber.getDisconnectReasonId()));
        }
        disconnectSubscriberXml = disconnectSubscriberXml.replace(AIRDefines.DISCONNECT_SUBSCRIBER.DELETE_REASON_CODE, disconnectionCodeXML);

        return disconnectSubscriberXml;
    }

    private String buildInstallSubscriberXml(InstallSubscriberRequest installSubscriber, ServiceOfferingPlan serviceOfferingPlan, List<String> caps) {
        String languageXml = buildLanguageXml(installSubscriber);
        String pamXml = buildPamXml();
        String accountGroupXml = buildAccountGroupXml(installSubscriber);
        String serviceOfferingXml = buildServiceOfferingXml(serviceOfferingPlan);
        String negotiatedCapabilities = buildNegotiatedCapabilitiesXml(caps);
        return new ReplacePlaceholderBuilder()
                // Basic request fields
                .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, installSubscriber.getUsername().toLowerCase())
                .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1")
                .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, airUtils.getCurrentFormattedDate())
                .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, properties.getOriginNodeType())
                .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME, properties.getOriginHostName())

                // Required fields
                .addPlaceholder(AIRDefines.INSTALL_SUBSCRIBER.SUBSCRIBER_NUMBER, installSubscriber.getSubscriberMsisdn())
                .addPlaceholder(AIRDefines.INSTALL_SUBSCRIBER.SERVICE_CLASS_NEW, String.valueOf(installSubscriber.getServiceClassId()))
                .addPlaceholder(AIRDefines.INSTALL_SUBSCRIBER.TEMPORARY_BLOCKED_FLAG, Boolean.TRUE.equals(installSubscriber.getTemporeryBlocked()) ? AIRDefines.AIR_BOOLEAN_TRUE : AIRDefines.AIR_BOOLEAN_FALSE)

                // Optional fields
                .addOptionalPlaceholder(AIRDefines.INSTALL_SUBSCRIBER.LANGUAGE_ID_NEW, languageXml)
                .addOptionalPlaceholder(AIRDefines.INSTALL_SUBSCRIBER.PAM, pamXml)
                .addOptionalPlaceholder(AIRDefines.INSTALL_SUBSCRIBER.ACCOUNT_GROUP_ID, accountGroupXml)
                .addOptionalPlaceholder(AIRDefines.INSTALL_SUBSCRIBER.SERVICE_OFFERING, serviceOfferingXml)
//                .addOptionalPlaceholder(AIRDefines.INSTALL_SUBSCRIBER.NEGOTIATED_CAPABILITIES, negotiatedCapabilities)

                .buildUrl(aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.INSTALL_SUBSCRIBER));
    }

    private String buildLanguageXml(InstallSubscriberRequest installSubscriber) {
        if (Objects.nonNull(installSubscriber.getLanguageId())) {
            return new ReplacePlaceholderBuilder()
                    .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.languageIDNew)
                    .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, String.valueOf(installSubscriber.getLanguageId()))
                    .buildUrl(AIRDefines.AIR_TAGS.TAG_MEMBER_INT);
        }
        return "";
    }

    private String buildPamXml() {
        if (Objects.nonNull(properties.getApplyPamFlag()) && properties.getApplyPamFlag()) {
            String classIdMember = buildMemberXml(AIRDefines.pamClassID, String.valueOf(properties.getPamClassId()));
            String serviceIdMember = buildMemberXml(AIRDefines.pamServiceID, String.valueOf(properties.getPamServiceId()));
            String scheduleIdMember = buildMemberXml(AIRDefines.scheduleID, String.valueOf(properties.getPamScheduleId()));

            String threeMemberedStruct = new ReplacePlaceholderBuilder()
                    .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_1, serviceIdMember)
                    .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_2, classIdMember)
                    .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_3, scheduleIdMember)
                    .buildUrl(AIRDefines.AIR_TAGS.TAG_STRUCT_3MEMBERS);

            return new ReplacePlaceholderBuilder()
                    .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.pamInformationList)
                    .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, threeMemberedStruct)
                    .buildUrl(AIRDefines.AIR_TAGS.TAG_ARRAY_DATA);
        }
        return "";
    }

    private String buildAccountGroupXml(InstallSubscriberRequest installSubscriber) {
        if (Objects.nonNull(installSubscriber.getAccountGroupId())) {
            return buildMemberXml(AIRDefines.accountGroupID, String.valueOf(installSubscriber.getAccountGroupId()));
        }
        return "";
    }

    private String buildServiceOfferingXml(ServiceOfferingPlan serviceOfferingPlan) {
        if (Objects.nonNull(serviceOfferingPlan)) {
            StringBuilder serviceOfferingListXml = new StringBuilder();

            for (Map.Entry<Integer, Boolean> soBit : serviceOfferingPlan.getServicePlanBits().entrySet()) {
                String serviceOfferingIDXML = buildMemberXml(AIRDefines.serviceOfferingID, String.valueOf(soBit.getKey()));
                String serviceOfferingActiveFlagXML = buildMemberXml(AIRDefines.serviceOfferingActiveFlag, soBit.getValue() ? "1" : "0");

                String serviceOfferingItemXML = new ReplacePlaceholderBuilder()
                        .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_1, serviceOfferingIDXML)
                        .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_2, serviceOfferingActiveFlagXML)
                        .buildUrl(AIRDefines.AIR_TAGS.TAG_STRUCT_2MEMBERS);

                serviceOfferingListXml.append(serviceOfferingItemXML);
            }

            return new ReplacePlaceholderBuilder()
                    .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.serviceOfferings)
                    .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, serviceOfferingListXml.toString())
                    .buildUrl(AIRDefines.AIR_TAGS.TAG_ARRAY_DATA);
        }
        return "";
    }

    private String buildMemberXml(String key, String value) {
        return new ReplacePlaceholderBuilder()
                .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, key)
                .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, value)
                .buildUrl(AIRDefines.AIR_TAGS.TAG_MEMBER_I4);
    }
    private String buildNegotiatedCapabilitiesXml(List<String> capabilities) {
        if (Objects.nonNull(capabilities) && !capabilities.isEmpty()) {
            ReplacePlaceholderBuilder negCapPlaceholder = new ReplacePlaceholderBuilder();
            StringBuilder capabilitiesValuesXml = new StringBuilder();

            negCapPlaceholder.addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, "negotiatedCapabilities");
            for (String cap : capabilities) {
                capabilitiesValuesXml.append("<value><i4>")
                        .append(cap)
                        .append("</i4></value>");
            }
            return new ReplacePlaceholderBuilder()
                    .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, "negotiatedCapabilities")
                    .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, capabilitiesValuesXml.toString())
                    .buildUrl(AIRDefines.AIR_TAGS.TAG_ARRAY_DATA);
        }

        return "";
    }
}


