package com.asset.ccat.ci_service.services;

import com.asset.ccat.ci_service.configurations.Properties;
import com.asset.ccat.ci_service.defines.CIDefines;
import com.asset.ccat.ci_service.exceptions.CIException;
import com.asset.ccat.ci_service.exceptions.CIServiceException;
import com.asset.ccat.ci_service.logger.CCATLogger;
import com.asset.ccat.ci_service.models.dtos.CICodesRenewalsValue;
import com.asset.ccat.ci_service.models.requests.UpdateLimitRequest;
import com.asset.ccat.ci_service.models.requests.prepaidVBP.PrepaidCheckSubscriptionRequest;
import com.asset.ccat.ci_service.models.requests.prepaidVBP.PrepaidVBPSubscriptionRequest;
import com.asset.ccat.ci_service.models.requests.prepaidVBP.PrepaidVBPUnsubscriptionRequest;
import com.asset.ccat.ci_service.models.responses.PrepaidVBPCheckResponse;
import com.asset.ccat.ci_service.parser.CIParser;
import com.asset.ccat.ci_service.proxy.ChargingInterfaceProxy;
import com.asset.ccat.ci_service.validators.PrepaidVBPValidator;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PrepaidVBPService {

    private final ChargingInterfaceProxy prepaidVBPProxy;
    private final Properties properties;
    private final CIParser ciParser;
    private final PrepaidVBPValidator prepaidVBPValidator;
    private final LookupService lookupService;
    private final UserLimitsService userLimitsService;

    public PrepaidVBPService(ChargingInterfaceProxy prepaidVBPProxy,
                             Properties properties,
                             CIParser ciParser,
                             PrepaidVBPValidator prepaidVBPValidator,
                             LookupService lookupService,
                             UserLimitsService userLimitsService) {
        this.prepaidVBPProxy = prepaidVBPProxy;
        this.properties = properties;
        this.ciParser = ciParser;
        this.prepaidVBPValidator = prepaidVBPValidator;
        this.lookupService = lookupService;
        this.userLimitsService = userLimitsService;
    }

    public PrepaidVBPCheckResponse prepaidVBPCheckSubscription(PrepaidCheckSubscriptionRequest prepaidCheckSubscriptionRequest) throws CIServiceException, CIException {
        CCATLogger.DEBUG_LOGGER.info("Start prepaid VBP Check Subscription for [" + prepaidCheckSubscriptionRequest.getMsisdn() + "]");
        String urlRequest = properties.getPvbpCheckSubscriptionUrl();
        urlRequest = urlRequest.replace(CIDefines.PREPAIDVBP.CHECK_SUBSCRIPTION_PARAMETERS.PVBP_MSISDN, prepaidCheckSubscriptionRequest.getMsisdn());
        CCATLogger.DEBUG_LOGGER.debug("CI PrepaidVBP Service Class Check Subscription urlRequest is " + urlRequest);
        String response = prepaidVBPProxy.chargingRequest(urlRequest,CIDefines.PREPAIDVBP.ACTIONS.CHECK);
        CCATLogger.DEBUG_LOGGER.debug("CI repaidVBP Service Class Check Subscription response is " + response);
        String responseCode = ciParser.parseCheckSubscriptionPVBPResponse(response);
        CCATLogger.DEBUG_LOGGER.debug("Parsing Ended successfully with responseCode[" + responseCode + "].");
        Boolean isSubscribed = prepaidVBPValidator.checkSubscriptionResponseCodeValidator(responseCode);
        return new PrepaidVBPCheckResponse(isSubscribed);
    }

    public String prepaidVBPUnsubscription(PrepaidVBPUnsubscriptionRequest prepaidVBPUnsubscriptionRequest) throws CIServiceException, CIException {
        CCATLogger.DEBUG_LOGGER.info("Start prepaid VBP Unsubscription for [" + prepaidVBPUnsubscriptionRequest.getMsisdn() + "]");
        String urlRequest = properties.getPvbpUnsubscribeUrl();
        urlRequest = urlRequest.replace(CIDefines.PREPAIDVBP.UNSUBSCRIPTION_PARAMETERS.PVBP_MSISDN, prepaidVBPUnsubscriptionRequest.getMsisdn());
        CCATLogger.DEBUG_LOGGER.debug("CI PrepaidVBP Service Class Unsubscription urlRequest is " + urlRequest);
        String response = prepaidVBPProxy.chargingRequest(urlRequest,CIDefines.PREPAIDVBP.ACTIONS.UNSUBSCRIPTION);
        CCATLogger.DEBUG_LOGGER.debug("CI repaidVBP Service Class Unsubscription response is " + response);
        String responseCode = ciParser.parseUnsubscriptionPVBPResponse(response);
        CCATLogger.DEBUG_LOGGER.debug("Parsing Ended successfully with responseCode[" + responseCode + "].");
        prepaidVBPValidator.unsubscribeResponseCodeValidator(responseCode);
        return responseCode;
    }

    public String prepaidVBPSubscription(PrepaidVBPSubscriptionRequest prepaidVBPSubscriptionRequest) throws CIServiceException, CIException {
        CCATLogger.DEBUG_LOGGER.info("Start prepaid VBP Subscription for [" + prepaidVBPSubscriptionRequest.getMsisdn() + "]");
        CICodesRenewalsValue ciCodesRenewalsValue = lookupService.retrievePrepaidVBPCodesRenewalsValues(prepaidVBPSubscriptionRequest.getTransactionCode());
        Integer numberOfRenewals = -1 ;
        if(Objects.nonNull(ciCodesRenewalsValue)){
            numberOfRenewals = ciCodesRenewalsValue.getRenewalsValue();
        }
        CCATLogger.DEBUG_LOGGER.info("Number of renewals retrieved successfully with value : "+numberOfRenewals);
        String urlRequest = properties.getPvbpSubscribeUrl();
        urlRequest = urlRequest.replace(CIDefines.PREPAIDVBP.SUBSCRIPTION_PARAMETERS.PVBP_MSISDN, prepaidVBPSubscriptionRequest.getMsisdn());
        urlRequest = urlRequest.replace(CIDefines.PREPAIDVBP.SUBSCRIPTION_PARAMETERS.PVBP_AMOUNT, prepaidVBPSubscriptionRequest.getTransactionAmount()+"");
        urlRequest = urlRequest.replace(CIDefines.PREPAIDVBP.SUBSCRIPTION_PARAMETERS.PVBP_ORIGIN_OPERATOR_ID, prepaidVBPSubscriptionRequest.getUsername());
        urlRequest = urlRequest.replace(CIDefines.PREPAIDVBP.SUBSCRIPTION_PARAMETERS.PVBP_EXTERNAL_DATA_1, prepaidVBPSubscriptionRequest.getTransactionType());
        urlRequest = urlRequest.replace(CIDefines.PREPAIDVBP.SUBSCRIPTION_PARAMETERS.PVBP_EXTERNAL_DATA_2, prepaidVBPSubscriptionRequest.getTransactionCode()+"");
        urlRequest = urlRequest.replace(CIDefines.PREPAIDVBP.SUBSCRIPTION_PARAMETERS.PVBP_MAX_NUM_OF_RENEWALS, String.valueOf(numberOfRenewals));
        CCATLogger.DEBUG_LOGGER.debug("CI PrepaidVBP Service Class Subscription urlRequest is " + urlRequest);
        String response = prepaidVBPProxy.chargingRequest(urlRequest,CIDefines.PREPAIDVBP.ACTIONS.SUBSCRIPTION);
        CCATLogger.DEBUG_LOGGER.debug("CI repaidVBP Service Class Subscription response is " + response);
        String responseCode = ciParser.parseSubscriptionPVBPResponse(response);
        CCATLogger.DEBUG_LOGGER.debug("Parsing Ended successfully with responseCode[" + responseCode + "].");
        prepaidVBPValidator.subscribeResponseCodeValidator(responseCode);
        CCATLogger.DEBUG_LOGGER.debug("Starting update user limits");
        UpdateLimitRequest updateLimitRequest = new UpdateLimitRequest(prepaidVBPSubscriptionRequest.getUserId(),
                1,
                prepaidVBPSubscriptionRequest.getTransactionAmount());
        updateLimitRequest.setToken(prepaidVBPSubscriptionRequest.getToken());
        updateLimitRequest.setRequestId(prepaidVBPSubscriptionRequest.getRequestId());
        updateLimitRequest.setSessionId(prepaidVBPSubscriptionRequest.getSessionId());
        updateLimitRequest.setUsername(prepaidVBPSubscriptionRequest.getUsername());
        userLimitsService.updateLimits(updateLimitRequest);
        CCATLogger.DEBUG_LOGGER.debug("Update user limits Finished Successfully");
        return responseCode;
    }
}
