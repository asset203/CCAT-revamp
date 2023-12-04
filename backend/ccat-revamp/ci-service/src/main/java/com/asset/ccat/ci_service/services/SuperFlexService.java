package com.asset.ccat.ci_service.services;

import com.asset.ccat.ci_service.configurations.Properties;
import com.asset.ccat.ci_service.defines.CIDefines;
import com.asset.ccat.ci_service.defines.ErrorCodes;
import com.asset.ccat.ci_service.exceptions.CIException;
import com.asset.ccat.ci_service.exceptions.CIServiceException;
import com.asset.ccat.ci_service.logger.CCATLogger;
import com.asset.ccat.ci_service.models.requests.SubscriberRequest;
import com.asset.ccat.ci_service.models.requests.super_flex.ActivateAddonRequest;
import com.asset.ccat.ci_service.models.requests.super_flex.ActivateThresholdRequest;
import com.asset.ccat.ci_service.models.responses.GetMIThresholdResponse;
import com.asset.ccat.ci_service.models.responses.GetPrepaidProfileResponse;
import com.asset.ccat.ci_service.models.shared.SuperFlexLookupModel;
import com.asset.ccat.ci_service.parser.CIParser;
import com.asset.ccat.ci_service.proxy.ChargingInterfaceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class SuperFlexService {

    @Autowired
    private GetPrepaidProfileService prepaidProfileService;

    @Autowired
    private LookupService lookupService;

    @Autowired
    private ChargingInterfaceProxy chargingInterfaceProxy;

    @Autowired
    private CIParser ciParser;

    @Autowired
    private Properties properties;

    public void activateAddon(ActivateAddonRequest request) throws CIServiceException, CIException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexService - activateAddon()");
        CCATLogger.DEBUG_LOGGER.info("Start serving activateAddon request for subscriber [" + request.getMsisdn() + "] ");

        CCATLogger.DEBUG_LOGGER.debug("Start preparing request url");
        String url = properties.getSuperFlexAddonOptin();
        if (url == null || url.isBlank()) {
            CCATLogger.DEBUG_LOGGER.info("Missing configuration [super flex addon optin url]");
            throw new CIServiceException(ErrorCodes.ERROR.MISSING_CONFIGURATION, "super flex addon optin url");
        }
        url = url.replace(CIDefines.SUPER_FLEX.SUPER_FLEX_MSISDN, request.getMsisdn());
        url = url.replace(CIDefines.SUPER_FLEX.SUPER_FLEX_PACKAGE_ID, request.getPackageId());
        CCATLogger.DEBUG_LOGGER.debug("Super flex addon optin url is [" + url + "]");

        CCATLogger.DEBUG_LOGGER.debug("Send super flex addon optin charging request");
        String response = chargingInterfaceProxy.chargingRequest(url, "SuperFlexService");
        CCATLogger.DEBUG_LOGGER.debug("Super flex addon optin charging response is [" + response + "]");

        CCATLogger.DEBUG_LOGGER.debug("Start parsing response");
        String statusCode = ciParser.parseSuperFlexActivateAddonResp(response);
        String result = "," + statusCode + ",";
        CCATLogger.DEBUG_LOGGER.debug("Super flex addon optin parsed response is [" + result + "]");

        CCATLogger.DEBUG_LOGGER.debug("Start validating response");
        String ciSuccessCodes = properties.getSuperFlexCiSuccessCodes();
        if (ciSuccessCodes != null && !ciSuccessCodes.isBlank()) {
            if (!ciSuccessCodes.contains(result)) {
                CCATLogger.DEBUG_LOGGER.info("Invalid CI response : [" + result + "]");
                statusCode = statusCode.replace("SF_", ""); // TODO Handle prefix in exception messages cache
                throw new CIException(Integer.valueOf("-" + statusCode + "2"));
            }
        } else {
            CCATLogger.DEBUG_LOGGER.info("Missing configuration [Super Flex CI Success codes]");
            throw new CIServiceException(ErrorCodes.ERROR.MISSING_CONFIGURATION, "Super Flex CI Success codes");
        }

        CCATLogger.DEBUG_LOGGER.info("Finished serving activateAddon request for subscriber [" + request.getMsisdn() + "] sucessfullly");
        CCATLogger.DEBUG_LOGGER.debug("Ended SuperFlexService - activateAddon()");
    }


    public void deactivateAddon(SubscriberRequest request) throws CIServiceException, CIException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexService - deactivateAddon()");
        CCATLogger.DEBUG_LOGGER.info("Start serving deactivateAddon request for subscriber [" + request.getMsisdn() + "] ");

        CCATLogger.DEBUG_LOGGER.debug("Start preparing request url");
        String url = properties.getSuperFlexAddonOptout();
        if (url == null || url.isBlank()) {
            CCATLogger.DEBUG_LOGGER.info("Missing configuration [super flex addon optout url]");
            throw new CIServiceException(ErrorCodes.ERROR.MISSING_CONFIGURATION, "super flex addon optout url");
        }
        url = url.replace(CIDefines.SUPER_FLEX.SUPER_FLEX_MSISDN, request.getMsisdn());
        CCATLogger.DEBUG_LOGGER.debug("Super flex addon optout url is [" + url + "]");

        CCATLogger.DEBUG_LOGGER.debug("Send super flex addon optout charging request");
        String response = chargingInterfaceProxy.chargingRequest(url, "SuperFlexService");
        CCATLogger.DEBUG_LOGGER.debug("Super flex addon optout charging response is [" + response + "]");

        CCATLogger.DEBUG_LOGGER.debug("Start parsing response");
        String statusCode = ciParser.parseSuperFlexDeActivateAddonResp(response);
        String result = "," + statusCode + ",";
        CCATLogger.DEBUG_LOGGER.debug("Super flex addon optout parsed response is [" + result + "]");

        CCATLogger.DEBUG_LOGGER.debug("Start validating response");
        String ciSuccessCodes = properties.getSuperFlexCiSuccessCodes();
        if (ciSuccessCodes != null && !ciSuccessCodes.isBlank()) {
            if (!ciSuccessCodes.contains(result)) {
                CCATLogger.DEBUG_LOGGER.info("Invalid CI response : [" + result + "]");
                statusCode = statusCode.replace("SF_", ""); // TODO Handle prefix in exception messages cache
                throw new CIException(Integer.valueOf("-" + statusCode + "2"));
            }
        } else {
            CCATLogger.DEBUG_LOGGER.info("Missing configuration [Super Flex CI Success codes]");
            throw new CIServiceException(ErrorCodes.ERROR.MISSING_CONFIGURATION, "Super Flex CI Success codes");
        }

        CCATLogger.DEBUG_LOGGER.info("Finished serving deactivateAddon request for subscriber [" + request.getMsisdn() + "] sucessfullly");
        CCATLogger.DEBUG_LOGGER.debug("Ended SuperFlexService - deactivateAddon()");
    }


    public GetMIThresholdResponse getMiThreshold(SubscriberRequest request) throws CIServiceException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexService - getMiThreshold()");
        CCATLogger.DEBUG_LOGGER.info("Start serving getMiThreshold request for subscriber [" + request.getMsisdn() + "] ");

        CCATLogger.DEBUG_LOGGER.debug("Start getting product list for subscriber");
        GetPrepaidProfileResponse prepaidProfileResponse = prepaidProfileService.getPrepaidProfile(request);
        List<GetPrepaidProfileResponse.Products.Product> retrievedProducts = prepaidProfileResponse.getProducts().getProduct();

        CCATLogger.DEBUG_LOGGER.debug("Start getting super flex threshold offers lookup");
        HashMap<Integer, SuperFlexLookupModel> offerslookup = lookupService.getSuperFlexThresholdOffers();

        String thresholdName = "";
        Integer thresholdAmount = 0;
        GetPrepaidProfileResponse.Products.Product target = null;
        List<GetPrepaidProfileResponse.Products.Product> productList = new ArrayList<>();

        if (offerslookup != null && !offerslookup.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("Start getting threshold amount of the subscriber product defined in lookup");
            target = retrievedProducts.stream()
                    .filter(product -> offerslookup.containsKey(product.getProductId()))
                    .findFirst()
                    .orElse(null);

            if (target != null) { // product found in lookup set threshold name & amount
                if (target.getQuotas() != null && !target.getQuotas().isEmpty()) {
                    thresholdAmount = Integer.valueOf((int) target.getQuotas().get(0).getMaxQuota());
                } else {
                    thresholdAmount = 0;
                }
                thresholdName = offerslookup.get(target.getProductId()).getName();
                productList.add(target);
            }
        }

        // if threshold name is still null take the found product quota name
        if ((thresholdName == null || thresholdName.isBlank()) && target != null) {
            thresholdName = target.getQuotas().get(0).getQuotaName();
        }

        // if still null set to inactive
        if (thresholdName == null || thresholdName.isBlank()) {
            thresholdName = "Inactive";
        }

        GetMIThresholdResponse response = new GetMIThresholdResponse(thresholdName, thresholdAmount, productList);
        CCATLogger.DEBUG_LOGGER.info("Finished serving getMiThreshold request for subscriber [" + request.getMsisdn() + "] sucessfullly");
        CCATLogger.DEBUG_LOGGER.debug("Ended SuperFlexService - getMiThreshold()");
        return response;
    }

    public void activateThreshold(ActivateThresholdRequest request) throws CIException, CIServiceException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexService - activateThreshold()");
        CCATLogger.DEBUG_LOGGER.info("Start serving activateThreshold request for subscriber [" + request.getMsisdn() + "] ");

        CCATLogger.DEBUG_LOGGER.debug("Start preparing request url");
        String url = properties.getSuperFlexThresholdOptin();
        if (url == null || url.isBlank()) {
            CCATLogger.DEBUG_LOGGER.info("Missing configuration [super flex threshold optin url]");
            throw new CIServiceException(ErrorCodes.ERROR.MISSING_CONFIGURATION, "super flex threshold optin url");
        }
        url = url.replace(CIDefines.SUPER_FLEX.SUPER_FLEX_MSISDN, request.getMsisdn());
        url = url.replace(CIDefines.SUPER_FLEX.SUPER_FLEX_THRESHOLD_AMOUNT, request.getThresholdAmount() + "");
        CCATLogger.DEBUG_LOGGER.debug("Super flex threshold optin url is [" + url + "]");

        CCATLogger.DEBUG_LOGGER.debug("Send super flex threshold optin charging request");
        String response = chargingInterfaceProxy.chargingRequest(url, "SuperFlexService");
        CCATLogger.DEBUG_LOGGER.debug("Super flex threshold optin charging response is [" + response + "]");

        CCATLogger.DEBUG_LOGGER.debug("Start parsing response");
        String statusCode = ciParser.parseSuperFlexActivateThresholdResp(response);
        String result = "," + statusCode + ",";
        CCATLogger.DEBUG_LOGGER.debug("Super flex threshold optin parsed response is [" + result + "]");

        CCATLogger.DEBUG_LOGGER.debug("Start validating response");
        String ciSuccessCodes = properties.getSuperFlexCiSuccessCodes();
        if (ciSuccessCodes != null && !ciSuccessCodes.isBlank()) {
            if (!ciSuccessCodes.contains(result)) {
                CCATLogger.DEBUG_LOGGER.info("Invalid CI response : [" + result + "]");
                statusCode = statusCode.replace("SF_", ""); // TODO Handle prefix in exception messages cache
                throw new CIException(Integer.valueOf("-" + statusCode + "2"));
            }
        } else {
            CCATLogger.DEBUG_LOGGER.info("Missing configuration [Super Flex CI Success codes]");
            throw new CIServiceException(ErrorCodes.ERROR.MISSING_CONFIGURATION, "Super Flex CI Success codes");
        }

        CCATLogger.DEBUG_LOGGER.info("Finished serving activateThreshold request for subscriber [" + request.getMsisdn() + "] sucessfullly");
        CCATLogger.DEBUG_LOGGER.debug("Ended SuperFlexService - activateThreshold()");

    }

    public void deactivateThreshold(SubscriberRequest request) throws CIServiceException, CIException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexService - deactivateThreshold()");
        CCATLogger.DEBUG_LOGGER.info("Start serving deactivateThreshold request for subscriber [" + request.getMsisdn() + "] ");

        CCATLogger.DEBUG_LOGGER.debug("Start preparing request url");
        String url = properties.getSuperFlexThresholdOptout();
        if (url == null || url.isBlank()) {
            CCATLogger.DEBUG_LOGGER.info("Missing configuration [super flex threshold optout url]");
            throw new CIServiceException(ErrorCodes.ERROR.MISSING_CONFIGURATION, "super flex threshold optout url");
        }
        url = url.replace(CIDefines.SUPER_FLEX.SUPER_FLEX_MSISDN, request.getMsisdn());
        CCATLogger.DEBUG_LOGGER.debug("Super flex threshold optout url is [" + url + "]");

        CCATLogger.DEBUG_LOGGER.debug("Send super flex threshold optout charging request");
        String response = chargingInterfaceProxy.chargingRequest(url, "SuperFlexService");
        CCATLogger.DEBUG_LOGGER.debug("Super flex threshold optout charging response is [" + response + "]");

        CCATLogger.DEBUG_LOGGER.debug("Start parsing response");
        String statusCode = ciParser.parseSuperFlexDeActivateThresholdResp(response);
        String result = "," + statusCode + ",";

        CCATLogger.DEBUG_LOGGER.debug("Super flex threshold optout parsed response is [" + result + "]");

        CCATLogger.DEBUG_LOGGER.debug("Start validating response");
        String ciSuccessCodes = properties.getSuperFlexCiSuccessCodes();
        if (ciSuccessCodes != null && !ciSuccessCodes.isBlank()) {
            if (!ciSuccessCodes.contains(result)) {
                CCATLogger.DEBUG_LOGGER.info("Invalid CI response : [" + result + "]");
                statusCode = statusCode.replace("SF_", ""); // TODO Handle prefix in exception messages cache
                throw new CIException(Integer.valueOf("-" + statusCode + "2"));
            }
        } else {
            CCATLogger.DEBUG_LOGGER.info("Missing configuration [Super Flex CI Success codes]");
            throw new CIServiceException(ErrorCodes.ERROR.MISSING_CONFIGURATION, "Super Flex CI Success codes");
        }

        CCATLogger.DEBUG_LOGGER.info("Finished serving deactivateThreshold request for subscriber [" + request.getMsisdn() + "] sucessfullly");
        CCATLogger.DEBUG_LOGGER.debug("Ended SuperFlexService - deactivateThreshold()");
    }

    public void stopMIThreshold(SubscriberRequest request) throws CIServiceException, CIException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexService - stopMIThreshold()");
        CCATLogger.DEBUG_LOGGER.info("Start serving stopMIThreshold request for subscriber [" + request.getMsisdn() + "] ");

        CCATLogger.DEBUG_LOGGER.debug("Start preparing request url");
        String url = properties.getSuperFlexThresholdStop();
        if (url == null || url.isBlank()) {
            CCATLogger.DEBUG_LOGGER.info("Missing configuration [super flex threshold stop url]");
            throw new CIServiceException(ErrorCodes.ERROR.MISSING_CONFIGURATION, "super flex threshold stop url");
        }
        url = url.replace(CIDefines.SUPER_FLEX.SUPER_FLEX_MSISDN, request.getMsisdn());
        CCATLogger.DEBUG_LOGGER.debug("Super flex threshold stop url is [" + url + "]");

        CCATLogger.DEBUG_LOGGER.debug("Send super flex threshold stop charging request");
        String response = chargingInterfaceProxy.chargingRequest(url, "SuperFlexService");
        CCATLogger.DEBUG_LOGGER.debug("Super flex threshold stop charging response is [" + response + "]");

        CCATLogger.DEBUG_LOGGER.debug("Start parsing response");
        String statusCode = ciParser.parseSuperFlexThresholdStopResp(response);
        String result = "," + statusCode + ",";
        CCATLogger.DEBUG_LOGGER.debug("Super flex threshold stop parsed response is [" + result + "]");

        CCATLogger.DEBUG_LOGGER.debug("Start validating response");
        String ciSuccessCodes = properties.getSuperFlexCiSuccessCodes();
        if (ciSuccessCodes != null && !ciSuccessCodes.isBlank()) {
            if (!ciSuccessCodes.contains(result)) {
                CCATLogger.DEBUG_LOGGER.info("Invalid CI response : [" + result + "]");
                statusCode = statusCode.replace("SF_", ""); // TODO Handle prefix in exception messages cache
                throw new CIException(Integer.valueOf("-" + statusCode + "2"));
            }
        } else {
            CCATLogger.DEBUG_LOGGER.info("Missing configuration [Super Flex CI Success codes]");
            throw new CIServiceException(ErrorCodes.ERROR.MISSING_CONFIGURATION, "Super Flex CI Success codes");
        }
        CCATLogger.DEBUG_LOGGER.info("Finished serving stopMIThreshold request for subscriber [" + request.getMsisdn() + "] sucessfullly");
        CCATLogger.DEBUG_LOGGER.debug("Ended SuperFlexService - stopMIThreshold()");

    }

    public void deactivateStopMI(SubscriberRequest request) throws CIException, CIServiceException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexService - deactivateStopMI()");
        CCATLogger.DEBUG_LOGGER.info("Start serving deactivateStopMI request for subscriber [" + request.getMsisdn() + "] ");

        CCATLogger.DEBUG_LOGGER.debug("Start preparing request url");
        String url = properties.getSuperFlexDeactivateStopMi();
        if (url == null || url.isBlank()) {
            CCATLogger.DEBUG_LOGGER.info("Missing configuration [super flex deactivate stop MI url]");
            throw new CIServiceException(ErrorCodes.ERROR.MISSING_CONFIGURATION, "super flex deactivate stop MI url");
        }
        url = url.replace(CIDefines.SUPER_FLEX.SUPER_FLEX_MSISDN, request.getMsisdn());
        CCATLogger.DEBUG_LOGGER.debug("Super flex deactivate stop MI url is [" + url + "]");

        CCATLogger.DEBUG_LOGGER.debug("Send super flex tdeactivate stop MI charging request");
        String response = chargingInterfaceProxy.chargingRequest(url, "SuperFlexService");
        CCATLogger.DEBUG_LOGGER.debug("Super flex deactivate stop MI charging response is [" + response + "]");

        CCATLogger.DEBUG_LOGGER.debug("Start parsing response");
        String statusCode = ciParser.parseSuperFlexDeactivateStopMIResp(response);
        String result = "," + statusCode + ",";
        CCATLogger.DEBUG_LOGGER.debug("Super flex deactivate stop MI parsed response is [" + result + "]");

        CCATLogger.DEBUG_LOGGER.debug("Start validating response");
        String ciSuccessCodes = properties.getSuperFlexCiSuccessCodes();
        if (ciSuccessCodes != null && !ciSuccessCodes.isBlank()) {
            if (!ciSuccessCodes.contains(result)) {
                CCATLogger.DEBUG_LOGGER.info("Invalid CI response : [" + result + "]");
                statusCode = statusCode.replace("SF_", ""); // TODO Handle prefix in exception messages cache
                throw new CIException(Integer.valueOf("-" + statusCode + "2"));
            }
        } else {
            CCATLogger.DEBUG_LOGGER.info("Missing configuration [Super Flex CI Success codes]");
            throw new CIServiceException(ErrorCodes.ERROR.MISSING_CONFIGURATION, "Super Flex CI Success codes");
        }
        CCATLogger.DEBUG_LOGGER.info("Finished serving deactivateStopMI request for subscriber [" + request.getMsisdn() + "] sucessfullly");
        CCATLogger.DEBUG_LOGGER.debug("Ended SuperFlexService - deactivateStopMI()");

    }
}
