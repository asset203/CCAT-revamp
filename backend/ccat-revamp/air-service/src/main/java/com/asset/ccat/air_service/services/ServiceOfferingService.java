package com.asset.ccat.air_service.services;

import com.asset.ccat.air_service.cache.AIRRequestsCache;
import com.asset.ccat.air_service.configurations.Properties;
import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.ServiceOfferingBitsModel;
import com.asset.ccat.air_service.models.customer_care.service_offering_details_models.ServiceOfferingPlanBitModel;
import com.asset.ccat.air_service.models.customer_care.service_offering_details_models.ServiceOfferingPlanModel;
import com.asset.ccat.air_service.models.customer_care.service_offering_details_models.lookup_models.ServiceOfferingBitLookupModel;
import com.asset.ccat.air_service.models.customer_care.service_offering_details_models.lookup_models.ServiceOfferingPlanBitLookupModel;
import com.asset.ccat.air_service.models.requests.UpdateServiceOfferingRequest;
import com.asset.ccat.air_service.models.requests.customer_care.GetCurrentServiceOfferingPlan;
import com.asset.ccat.air_service.parser.AIRParser;
import com.asset.ccat.air_service.proxy.AIRProxy;
import com.asset.ccat.air_service.utils.AIRUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Component
public class ServiceOfferingService {
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


    public ServiceOfferingPlanModel getCurrentServiceOffering(GetCurrentServiceOfferingPlan request) throws AIRServiceException, AIRException {
        CCATLogger.DEBUG_LOGGER.debug("Getting subscriber account details");
        HashMap map = getAccountDetailsService.getAccountDetailsMap(request);
        List<ServiceOfferingBitsModel> currentBitsList = (ArrayList) map.get(AIRDefines.serviceOfferings);
        if (Objects.isNull(currentBitsList) || currentBitsList.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.info("No current service offering found");
            throw new AIRServiceException(ErrorCodes.ERROR.NO_DATA_FOUND);
        }
        CCATLogger.DEBUG_LOGGER.debug("Getting service offering bits lookup");
        HashMap<Integer, ServiceOfferingBitLookupModel> serviceOfferingBitsLookup = lookupsService.getServiceOfferingBitsLookup();
        CCATLogger.DEBUG_LOGGER.debug("Getting service offering plans lookup");
        HashMap<Integer, ServiceOfferingPlanBitLookupModel> plansLookup = lookupsService.getServiceOfferingPlansLookup();
        Double currentDecimalValue = 0.0;
        // Mapping retrieved current service bits to service bits lookup model
        List<ServiceOfferingPlanBitModel> bits = new ArrayList<>();
        for (ServiceOfferingBitsModel bit : currentBitsList) {
            ServiceOfferingPlanBitModel bitModel = new ServiceOfferingPlanBitModel();
            bitModel.setIsEnabled(Boolean.valueOf(bit.getValue())); // set isEnabled flag
            bitModel.setBitPosition(Integer.valueOf(bit.getKey())); // set bitPosition
            //calculate the bits decimal representation
            if (bitModel.getIsEnabled()) {
                currentDecimalValue += Math.pow(2, bitModel.getBitPosition());
            }
            // if bit lookup model is found start setting bitName from it
            if (Objects.nonNull(serviceOfferingBitsLookup)
                    && !serviceOfferingBitsLookup.isEmpty()
                    && serviceOfferingBitsLookup.get(bitModel.getBitPosition()) != null) {
                ServiceOfferingBitLookupModel bitDetailsModel = serviceOfferingBitsLookup.get(bitModel.getBitPosition());
                if (bitDetailsModel.getServiceClassDesc() != null
                        && bitDetailsModel.getServiceClassDesc().get(request.getServiceClassId()) != null) {
                    // set bitName to service class bit description if found
                    bitModel.setBitName(bitDetailsModel.getServiceClassDesc().get(request.getServiceClassId()));
                } else {
                    // set bitName to lookup bit name
                    bitModel.setBitName(bitDetailsModel.getBitName());
                }
            } else {
                // if no lookup found set bit name as retrieved from air
                bitModel.setBitName(bit.getName());
            }
            String profileSOB = request.getProfileSOB() == null ? "" : request.getProfileSOB();

            if (Objects.nonNull(profileSOB) && profileSOB.isBlank()) {
                profileSOB = profileSOB.trim();
                profileSOB = profileSOB.startsWith(",") ? profileSOB : "," + profileSOB;
                profileSOB = profileSOB.endsWith(",") ? profileSOB : profileSOB + ",";
            }
            if (profileSOB.isEmpty() || profileSOB.contains("," + bitModel.getBitPosition() + ",")) {
                bitModel.setDisabled(false);
            } else {
                bitModel.setDisabled(true);
            }
            bits.add(bitModel);
        }

        // start generating service offering plan model
        ServiceOfferingPlanModel currentPlan = new ServiceOfferingPlanModel();
        final Integer finalCurrentDecimal = currentDecimalValue.intValue();

        // set plan name and id from lookup by comparing previously calculated decimal value
        // of service bits retrieved from air with each plan defined
        if (Objects.nonNull(plansLookup) && !plansLookup.isEmpty()) {
            plansLookup.forEach((planId, planLookupModel) -> {
                if (finalCurrentDecimal.equals(plansLookup.get(planId).getDecimalValue())) {
                    //Setting Plan ID & Plan Name based on serviceClassId
                    currentPlan.setId(planId);
                    HashMap<Integer, String> serviceClassDescForPlans = planLookupModel.getServiceClassDisc();
                    String planName = "";
                    if (Objects.nonNull(serviceClassDescForPlans)
                            && serviceClassDescForPlans.containsKey(request.getServiceClassId())) {
                        planName = serviceClassDescForPlans.get(
                                request.getServiceClassId()) + "(" + planLookupModel.getDecimalValue() + ")";
                    } else {
                        planName = planLookupModel.getPlanName() + "(" + planLookupModel.getDecimalValue() + ")";
                    }
                    currentPlan.setName(planName);
                }
            });
        }
        // if no lookup plan found set plan name to unknown
        if (Objects.isNull(currentPlan.getId())) {
            currentPlan.setId(-1);
            currentPlan.setName("Unknown(" + finalCurrentDecimal + ")");
        }
        currentPlan.setBits(bits);

        return currentPlan;
    }

    public void updateServiceOffering(UpdateServiceOfferingRequest request) throws AIRServiceException, AIRException {
        try {
            String xmlRequest = buildUpdateServiceOfferingXml(request.getMsisdn(), request.getUsername(), request.getNewServiceOffering().getBits());
            CCATLogger.DEBUG_LOGGER.debug("Update service offering Air request is : " + xmlRequest);
            long t1 = System.currentTimeMillis();
            String airResponse = airProxy.sendAIRRequest(xmlRequest);
            long t2 = System.currentTimeMillis();
            CCATLogger.DEBUG_LOGGER.debug("Parsing air response");
            HashMap responseMap = airParser.parse(airResponse);
            CCATLogger.DEBUG_LOGGER.debug("Validating air response code");
            airUtils.validateAIRResponse(responseMap, "Update-Service-Offering", t2 - t1, request.getMsisdn());
            String responseCode = (String) responseMap.get(AIRDefines.responseCode);
            airUtils.validateUCIPResponseCodes(responseCode);
            CCATLogger.DEBUG_LOGGER.debug("Finished serving update service offering request successfully");
        } catch (AIRException | AIRServiceException ex) {
            throw ex;
        } catch (SAXException | IOException ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to parse air response | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Failed to parse air response", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Unknown error in updateServiceOffering() | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Unknown error in updateServiceOffering()", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    private String buildUpdateServiceOfferingXml(String msisdn, String username, List<ServiceOfferingPlanBitModel> serviceBits) {
        String xmlRequest = aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.UPDATE_SERVICE_OFFERING);
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, username.toLowerCase());
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1");
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, airUtils.getCurrentFormattedDate());
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, properties.getOriginNodeType());
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME, properties.getOriginHostName());
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, msisdn);

        StringBuilder serviceOfferingsListXML = new StringBuilder();
        for (int i = 0; i < serviceBits.size(); i++) {
            Integer serviceOfferingID = serviceBits.get(i).getBitPosition();
            String serviceOfferingActiveFlag = (serviceBits.get(i).getIsEnabled() ? "1" : "0");
            String serviceOfferingIDXML = AIRDefines.AIR_TAGS.TAG_MEMBER_I4;
            serviceOfferingIDXML = serviceOfferingIDXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.serviceOfferingID);
            serviceOfferingIDXML = serviceOfferingIDXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, serviceOfferingID.toString());
            String serviceOfferingActiveFlagXML = AIRDefines.AIR_TAGS.TAG_MEMBER_BOOLEAN;
            serviceOfferingActiveFlagXML = serviceOfferingActiveFlagXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.serviceOfferingActiveFlag);
            serviceOfferingActiveFlagXML = serviceOfferingActiveFlagXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, serviceOfferingActiveFlag);
            String serviceOfferingXML = AIRDefines.AIR_TAGS.TAG_STRUCT_2MEMBERS;
            serviceOfferingXML = serviceOfferingXML.replace("$MEMBER_1$", serviceOfferingIDXML);
            serviceOfferingXML = serviceOfferingXML.replace("$MEMBER_2$", serviceOfferingActiveFlagXML);
            serviceOfferingsListXML.append(serviceOfferingXML);
        }
        String serviceOfferingXML = "";
        if (Objects.nonNull(serviceOfferingsListXML) && !serviceOfferingsListXML.toString().equals("")) {
            serviceOfferingXML += AIRDefines.AIR_TAGS.TAG_ARRAY_DATA;
            serviceOfferingXML = serviceOfferingXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.serviceOfferings);
            serviceOfferingXML = serviceOfferingXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, serviceOfferingsListXML.toString());
        }
        xmlRequest = xmlRequest.replace(AIRDefines.UPDATE_SERVICE_OFFERING.SERVICE_OFFERING, serviceOfferingXML);

        return xmlRequest;
    }

    public static void main(String[] args) {
        String msisdn = "1099900306";
        String username = "MahmoudShehab";
        List<ServiceOfferingPlanBitModel> bitModels = new ArrayList<>();
        bitModels.add(new ServiceOfferingPlanBitModel("Bit 1- Omneyas", 1, false, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit02 - Hakawy Stretch and Get", 2, false, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit03 - Hakawy Stretch and Get", 3, false, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit04 - Inactivity 1hr Promo", 4, false, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit05 - New BB International - Easy EOCN", 5, true, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit06 -  On-Going SMS - CVM 5 Days Bundle", 6, true, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit07 - Inactivity 1hr Promo", 7, true, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit08 - Inactivity 1hr Promo", 8, true, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit09 - Easy 19pt", 9, false, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit10 - Easy A7la Kalam - Friends SMS Add-on", 10, false, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit11", 11, false, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit12 - Databundles", 12, false, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit13 - Easy Hakawy", 13, true, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit14 - International 1.99LE Promo", 14, true, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit15 - International 1.99LE Promo", 15, true, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit16", 16, true, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit17 - Business Time", 17, false, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit18 - Business Time", 18, false, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit19 - Databundles", 19, false, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit20 - Chinese Handset Talk2 Get5 Promo", 20, false, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit21 - Data Bundles", 21, false, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit22 - Databundles", 22, false, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit23 - Prepaid Add-on", 23, false, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit24 -  October 500 SMS - CVM 5 SMS", 24, false, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit25 - CVM 2 SMS - CVM 5 SMS - CVM 5 Days Bundle", 25, true, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit26 - Weekend 4hrs Promo", 26, false, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit27 -  Prepaid Add-on 6PM-9PM", 27, false, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit28 -  Prepaid Add-on 9PM-12AM", 28, false, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit29 - Sallefny Shokran - Control Plus Deposit", 29, false, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit30 - Sallefny Shokran - Easy 14pt", 30, false, true));
        bitModels.add(new ServiceOfferingPlanBitModel("Bit31 - End of Call Notification", 31, false, true));

        String xmlRequest = "<?xml version=\"1.0\"?>\n" +
                "<methodCall>\n" +
                "    <methodName>UpdateSubscriberSegmentation</methodName>\n" +
                "    <params>\n" +
                "        <param>\n" +
                "            <value>\n" +
                "                <struct>\n" +
                "                    <member>\n" +
                "                        <name>originOperatorID</name>\n" +
                "                        <value>\n" +
                "                            <string>CCAT$originOperatorID$</string>\n" +
                "                        </value>\n" +
                "                    </member>\n" +
                "                    <member>\n" +
                "                        <name>originNodeType</name>\n" +
                "                        <value>\n" +
                "                            <string>CCAT</string>\n" +
                "                        </value>\n" +
                "                    </member>\n" +
                "                    <member>\n" +
                "                        <name>originHostName</name>\n" +
                "                        <value>\n" +
                "                            <string>CCAT$originOperatorID$</string>\n" +
                "                        </value>\n" +
                "                    </member>\n" +
                "                    <member>\n" +
                "                        <name>originTransactionID</name>\n" +
                "                        <value>\n" +
                "                            <string>$originTransactionID$</string>\n" +
                "                        </value>\n" +
                "                    </member>\n" +
                "                    <member>\n" +
                "                        <name>originTimeStamp</name>\n" +
                "                        <value>\n" +
                "                            <dateTime.iso8601>$originTimeStamp$</dateTime.iso8601>\n" +
                "                        </value>\n" +
                "                    </member>\n" +
                "                    <member>\n" +
                "                        <name>subscriberNumber</name>\n" +
                "                        <value>\n" +
                "                            <string>$subscriberNumber$</string>\n" +
                "                        </value>\n" +
                "                    </member>\n" +
                "                    $ServiceOffering$\n" +
                "                </struct>\n" +
                "            </value>\n" +
                "        </param>\n" +
                "    </params>\n" +
                "</methodCall>";


        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, username.toLowerCase());
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1");
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, "ty");
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME, "call");
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, msisdn);

        StringBuilder serviceOfferingsListXML = new StringBuilder();
        for (int i = 0; i < bitModels.size(); i++) {
            Integer serviceOfferingID = bitModels.get(i).getBitPosition();
            String serviceOfferingActiveFlag = (bitModels.get(i).getIsEnabled() ? "1" : "0");
            String serviceOfferingIDXML = AIRDefines.AIR_TAGS.TAG_MEMBER_I4;
            serviceOfferingIDXML = serviceOfferingIDXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.serviceOfferingID);
            serviceOfferingIDXML = serviceOfferingIDXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, serviceOfferingID.toString());
            String serviceOfferingActiveFlagXML = AIRDefines.AIR_TAGS.TAG_MEMBER_BOOLEAN;
            serviceOfferingActiveFlagXML = serviceOfferingActiveFlagXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.serviceOfferingActiveFlag);
            serviceOfferingActiveFlagXML = serviceOfferingActiveFlagXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, serviceOfferingActiveFlag);
            String serviceOfferingXML = AIRDefines.AIR_TAGS.TAG_STRUCT_2MEMBERS;
            serviceOfferingXML = serviceOfferingXML.replace("$MEMBER_1$", serviceOfferingIDXML);
            serviceOfferingXML = serviceOfferingXML.replace("$MEMBER_2$", serviceOfferingActiveFlagXML);
            serviceOfferingsListXML.append(serviceOfferingXML);
        }
        String serviceOfferingXML = "";
        if (Objects.nonNull(serviceOfferingsListXML) && !serviceOfferingsListXML.toString().equals("")) {
            serviceOfferingXML += AIRDefines.AIR_TAGS.TAG_ARRAY_DATA;
            serviceOfferingXML = serviceOfferingXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.serviceOfferings);
            serviceOfferingXML = serviceOfferingXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, serviceOfferingsListXML.toString());
        }
        xmlRequest = xmlRequest.replace(AIRDefines.UPDATE_SERVICE_OFFERING.SERVICE_OFFERING, serviceOfferingXML);
        System.out.println(xmlRequest);
    }
}
