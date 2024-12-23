/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.mappers;

import com.asset.ccat.air_service.configurations.Properties;
import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.models.PamInformationModel;
import com.asset.ccat.air_service.models.ServiceClassModel;
import com.asset.ccat.air_service.models.SubscriberAccountModel;
import com.asset.ccat.air_service.models.shared.LookupModel;
import com.asset.ccat.air_service.services.LookupsService;
import com.asset.ccat.air_service.utils.AIRUtils;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Mahmoud Shehab
 */
@Component
public class GetAccountDetailsMapper {

    @Autowired
    LookupsService lookupsService;

    @Autowired
    AIRUtils aIRUtils;

    @Autowired
    Properties properties ;
    public SubscriberAccountModel map(String msisdn, HashMap map) throws AIRServiceException, AIRException {
        SubscriberAccountModel accountModel = new SubscriberAccountModel();
        HashMap accountFlags = (HashMap) map.get(AIRDefines.accountFlags);
        String responseCode = (String) map.get(AIRDefines.responseCode);
//        aIRUtils.validateUCIPResponseCodes(responseCode);
        String scId = (String) map.get(AIRDefines.serviceClassCurrent);
        String origScId = (String) map.get(AIRDefines.serviceClassCurrent);
        ServiceClassModel serviceClassModel = lookupsService.getServiceClasses().get(Integer.parseInt(scId));
        ServiceClassModel originalServiceClassModel = lookupsService.getServiceClasses().get(Integer.parseInt(origScId));
        String temporaryBlockedFlag = (String) map.get(AIRDefines.temporaryBlockedFlag);
        String negativeBarringStatusFlag = (String) accountFlags.get(AIRDefines.negativeBarringStatusFlag);
        String refillDateTime = (String) map.get(AIRDefines.refillUnbarDateTime);
        String maxServiceFeePeriod = (String) map.get(AIRDefines.maxServiceFeePeriod);
        String maxSupervisionPeriod = (String) map.get(AIRDefines.maxSupervisionPeriod);
        Date refillDate = null;
        Integer supervisionPeriod = (map.get(AIRDefines.supervisionPeriod) == null) ? 0 : Integer.parseInt((String) map.get(AIRDefines.supervisionPeriod));
        Integer serviceFeePeriod = (map.get(AIRDefines.serviceFeePeriod) == null) ? 0 : Integer.parseInt((String) map.get(AIRDefines.serviceFeePeriod));
        String barringStatus = getSubscriberBarringStatus(accountFlags, temporaryBlockedFlag);
        String status = getSubscriberStatus(accountFlags);
        LookupModel language = lookupsService.getLanguages().get(map.get(AIRDefines.languageIDCurrent));

        //Check refill untill date time if equals NULL, this means that the subscriber is not Refill Barred
        if (Objects.nonNull(refillDateTime)) {
            refillDate = aIRUtils.parseAirDate(refillDateTime);
            barringStatus = "Refill Barred";
        }

        String creditClearance = "";
        if (Objects.nonNull(map.get(AIRDefines.creditClearancePeriod)) && Objects.nonNull(map.get(AIRDefines.creditClearanceDate))) {


            Date creditClearanceDate = aIRUtils.parseAirDate((String) map.get(AIRDefines.creditClearanceDate));
            String creditClearanceStr = aIRUtils.formatCCATDate(creditClearanceDate);
            creditClearance = map.get(AIRDefines.creditClearancePeriod)
                    + ("".equals(creditClearanceStr) ? "" : (" days (" + creditClearanceStr + ")"));
        }
        String serviceRemoval = "";
        if (Objects.nonNull(map.get(AIRDefines.serviceRemovalPeriod)) && Objects.nonNull(map.get(AIRDefines.serviceRemovalDate))) {
            Date serviceRemovalDate = aIRUtils.parseAirDate((String) map.get(AIRDefines.serviceRemovalDate));
            String serviceRemovalString = aIRUtils.formatCCATDate(serviceRemovalDate);
            serviceRemoval = map.get(AIRDefines.serviceRemovalPeriod) + " days (" + serviceRemovalString + ")";
        }

        //Set negative balance flag with TRUE or FALSE based on negativeBarringStatusFlag value
        if (temporaryBlockedFlag != null && temporaryBlockedFlag.equals(AIRDefines.AIR_BOOLEAN_TRUE)) {
            accountModel.setIsTempBlocked(true);
        } else {
            accountModel.setIsTempBlocked(false);
        }

        accountModel.setSubscriberNumber(msisdn);
        String balanceStr = (String) map.get(AIRDefines.accountValue1);
        if (balanceStr != null && !balanceStr.isBlank()) {
            accountModel.setBalance(aIRUtils.amountInLE(balanceStr));
        }
        String activationDateStr = (String) map.get(AIRDefines.activationDate);
        accountModel.setActivationDate(aIRUtils.parseAirDate(activationDateStr));
//        HashMap curruncyMap = (HashMap) map.get(AIRDefines.CHARGES_RESULT_INFO);
        //Set negative balance flag with TRUE or FALSE based on negativeBarringStatusFlag value
        if (Objects.nonNull(negativeBarringStatusFlag) && negativeBarringStatusFlag.equals(AIRDefines.AIR_BOOLEAN_TRUE)) {
            accountModel.setIsNegativeBalBarring(true);
            accountModel.setNegativeBalanceBarDate(null);
        } else {
            accountModel.setIsNegativeBalBarring(false);
            accountModel.setNegativeBalanceBarDate(null);
        }
        String currency = (String) map.get(AIRDefines.currency1);
//        HashMap chargingResultInformation = (HashMap) map.get(AIRDefines.CHARGING_RESULT_INFORMATION);
//        String currency = (String) chargingResultInformation.get(AIRDefines.currency1);
        accountModel.setCurrency(currency);
        accountModel.setSubscriberNumber(msisdn);
        accountModel.setServiceClass(serviceClassModel);
        accountModel.setSupervisionFeePeriod(String.valueOf(supervisionPeriod));
        Date supervisionFeeExpiryDate = aIRUtils.addDaysToCurrentDate((String) map.get(AIRDefines.supervisionPeriod));
        accountModel.setSupervisionFeePeriodExpiryDate(supervisionFeeExpiryDate);
        accountModel.setServiceFeePeriod(String.valueOf(serviceFeePeriod));
        Date serviceFeeExpiryDate = aIRUtils.addDaysToCurrentDate((String) map.get(AIRDefines.serviceFeePeriod));
        accountModel.setServiceFeePeriodExpiryDate(serviceFeeExpiryDate);
        accountModel.setStatus(status);

        accountModel.setLanguageId(language != null ? Integer.valueOf(language.getKey()) : null);
        accountModel.setLanguage(Objects.nonNull(language) ? language.getValue() : "");

        accountModel.setBarringStatus(barringStatus);
        accountModel.setRefillBarredUntil(refillDate);
        accountModel.setCreditClearance(creditClearance);
        accountModel.setServiceRemoval(serviceRemoval);
        accountModel.setMaxServiceFeePeriod(maxServiceFeePeriod);
        accountModel.setMaxSupervisionPeriod(maxSupervisionPeriod);
        accountModel.setOriginalServiceClass(Objects.isNull(originalServiceClassModel) ? "" : originalServiceClassModel.getName());

        Object pams = map.get(AIRDefines.pamInformationList);
        accountModel.setAccountGroupNumber(map.get(AIRDefines.accountGroupID) == null ? null : Integer.valueOf((String) map.get(AIRDefines.accountGroupID)));
        accountModel.setPams(Objects.isNull(pams) ? null : (ArrayList<PamInformationModel>) pams);

        return accountModel;
    }

    private String getSubscriberBarringStatus(HashMap accountFlags, String tempBlocked) {
        boolean activationStatusFlag = (accountFlags.get(AIRDefines.activationStatusFlag) != null && accountFlags.get(AIRDefines.activationStatusFlag).equals("1"));
        boolean negativeBarringStatusFlag = (accountFlags.get(AIRDefines.negativeBarringStatusFlag) != null && accountFlags.get(AIRDefines.negativeBarringStatusFlag).equals("1"));
        boolean supervisionPeriodWarningActiveFlag = (accountFlags.get(AIRDefines.supervisionPeriodWarningActiveFlag) != null && accountFlags.get(AIRDefines.supervisionPeriodWarningActiveFlag).equals("1"));
        boolean serviceFeePeriodWarningActiveFlag = (accountFlags.get(AIRDefines.serviceFeePeriodWarningActiveFlag) != null && accountFlags.get(AIRDefines.serviceFeePeriodWarningActiveFlag).equals("1"));
        boolean supervisionPeriodExpiryFlag = (accountFlags.get(AIRDefines.supervisionPeriodExpiryFlag) != null && accountFlags.get(AIRDefines.supervisionPeriodExpiryFlag).equals("1"));
        boolean serviceFeePeriodExpiryFlag = (accountFlags.get(AIRDefines.serviceFeePeriodExpiryFlag) != null && accountFlags.get(AIRDefines.serviceFeePeriodExpiryFlag).equals("1"));
        if (tempBlocked != null && tempBlocked.equals("1")) {
            return "Temporary Blocked";
        }
        if (!activationStatusFlag &&
                !supervisionPeriodWarningActiveFlag &&
                !serviceFeePeriodWarningActiveFlag &&
                !supervisionPeriodExpiryFlag &&
                !serviceFeePeriodExpiryFlag) {
            return "Outgoing Call Barred";
        }
        if (negativeBarringStatusFlag) {
            return "Negative Balance Barred";
        }
        if (supervisionPeriodExpiryFlag) {
            return "Outgoing Call Barred";
        }
        if (serviceFeePeriodExpiryFlag) {
            return "Incoming/Outgoing Call Barred";
        }
        return "";
    }

    private String getSubscriberStatus(HashMap accountFlags) {
        boolean activationStatusFlag = (accountFlags.get(AIRDefines.activationStatusFlag) != null && accountFlags.get(AIRDefines.activationStatusFlag).equals("1"));
        boolean supervisionPeriodWarningActiveFlag = (accountFlags.get(AIRDefines.supervisionPeriodWarningActiveFlag) != null && accountFlags.get(AIRDefines.supervisionPeriodWarningActiveFlag).equals("1"));
        boolean serviceFeePeriodWarningActiveFlag = (accountFlags.get(AIRDefines.serviceFeePeriodWarningActiveFlag) != null && accountFlags.get(AIRDefines.serviceFeePeriodWarningActiveFlag).equals("1"));
        boolean supervisionPeriodExpiryFlag = (accountFlags.get(AIRDefines.supervisionPeriodExpiryFlag) != null && accountFlags.get(AIRDefines.supervisionPeriodExpiryFlag).equals("1"));
        boolean serviceFeePeriodExpiryFlag = (accountFlags.get(AIRDefines.serviceFeePeriodExpiryFlag) != null && accountFlags.get(AIRDefines.serviceFeePeriodExpiryFlag).equals("1"));
        if (!activationStatusFlag && !supervisionPeriodWarningActiveFlag && !serviceFeePeriodWarningActiveFlag && !supervisionPeriodExpiryFlag && !serviceFeePeriodExpiryFlag) {
            return AIRDefines.SUBSCRIBER_STATUS.SUBSCRIBER_STATUS_INSTALLED;
        }
        if (activationStatusFlag && !supervisionPeriodWarningActiveFlag && !serviceFeePeriodWarningActiveFlag && !supervisionPeriodExpiryFlag && !serviceFeePeriodExpiryFlag) {
            return AIRDefines.SUBSCRIBER_STATUS.SUBSCRIBER_STATUS_ACTIVE;
        }
        if (activationStatusFlag && supervisionPeriodWarningActiveFlag && !supervisionPeriodExpiryFlag) {
            return AIRDefines.SUBSCRIBER_STATUS.SUBSCRIBER_STATUS_SUPERVISION_WARNING;
        }
        if (activationStatusFlag && supervisionPeriodExpiryFlag && !serviceFeePeriodExpiryFlag) {
            return AIRDefines.SUBSCRIBER_STATUS.SUBSCRIBER_STATUS_IN_GRACE;
        }
        if (serviceFeePeriodExpiryFlag) {
            return AIRDefines.SUBSCRIBER_STATUS.SUBSCRIBER_STATUS_EXPIRED;
        }
        return "";
    }
}
