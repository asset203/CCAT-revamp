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
import com.asset.ccat.air_service.parser.AIRParser;
import com.asset.ccat.air_service.proxy.AIRProxy;
import com.asset.ccat.air_service.utils.AIRUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
            //build request
            CCATLogger.DEBUG_LOGGER.debug("Building install subscriber air request");
            String airRequest = buildInstallSubscriberXml(installSubscriber, serviceOfferingPlan);
            CCATLogger.DEBUG_LOGGER.debug("Install subscriber air request is [" + airRequest + "]");
            //call air
            long t1 = System.currentTimeMillis();
            String airResponse = airProxy.sendAIRRequest(airRequest);
            long t2 = System.currentTimeMillis();

            //parsing air response
            CCATLogger.DEBUG_LOGGER.debug("Parsing air response");
            HashMap responseMap = airParser.parse(airResponse);

            //validating response code
            CCATLogger.DEBUG_LOGGER.debug("Validating air response code");
            airUtils.validateAIRResponse(responseMap, "Install-Subscriber", t2 - t1, installSubscriber.getSubscriberMsisdn());
            String responseCode = (String) responseMap.get(AIRDefines.responseCode);
            airUtils.validateACIPResponseCodes(responseCode);

        } catch (AIRServiceException | AIRException ex) {
            throw ex;
        } catch (SAXException | IOException ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to parse air response | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Failed to parse air response", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Unknown error in installSubscriber() | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Unknown error in installSubscriber()", ex);
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

    private String buildInstallSubscriberXml(InstallSubscriberRequest installSubscriber, ServiceOfferingPlan serviceOfferingPlan) {
        String installSubscriberXml = aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.INSTALL_SUBSCRIBER);

        // set basic request fields
        installSubscriberXml = installSubscriberXml.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, installSubscriber.getUsername().toLowerCase());
        installSubscriberXml = installSubscriberXml.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1");
        installSubscriberXml = installSubscriberXml.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, airUtils.getCurrentFormattedDate());
        installSubscriberXml = installSubscriberXml.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, properties.getOriginNodeType());
        installSubscriberXml = installSubscriberXml.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME, properties.getOriginHostName());
        // set install subscribers required fields
        String subscriberMsisdn = installSubscriber.getSubscriberMsisdn();
        String newBusinessPlanId = String.valueOf(installSubscriber.getBusinessPlanId());
        String tempBlockedFlag = installSubscriber.getTemporeryBlocked() ? AIRDefines.AIR_BOOLEAN_TRUE : AIRDefines.AIR_BOOLEAN_FALSE;
        installSubscriberXml = installSubscriberXml.replace(AIRDefines.INSTALL_SUBSCRIBER.SUBSCRIBER_NUMBER, subscriberMsisdn);
        installSubscriberXml = installSubscriberXml.replace(AIRDefines.INSTALL_SUBSCRIBER.SERVICE_CLASS_NEW, newBusinessPlanId);
        installSubscriberXml = installSubscriberXml.replace(AIRDefines.INSTALL_SUBSCRIBER.TEMPORARY_BLOCKED_FLAG, tempBlockedFlag);
        // set install subscribers optional fields
        String languageXml = "";
        String pamXml = "";
        String serviceOfferingXml = "";
        String accountGroupXml = "";
        //language
        if (Objects.nonNull(installSubscriber.getLanguageId())) {
            languageXml = AIRDefines.AIR_TAGS.TAG_MEMBER_INT;
            languageXml = languageXml.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.languageIDNew);
            languageXml = languageXml.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, String.valueOf(installSubscriber.getLanguageId()));
        }
        //pam
        if (Objects.nonNull(properties.getApplyPamFlag()) && properties.getApplyPamFlag()) {
            //CLASS_ID
            String classIdMember = AIRDefines.AIR_TAGS.TAG_MEMBER_I4;
            classIdMember = classIdMember.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.pamClassID);
            classIdMember = classIdMember.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, String.valueOf(properties.getPamClassId()));
            //SERVICE_ID
            String serviceIdMember = AIRDefines.AIR_TAGS.TAG_MEMBER_I4;
            serviceIdMember = serviceIdMember.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.pamServiceID);
            serviceIdMember = serviceIdMember.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, String.valueOf(properties.getPamServiceId()));
            //Schedule ID
            String scheduleIdMember = AIRDefines.AIR_TAGS.TAG_MEMBER_I4;
            scheduleIdMember = scheduleIdMember.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.scheduleID);
            scheduleIdMember = scheduleIdMember.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, String.valueOf(properties.getPamScheduleId()));
            //Struct containing the previous 3 members
            String threeMemberedStruct = AIRDefines.AIR_TAGS.TAG_STRUCT_3MEMBERS;
            threeMemberedStruct = threeMemberedStruct.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_1, serviceIdMember);
            threeMemberedStruct = threeMemberedStruct.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_2, classIdMember);
            threeMemberedStruct = threeMemberedStruct.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_3, scheduleIdMember);
            //Array containing the struct
            pamXml = AIRDefines.AIR_TAGS.TAG_ARRAY_DATA;
            pamXml = pamXml.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.pamInformationList);
            pamXml = pamXml.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, threeMemberedStruct);
        }
        //accountGroupId
        if (Objects.nonNull(installSubscriber.getAccountGroupId())) {
            accountGroupXml = AIRDefines.AIR_TAGS.TAG_MEMBER_INT;
            accountGroupXml = accountGroupXml.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.accountGroupID);
            accountGroupXml = accountGroupXml.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, String.valueOf(installSubscriber.getAccountGroupId()));
        }
        // service offering list
        if (Objects.nonNull(serviceOfferingPlan)) {
            StringBuilder serviceOfferingListXml = new StringBuilder();
            for (Map.Entry<Integer, Boolean> soBit : serviceOfferingPlan.getServicePlanBits().entrySet()) {
                String bitId = String.valueOf(soBit.getKey());
                String bitValue = soBit.getValue() ? "1" : "0";
                String serviceOfferingIDXML = AIRDefines.AIR_TAGS.TAG_MEMBER_I4;
                serviceOfferingIDXML = serviceOfferingIDXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.serviceOfferingID);
                serviceOfferingIDXML = serviceOfferingIDXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, bitId);
                String serviceOfferingActiveFlagXML = AIRDefines.AIR_TAGS.TAG_MEMBER_BOOLEAN;
                serviceOfferingActiveFlagXML = serviceOfferingActiveFlagXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.serviceOfferingActiveFlag);
                serviceOfferingActiveFlagXML = serviceOfferingActiveFlagXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, bitValue);
                String serviceOfferingItemXML = AIRDefines.AIR_TAGS.TAG_STRUCT_2MEMBERS;
                serviceOfferingItemXML = serviceOfferingItemXML.replace("$MEMBER_1$", serviceOfferingIDXML);
                serviceOfferingItemXML = serviceOfferingItemXML.replace("$MEMBER_2$", serviceOfferingActiveFlagXML);
                serviceOfferingListXml.append(serviceOfferingItemXML);
            }
            serviceOfferingXml += AIRDefines.AIR_TAGS.TAG_ARRAY_DATA;
            serviceOfferingXml = serviceOfferingXml.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.serviceOfferings);
            serviceOfferingXml = serviceOfferingXml.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, serviceOfferingListXml.toString());
        }
        installSubscriberXml = installSubscriberXml.replace(AIRDefines.INSTALL_SUBSCRIBER.LANGUAGE_ID_NEW, languageXml);
        installSubscriberXml = installSubscriberXml.replace(AIRDefines.INSTALL_SUBSCRIBER.PAM, pamXml);
        installSubscriberXml = installSubscriberXml.replace(AIRDefines.INSTALL_SUBSCRIBER.ACCOUNT_GROUP_ID, accountGroupXml);
        installSubscriberXml = installSubscriberXml.replace(AIRDefines.INSTALL_SUBSCRIBER.SERVICE_OFFERING, serviceOfferingXml);

        return installSubscriberXml;
    }
}
