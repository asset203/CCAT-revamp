/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.ci_service.parser;

import com.asset.ccat.ci_service.defines.CIDefines;
import com.asset.ccat.ci_service.defines.ErrorCodes;
import com.asset.ccat.ci_service.exceptions.CIServiceException;
import com.asset.ccat.ci_service.logger.CCATLogger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author wael.mohamed
 */
@Component
public class CIParser {

    public String getMigrationCode(String xmlResponse) throws CIServiceException {
        CCATLogger.DEBUG_LOGGER.debug("CIParser -> getMigrationCode() Started successfully.");
        String responseCode;
        if (xmlResponse != null && !xmlResponse.isEmpty() && !xmlResponse.isBlank()) {
            try {
                responseCode = xmlResponse.split(",")[1];
            } catch (ArrayIndexOutOfBoundsException ex) {
                CCATLogger.ERROR_LOGGER.error("Response is " + xmlResponse, ex);
                CCATLogger.DEBUG_LOGGER.debug("CIParser -> getMigrationCode() Ended with ArrayIndexOutOfBoundsException.");
                throw new CIServiceException(ErrorCodes.ERROR.INVALID_AIR_RESPONSE, "");
            }
        } else {
            CCATLogger.DEBUG_LOGGER.debug("CIParser -> getMigrationCode() Ended With Null value.");
            throw new CIServiceException(ErrorCodes.ERROR.AIR_RESPONSE_NULL);
        }
        CCATLogger.DEBUG_LOGGER.debug("CIParser -> getMigrationCode() Ended successfully.");
        return responseCode;
    }

    public String parseCheckSubscriptionPVBPResponse(String response) throws CIServiceException {
        CCATLogger.DEBUG_LOGGER.debug("CIParser -> parseCheckSubscriptionPVBPResponse() Started successfully.");
        String responseCode;
        if (Objects.nonNull(response) && !response.isEmpty() && !response.isBlank()) {
            try {
                String[] tmp1 = response.split(";");
                String[] tmp2 = tmp1[0].split(",");
                if (tmp2[1] != null && tmp2[1].equals(CIDefines.PREPAIDVBP.CHECK_SUBSCRIPTION_RESPONSES.SUCCESS_CODE)) {
                    String[] tmp3 = tmp1[1].split(",");
                    responseCode = tmp3[1];
                } else {
                    responseCode = tmp2[1];
                }

            } catch (Exception ex) {
                CCATLogger.ERROR_LOGGER.error("Response is " + response, ex);
                CCATLogger.DEBUG_LOGGER.debug("CIParser -> parseCheckSubscriptionPVBPResponse() Ended with INVALID CI RESPONSE.");
                throw new CIServiceException(ErrorCodes.ERROR.INVALID_CI_RESPONSE);
            }
        } else {
            CCATLogger.DEBUG_LOGGER.debug("CIParser -> parseCheckSubscriptionPVBPResponse() Ended With Null value.");
            throw new CIServiceException(ErrorCodes.ERROR.EMPTY_CI_RESPONSE);
        }
        CCATLogger.DEBUG_LOGGER.debug("CIParser -> parseCheckSubscriptionPVBPResponse()  Ended successfully.");
        return responseCode;
    }

    public String parseSubscriptionPVBPResponse(String response) throws CIServiceException {
        CCATLogger.DEBUG_LOGGER.debug("CIParser -> parseSubscriptionPVBPResponse() Started successfully.");
        String responseCode;
        if (Objects.nonNull(response) && !response.isEmpty() && !response.isBlank()) {
            try {
                responseCode = response.split(";")[0].split(",")[1];
            } catch (Exception ex) {
                CCATLogger.ERROR_LOGGER.error("Response is " + response, ex);
                CCATLogger.DEBUG_LOGGER.debug("CIParser -> parseSubscriptionPVBPResponse() Ended with INVALID CI RESPONSE.");
                throw new CIServiceException(ErrorCodes.ERROR.INVALID_CI_RESPONSE);
            }
        } else {
            CCATLogger.DEBUG_LOGGER.debug("CIParser -> parseSubscriptionPVBPResponse() Ended With Null value.");
            throw new CIServiceException(ErrorCodes.ERROR.EMPTY_CI_RESPONSE);
        }
        CCATLogger.DEBUG_LOGGER.debug("CIParser -> parseSubscriptionPVBPResponse()  Ended successfully.");
        return responseCode;
    }

    public String parseUnsubscriptionPVBPResponse(String response) throws CIServiceException {
        CCATLogger.DEBUG_LOGGER.debug("CIParser -> parseUnsubscriptionPVBPResponse() Started successfully.");
        String responseCode;
        if (Objects.nonNull(response) && !response.isEmpty() && !response.isBlank()) {
            try {
                responseCode = response.split(";")[0].split(",")[1];
            } catch (Exception ex) {
                CCATLogger.ERROR_LOGGER.error("Response is " + response, ex);
                CCATLogger.DEBUG_LOGGER.debug("CIParser -> parseUnsubscriptionPVBPResponse() Ended with Invalid CI Response.");
                throw new CIServiceException(ErrorCodes.ERROR.INVALID_CI_RESPONSE);
            }
        } else {
            CCATLogger.DEBUG_LOGGER.debug("CIParser -> parseUnsubscriptionPVBPResponse() Ended With Null value.");
            throw new CIServiceException(ErrorCodes.ERROR.EMPTY_CI_RESPONSE);
        }
        CCATLogger.DEBUG_LOGGER.debug("CIParser -> parseUnsubscriptionPVBPResponse() Ended successfully.");
        return responseCode;
    }

    public Map<String, String> flexShareEligibility(String ciResponse) {
        CCATLogger.DEBUG_LOGGER.debug("CIParser -> flexShareEligibility() Started successfully.");
        Map<String, String> map = Pattern.compile("\\s*&\\s*")
                .splitAsStream(ciResponse.trim())
                .map(s -> s.split("=", 2))
                .collect(Collectors.toMap(a -> a[0], a -> a.length > 1 ? a[1] : ""));
        CCATLogger.DEBUG_LOGGER.debug("CIParser -> flexShareEligibility() Response : " + map);
        CCATLogger.DEBUG_LOGGER.debug("CIParser -> flexShareEligibility() Ended successfully.");
        return map;
    }

    public String parseSuperFlexActivateAddonResp(String response) {
        String res = "";
        int commaIndex = response.indexOf(",");
        int semicolonIndex = response.indexOf(";");
        res = response.substring(commaIndex + 1, semicolonIndex);
        if (res.contains(",")) {
            int commaIndex2 = res.indexOf(",");
            res = res.substring(0, commaIndex2);
        }
        return res;
    }

    public String parseSuperFlexDeActivateAddonResp(String response) {
        String res = "";
        int commaIndex = response.indexOf(",");
        int semicolonIndex = response.indexOf(";");
        res = response.substring(commaIndex + 1, semicolonIndex);
        if (res.contains(",")) {
            int commaIndex2 = res.indexOf(",");
            res = res.substring(0, commaIndex2);
        }
        return res;
    }

    public String parseSuperFlexActivateThresholdResp(String response) {
        String res = "";
        int commaIndex = response.indexOf(",");
        int semicolonIndex = response.indexOf(";");
        res = response.substring(commaIndex + 1, semicolonIndex);
        if (res.contains(",")) {
            int commaIndex2 = res.indexOf(",");
            res = res.substring(0, commaIndex2);
        }
        return res;
    }

    public String parseSuperFlexDeActivateThresholdResp(String response) {
        String res = "";
        int commaIndex = response.indexOf(",");
        int semicolonIndex = response.indexOf(";");
        res = response.substring(commaIndex + 1, semicolonIndex);
        if (res.contains(",")) {
            int commaIndex2 = res.indexOf(",");
            res = res.substring(0, commaIndex2);
        }
        return res;
    }

    public String parseSuperFlexThresholdStopResp(String response) {
        String res = "";
        int commaIndex = response.indexOf(",");
        int semicolonIndex = response.indexOf(";");
        res = response.substring(commaIndex + 1, semicolonIndex);
        if (res.contains(",")) {
            int commaIndex2 = res.indexOf(",");
            res = res.substring(0, commaIndex2);
        }
        return res;
    }

    public String parseSuperFlexDeactivateStopMIResp(String response) {
        String res = "";
        int commaIndex = response.indexOf(",");
        int semicolonIndex = response.indexOf(";");
        res = response.substring(commaIndex + 1, semicolonIndex);
        if (res.contains(",")) {
            int commaIndex2 = res.indexOf(",");
            res = res.substring(0, commaIndex2);
        }
        return res;
    }

}
