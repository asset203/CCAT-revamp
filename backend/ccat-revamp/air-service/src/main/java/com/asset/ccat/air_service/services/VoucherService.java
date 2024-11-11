package com.asset.ccat.air_service.services;

import com.asset.ccat.air_service.cache.AIRRequestsCache;
import com.asset.ccat.air_service.configurations.Properties;
import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.exceptions.AIRVoucherException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.mappers.GetVoucherDetailsMapper;
import com.asset.ccat.air_service.models.VoucherModel;
import com.asset.ccat.air_service.models.requests.customer_care.voucher.CheckVoucherNumberRequest;
import com.asset.ccat.air_service.models.requests.customer_care.voucher.GetVoucherDetailsRequest;
import com.asset.ccat.air_service.models.requests.customer_care.voucher.UpdateVoucherStateRequest;
import com.asset.ccat.air_service.models.requests.customer_care.voucher.VoucherBasedRefillRequest;
import com.asset.ccat.air_service.models.responses.customer_care.voucher.CheckVoucherNumberResponse;
import com.asset.ccat.air_service.models.responses.customer_care.voucher.GetVoucherDetailsResponse;
import com.asset.ccat.air_service.models.shared.VoucherDigitModel;
import com.asset.ccat.air_service.parsers.AIRParser;
import com.asset.ccat.air_service.proxy.AIRProxy;
import com.asset.ccat.air_service.utils.AIRUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

@Component
public class VoucherService {

    @Autowired
    AIRRequestsCache aIRRequestsCache;
    @Autowired
    AIRProxy aIRProxy;
    @Autowired
    AIRUtils aIRUtils;
    @Autowired
    AIRParser aIRParser;
    @Autowired
    Properties properties;
    @Autowired
    GetVoucherDetailsMapper getVoucherDetailsMapper;

    public GetVoucherDetailsResponse getVoucherDetails(GetVoucherDetailsRequest request) throws AIRException, AIRServiceException, AIRVoucherException {
        CCATLogger.DEBUG_LOGGER.info("Start serving get VoucherDetails request");
        try {
            //build request
            CCATLogger.DEBUG_LOGGER.debug("Building get-VoucherDetails request for air request");
            String airRequest = buildGetVoucherDetailsRequest(request);
            CCATLogger.DEBUG_LOGGER.debug("Getting-VoucherDetails request for  air request is [" + airRequest + "]");
            //call air
            CCATLogger.DEBUG_LOGGER.debug("Calling airProxy - sendVCIPRequest()");
            long t1 = System.currentTimeMillis();
            String airResponse = aIRProxy.sendVoucherRequest(airRequest, request.getServerId(), request.getVoucherSerialNumber().length());
            long t2 = System.currentTimeMillis();
            CCATLogger.DEBUG_LOGGER.debug("Getting-VoucherDetails air response is [" + airResponse + "]");
            //parsing air response
            CCATLogger.DEBUG_LOGGER.debug("Parsing air response");
            HashMap responseMap = aIRParser.parse(airResponse);
            //validating response code
            CCATLogger.DEBUG_LOGGER.debug("Validating air response code");
            aIRUtils.validateAIRResponse(responseMap, "getVoucherDetails", t2 - t1, request.getUsername());
            String responseCode = (String) responseMap.get(AIRDefines.responseCode);
            aIRUtils.validateVCIPResponseCodes(responseCode);
            VoucherModel voucherModel = getVoucherDetailsMapper.map(request.getVoucherSerialNumber(), responseMap);
            CCATLogger.DEBUG_LOGGER.info("Finished serving get-VoucherDetails request successfully");

            return new GetVoucherDetailsResponse(voucherModel);
        } catch (AIRServiceException | AIRException | AIRVoucherException ex) {
            throw ex;
        } catch (SAXException | IOException ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to parse air response | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Failed to parse air response", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Unknown error in getVoucherDetails() | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Unknown error in getVoucherDetails()", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    public void updateVoucherState(UpdateVoucherStateRequest request) throws AIRServiceException, AIRException, AIRVoucherException {
        CCATLogger.DEBUG_LOGGER.info("Start serving update voucher state request");
        try {
            //build request
            CCATLogger.DEBUG_LOGGER.debug("Building Update voucher state air request");
            String airRequest = buildUpdateVoucherStateRequest(request);
            CCATLogger.DEBUG_LOGGER.debug("Update voucher state request air request is [" + airRequest + "]");
            //call air
            CCATLogger.DEBUG_LOGGER.debug("Calling airProxy - sendAirRequest()");
            long t1 = System.currentTimeMillis();
            String airResponse = aIRProxy.sendVoucherRequest(airRequest, request.getServerId(), request.getVoucherSerialNumber().length());
            long t2 = System.currentTimeMillis();
            //parsing air response
            CCATLogger.DEBUG_LOGGER.debug("Parsing air response");
            HashMap responseMap = aIRParser.parse(airResponse);
            //validating response code
            CCATLogger.DEBUG_LOGGER.debug("Validating air response code");
            aIRUtils.validateAIRResponse(responseMap, "updateVoucherState", t2 - t1, request.getUsername());
            String responseCode = (String) responseMap.get(AIRDefines.responseCode);
            aIRUtils.validateVCIPResponseCodes(responseCode);
        } catch (AIRServiceException | AIRException | AIRVoucherException ex) {
            throw ex;
        } catch (SAXException | IOException ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to parse air response | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Failed to parse air response", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Unknown error in updateVoucherState() | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Unknown error in updateVoucherState()", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }


    public void submitVoucherBasedRefill(VoucherBasedRefillRequest request) throws AIRServiceException, AIRException {
        try {
            //build request
            CCATLogger.DEBUG_LOGGER.debug("Building submit voucher based refill request air request");
            String airRequest = buildSubmitVoucherBasedRefill(request);
            CCATLogger.DEBUG_LOGGER.debug("Submit voucher based refill request air request is [" + airRequest + "]");
            //call air
            CCATLogger.DEBUG_LOGGER.debug("Calling airProxy - sendAirRequest()");
            long t1 = System.currentTimeMillis();
            String airResponse = aIRProxy.sendAIRRequest(airRequest);
            long t2 = System.currentTimeMillis();
            //parsing air response
            CCATLogger.DEBUG_LOGGER.debug("Parsing air response");
            HashMap responseMap = aIRParser.parse(airResponse);
            //validating response code
            CCATLogger.DEBUG_LOGGER.debug("Validating air response code");
            aIRUtils.validateAIRResponse(responseMap, "Submit voucher based refill", t2 - t1, request.getUsername());
            String responseCode = (String) responseMap.get(AIRDefines.responseCode);
            aIRUtils.validateUCIPResponseCodes(responseCode);
        } catch (AIRServiceException | AIRException ex) {
            throw ex;
        } catch (SAXException | IOException ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to parse air response | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Failed to parse air response", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Unknown error in submitVoucherBasedRefill() | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Unknown error in submitVoucherBasedRefill()", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    private String buildGetVoucherDetailsRequest(GetVoucherDetailsRequest request) {
        String getVoucherDetailsXmlRequest = aIRRequestsCache.getAirRequestsCache()
                .get(AIRDefines.AIR_COMMAND_KEY.GET_VOUCHER_DETAILS);
        getVoucherDetailsXmlRequest = getVoucherDetailsXmlRequest
                .replace(AIRDefines.VOUCHER_PLACEHOLDER.VOUCHER_SERIAL_NUMBER, String.valueOf(request.getVoucherSerialNumber()));
        getVoucherDetailsXmlRequest = getVoucherDetailsXmlRequest
                .replace(AIRDefines.VOUCHER_PLACEHOLDER.NETWORK_OPERATOR_ID, request.getUsername().toLowerCase());

        return getVoucherDetailsXmlRequest;
    }

    private String buildUpdateVoucherStateRequest(UpdateVoucherStateRequest request) {
        String updateVoucherStateXmlRequest = aIRRequestsCache.getAirRequestsCache()
                .get(AIRDefines.AIR_COMMAND_KEY.UPDATE_VOUCHER_STATE);
        updateVoucherStateXmlRequest = updateVoucherStateXmlRequest
                .replace(AIRDefines.VOUCHER_PLACEHOLDER.VOUCHER_SERIAL_NUMBER, request.getVoucherSerialNumber());
        updateVoucherStateXmlRequest = updateVoucherStateXmlRequest
                .replace(AIRDefines.VOUCHER_PLACEHOLDER.NETWORK_OPERATOR_ID, request.getUsername().toLowerCase());
        updateVoucherStateXmlRequest = updateVoucherStateXmlRequest
                .replace(AIRDefines.VOUCHER_PLACEHOLDER.NEW_STATE, String.valueOf(request.getNewState()));
        updateVoucherStateXmlRequest = updateVoucherStateXmlRequest
                .replace(AIRDefines.VOUCHER_PLACEHOLDER.CURRENT_STATE, String.valueOf(request.getCurrentState()));

        return updateVoucherStateXmlRequest;
    }

    private String buildSubmitVoucherBasedRefill(VoucherBasedRefillRequest request) {
        String voucherBasedRefillXmlRequest = aIRRequestsCache.getAirRequestsCache()
                .get(AIRDefines.AIR_COMMAND_KEY.VOUCHER_BASED_REFILL);
        // set basic request fields
        voucherBasedRefillXmlRequest = voucherBasedRefillXmlRequest
                .replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, request.getUsername().toLowerCase());
        voucherBasedRefillXmlRequest = voucherBasedRefillXmlRequest
                .replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1");
        voucherBasedRefillXmlRequest = voucherBasedRefillXmlRequest
                .replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, aIRUtils.getCurrentFormattedDate());
        voucherBasedRefillXmlRequest = voucherBasedRefillXmlRequest
                .replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, properties.getOriginNodeType());
        voucherBasedRefillXmlRequest = voucherBasedRefillXmlRequest
                .replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME, properties.getOriginHostName());
        voucherBasedRefillXmlRequest = voucherBasedRefillXmlRequest
                .replace(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, request.getMsisdn());
        voucherBasedRefillXmlRequest = voucherBasedRefillXmlRequest
                .replace(AIRDefines.VOUCHER_BASED_REFILL_PLACEHOLDER.VOUCHER_ACTIVATION_CODE, request.getVoucherNumber());

        String externalDateXml = "";
        if (Objects.nonNull(request.getIsMaredCard()) && request.getIsMaredCard()) {
            externalDateXml = AIRDefines.AIR_TAGS.TAG_MEMBER_STRING;
            externalDateXml = externalDateXml.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.EXTERNAL_DATA2);
            externalDateXml = externalDateXml.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, request.getSelectedMaredCard());
        }
        voucherBasedRefillXmlRequest = voucherBasedRefillXmlRequest.replace(AIRDefines.VOUCHER_BASED_REFILL_PLACEHOLDER.EXTERNAL_DATA_TO_LIST, externalDateXml);

        return voucherBasedRefillXmlRequest;
    }

    public CheckVoucherNumberResponse checkVoucherNumber(CheckVoucherNumberRequest request) throws AIRServiceException, AIRException, AIRVoucherException {

        GetVoucherDetailsRequest getVoucherDetailsRequest = new GetVoucherDetailsRequest();
        getVoucherDetailsRequest.setMsisdn(request.getMsisdn());
        getVoucherDetailsRequest.setUsername(request.getUsername());
        getVoucherDetailsRequest.setVoucherSerialNumber(request.getVoucherSerialNumber());
        getVoucherDetailsRequest.setServerId(request.getServerId());
        VoucherModel voucher = getVoucherDetails(getVoucherDetailsRequest).getVoucher();
        if (Objects.nonNull(voucher.getActivationCode()) && !voucher.getActivationCode().isBlank()) {
            CCATLogger.DEBUG_LOGGER.debug("Start validating voucher number");
            ArrayList<VoucherDigitModel> airVoucherNumber = new ArrayList<>();
            char[] airVoucherNumberDigits = voucher.getActivationCode().toCharArray();
            boolean isCorrect = true;

            // skip validating digits of index greater than length of retrieved air voucher number
            // validation is considered failed only if entered digit is not null and doesn't match air voucher digit
            for (int i = 0; i < airVoucherNumberDigits.length; i++) {
                String enteredDigit = request.getVoucherNumber().get(i);
                String airDigit = String.valueOf(airVoucherNumberDigits[i]);
                if (Objects.isNull(enteredDigit) || enteredDigit.isBlank()) {
                    VoucherDigitModel airDigitModel = new VoucherDigitModel(airDigit, false);
                    airVoucherNumber.add(airDigitModel);
                } else if (!enteredDigit.trim().equals(airDigit)) {
                    VoucherDigitModel airDigitModel = new VoucherDigitModel(airDigit, false);
                    airVoucherNumber.add(airDigitModel);
                    isCorrect = false;
                    break;
                } else {
                    VoucherDigitModel airDigitModel = new VoucherDigitModel(airDigit, true);
                    airVoucherNumber.add(airDigitModel);
                }
            }
            if ((Objects.isNull(request.getSkipValidationFlag()) || !request.getSkipValidationFlag()) && !isCorrect) {
                CCATLogger.DEBUG_LOGGER.debug("Skip Validation flag is true and air voucher number is not correct");
                throw new AIRServiceException(ErrorCodes.ERROR.OS_VOUCHER_NUMBER_FAILURE);
            }
            CCATLogger.DEBUG_LOGGER.debug("Finished validating voucher number successfully");

            return new CheckVoucherNumberResponse(airVoucherNumber, voucher.getActivationCode());
        } else {
            CCATLogger.DEBUG_LOGGER.debug("Failed to retrieve air activation code");
            throw new AIRServiceException(ErrorCodes.ERROR.OS_SERIAL_NUMBER_FAILURE);
        }
    }
}
