/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.util;

import com.asset.ccat.lookup_service.configurations.Properties;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.Accumlator;
import com.asset.ccat.lookup_service.models.DedicatedAccount;
import com.asset.ccat.lookup_service.models.ServiceOfferingPlanDescModel;
import com.asset.ccat.lookup_service.models.responses.AdmServiceClass;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wael.mohamed
 */
@Component
public class Utils {

    @Autowired
    private Properties properties;

    public void isFieldInteger(Integer param) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start null validation for [" + param + "]");
        if (param == null) {
            CCATLogger.DEBUG_LOGGER.debug("Validation failed, field [" + param + "] id null");
            throw new LookupException(ErrorCodes.ERROR.INVALID_PARAMETER, Defines.SEVERITY.VALIDATION, "serviceClassID " + param);
        }
        if (param <= 0) {
            CCATLogger.DEBUG_LOGGER.debug("Validation failed, field [" + param + "] id " + param);
            throw new LookupException(ErrorCodes.ERROR.INVALID_PARAMETER, Defines.SEVERITY.VALIDATION, "serviceClassID " + param);
        }
        CCATLogger.DEBUG_LOGGER.debug("Finished parameter validation successfully");
    }

    public String checkString(String name) {
        if (name.equalsIgnoreCase(null) || name.isBlank() || name.isEmpty()) {
            name = "";
        } else {
            name = name.replaceAll("'", "''");
        }
        return name;
    }

    public void checkDedAccountsDuplicate(AdmServiceClass serviceClass, Set<DedicatedAccount> dedAccountSet) throws LookupException {
        if (serviceClass.getDedAccounts() != null) {
            for (DedicatedAccount dedAccount : serviceClass.getDedAccounts()) {
                if (dedAccountSet.add(dedAccount) == false) {
                    CCATLogger.DEBUG_LOGGER.error("DedAccount with ID[" + dedAccount.getDaID() + "] is Duplicated.");
                    throw new LookupException(ErrorCodes.ERROR.DUPLICATED_DATA, Defines.SEVERITY.VALIDATION, "DedAccountId " + dedAccount.getDaID());
                }
            }
        } else {
            CCATLogger.DEBUG_LOGGER.error("DedAccounts is NULL.");
        }
    }

    public void checkAccumlatorsDuplicate(AdmServiceClass serviceClass, Set<Accumlator> accumlatorSet) throws LookupException {
        if (serviceClass.getAccumlators() != null) {
            for (Accumlator accumlator : serviceClass.getAccumlators()) {
                if (accumlatorSet.add(accumlator) == false) {
                    CCATLogger.DEBUG_LOGGER.error("Accumlator with ID[" + accumlator.getAccID() + "] is Duplicated.");
                    throw new LookupException(ErrorCodes.ERROR.DUPLICATED_DATA, Defines.SEVERITY.VALIDATION, "AccumulaterId " + accumlator.getAccID());
                }
            }
        } else {
            CCATLogger.DEBUG_LOGGER.error("Accumlators is NULL.");
        }
    }

    public void checkSODescDuplicate(AdmServiceClass serviceClass, Set<ServiceOfferingPlanDescModel> descSet) throws LookupException {
        if (serviceClass.getServiceOfferingPlanDescs() != null) {
            for (ServiceOfferingPlanDescModel description : serviceClass.getServiceOfferingPlanDescs()) {
                if (descSet.add(description) == false) {
                    CCATLogger.DEBUG_LOGGER.error("Service Offering Plan with ID[" + description.getPlanId() + "] is duplicate.");
                    throw new LookupException(ErrorCodes.ERROR.DUPLICATED_DATA, Defines.SEVERITY.VALIDATION, "Description for plan with ID " + description.getPlanId());
                }
            }
        } else {
            CCATLogger.DEBUG_LOGGER.error("ServiceOfferingPlanDescs is NULL.");
        }
    }

    public  String decodeString(String arg_String, String srcLanguage, String destinationLanguage) {
        String encodedString = arg_String;
        try {
            if (Objects.nonNull(arg_String)  && arg_String.isBlank()) {
                encodedString = new String(arg_String.getBytes(srcLanguage), destinationLanguage);
            }
        } catch (UnsupportedEncodingException e) {
            CCATLogger.DEBUG_LOGGER.error("Failed to decode string", e);
        }
        return encodedString;
    }


//    public String getOppositeMsisdnFormat(String msisdn){
//        String oldMsisdn = msisdn;
//        String newMsisdn = msisdn;
//
//        String oldPrefix = "";
//        String newPrefix = "";
//
//        String prefixMappingsStr = properties.getAirPrefixesMappings(); // get from properties
//
//        HashMap<String, String> msisdnPrefixes = new HashMap();
//
//        String[] tmp2 = null;
//        String[] tmp3 = null;
//
//        if (prefixMappingsStr != null) {
//            tmp2 = prefixMappingsStr.split(";");
//        }
//
//        if (tmp2 != null) {
//            for (int i = 0; i < tmp2.length; i++) {
//                if (tmp2[i] != null && !tmp2[i].isBlank()) {
//                    tmp3 = tmp2[i].trim().split(",");
//                    if (tmp3[0] != null && !tmp3[0].isBlank() && tmp3[1] != null && !tmp3[1].isBlank()) {
//                        msisdnPrefixes.put(tmp3[0].trim(), tmp3[1].trim());
//                    }
//                }
//            }
//        }
//
//        if (oldMsisdn.length() == 9) {
//            oldPrefix = oldMsisdn.substring(0, 2);
//            oldMsisdn = oldMsisdn.substring(2);
//
//        } else if (oldMsisdn.length() == 10) {
//            oldPrefix = oldMsisdn.substring(0, 3);
//            oldMsisdn = oldMsisdn.substring(3);
//        }
//
//        if (msisdnPrefixes.get(oldPrefix) != null) {
//            newPrefix = (String) msisdnPrefixes.get(oldPrefix);
//        }
//
//        //replace
//        newMsisdn = newPrefix + oldMsisdn;
//
//        return newMsisdn;
//    }
}
