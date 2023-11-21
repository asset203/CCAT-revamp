package com.asset.ccat.ci_service.services;

import com.asset.ccat.ci_service.configurations.Properties;
import com.asset.ccat.ci_service.defines.CIDefines;
import com.asset.ccat.ci_service.exceptions.CIException;
import com.asset.ccat.ci_service.exceptions.CIServiceException;
import com.asset.ccat.ci_service.logger.CCATLogger;
import com.asset.ccat.ci_service.models.requests.flex_share.GetFlexShareEligibilityRequest;
import com.asset.ccat.ci_service.models.responses.flex_share.GetFlexShareEligibilityResponse;
import com.asset.ccat.ci_service.models.shared.FlexShareEligibilityModel;
import com.asset.ccat.ci_service.parser.CIParser;
import com.asset.ccat.ci_service.proxy.ChargingInterfaceProxy;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FlexShareService {
    private final Properties properties;

    private final ChargingInterfaceProxy proxy;
    private final CIParser ciParser;

    public FlexShareService(Properties properties, ChargingInterfaceProxy proxy, CIParser ciParser) {
        this.properties = properties;
        this.proxy = proxy;
        this.ciParser = ciParser;
    }

    public GetFlexShareEligibilityResponse getFlexShareEligibility(GetFlexShareEligibilityRequest request) throws CIServiceException, CIException {
        CCATLogger.DEBUG_LOGGER.debug("FlexShareService -> getFlexShareEligibility() : Started Successfully");
        GetFlexShareEligibilityResponse response = new GetFlexShareEligibilityResponse();
        String flexShareURI = properties.getFlexShareUrl();
        String flexShareChannel = properties.getChannelIdFlex();
        flexShareURI = flexShareURI.replace(CIDefines.FLEX_SHARE.URL_REPLACEMENTS.SENDER_MSISDN, request.getMsisdn())
                .replace(CIDefines.FLEX_SHARE.URL_REPLACEMENTS.RECEIVER_MSISDN, request.getReceiverMsisdn())
                .replace(CIDefines.FLEX_SHARE.URL_REPLACEMENTS.TRANSFER_AMOUNT, request.getFlexAmount() + "")
                .replace(CIDefines.FLEX_SHARE.URL_REPLACEMENTS.CHANNEL_ID, flexShareChannel);
        CCATLogger.DEBUG_LOGGER.debug("FlexShareService -> getFlexShareEligibility() URL : " + flexShareURI);

        String result = proxy.chargingRequest(flexShareURI, "getFlexShareEligibility");

        Map<String, String> resultMap = ciParser.flexShareEligibility(result);
        String responseCode = resultMap.get(CIDefines.FLEX_SHARE.ELIGIBILITY_RESULT_MAP_KEYS.REQUEST_STATUS);
        if (responseCode.equals(properties.getFsSuccessCode())) {
            FlexShareEligibilityModel fsModel = new FlexShareEligibilityModel();
            fsModel.setFees(resultMap.get(CIDefines.FLEX_SHARE.ELIGIBILITY_RESULT_MAP_KEYS.FEES));
            fsModel.setMaxEligAmountFees(resultMap.get(CIDefines.FLEX_SHARE.ELIGIBILITY_RESULT_MAP_KEYS.MAX_ELIG_AMOUNT_FEES));
            fsModel.setTransferAmount(resultMap.get(CIDefines.FLEX_SHARE.ELIGIBILITY_RESULT_MAP_KEYS.TRANSFER_AMOUNT));
            fsModel.setMaxEligAmount(resultMap.get(CIDefines.FLEX_SHARE.ELIGIBILITY_RESULT_MAP_KEYS.MAX_ELIG_AMOUNT));
            fsModel.setExpiryDate(resultMap.get(CIDefines.FLEX_SHARE.ELIGIBILITY_RESULT_MAP_KEYS.EXPIRY_DATE));
            response.setFlexShareEligibilityModel(fsModel);
            CCATLogger.DEBUG_LOGGER.debug("FlexShareService -> getFlexShareEligibility() Ended Successfully");
            return response;
        } else {
            CCATLogger.DEBUG_LOGGER.info("Error while calling CI On URL " + flexShareURI);
            CCATLogger.ERROR_LOGGER.error("Error while calling CI On URL " + flexShareURI);
            throw new CIException(Integer.parseInt(responseCode + "1"));
        }
    }
}
