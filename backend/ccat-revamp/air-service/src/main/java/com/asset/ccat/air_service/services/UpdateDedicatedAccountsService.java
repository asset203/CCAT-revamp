package com.asset.ccat.air_service.services;

import com.asset.ccat.air_service.cache.AIRRequestsCache;
import com.asset.ccat.air_service.configurations.Properties;
import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.mappers.UpdateBalanceAndDateMapper;
import com.asset.ccat.air_service.models.requests.UpdateDedicatedAccount;
import com.asset.ccat.air_service.models.requests.UpdateDedicatedBalanceRequest;
import com.asset.ccat.air_service.models.requests.UpdateLimitRequest;
import com.asset.ccat.air_service.parser.AIRParser;
import com.asset.ccat.air_service.proxy.AIRProxy;
import com.asset.ccat.air_service.utils.AIRUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.*;

import static com.asset.ccat.air_service.defines.AIRDefines.adjustmentAmountRelative;

/**
 * @author Mahmoud Shehab
 */
@Component
public class UpdateDedicatedAccountsService {

    @Autowired
    Properties properties;
    @Autowired
    AIRRequestsCache aIRRequestsCache;
    @Autowired
    AIRProxy aIRProxy;
    @Autowired
    AIRUtils aIRUtils;
    @Autowired
    AIRParser aIRParser;
    @Autowired
    UpdateBalanceAndDateMapper balanceAndDateMapper;
    @Autowired
    private UserLimitsService userLimitsService;


    public void updateDedicatedAccounts(UpdateDedicatedBalanceRequest dedicatedBalanceRequest) throws AIRServiceException, AIRException {
        try {
            String dedValue = generateDedicatedValuesXML(dedicatedBalanceRequest.getList());
            CCATLogger.DEBUG_LOGGER.debug("prepared dedicated-value = {}", dedValue);
            String xmlRequest = aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.UPDATE_DEDICATED_ACCOUNTS);
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, dedicatedBalanceRequest.getMsisdn());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, dedicatedBalanceRequest.getUsername().toLowerCase());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1");
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, aIRUtils.getCurrentFormattedDate());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, properties.getOriginNodeType());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME, properties.getOriginHostName());
            xmlRequest = xmlRequest.replace(AIRDefines.UPDATE_BALANCE_PLACEHOLDER.ADJUSTMENT_AMOUNT_RELATIVE, adjustmentAmountRelative);
            xmlRequest = xmlRequest.replace(AIRDefines.UPDATE_BALANCE_PLACEHOLDER.EXTERNAL_DATA_1, dedicatedBalanceRequest.getTransactionType());
            xmlRequest = xmlRequest.replace(AIRDefines.UPDATE_BALANCE_PLACEHOLDER.EXTERNAL_DATA_2, dedicatedBalanceRequest.getTransactionCode());
            xmlRequest = xmlRequest.replace(AIRDefines.UPDATE_DEDICATED_PLACEHOLDER.DEDICATED_ACCOUNTS_VALUE, dedValue);
            xmlRequest = xmlRequest.replace(AIRDefines.UPDATE_DEDICATED_PLACEHOLDER.ALLOW_COMPOSE, "0");
            CCATLogger.DEBUG_LOGGER.debug(" AIR update dedicated account request is " + xmlRequest);
            String result = aIRProxy.sendAIRRequest(xmlRequest);
            CCATLogger.DEBUG_LOGGER.debug(" AIR update dedicated account response is " + result);
            HashMap resultMap = aIRParser.parse(result);
            balanceAndDateMapper.map(dedicatedBalanceRequest.getMsisdn(), resultMap);
            Float totalsAmounts = 0.0f;
            Float balance = 0.0f;
            Integer actionType = -1;
            for (UpdateDedicatedAccount account : dedicatedBalanceRequest.getList()) {
                if (Objects.nonNull(account.getAdjustmentAmount()) && Objects.nonNull(account.getAdjustmentMethod())) {
                    totalsAmounts += account.getAdjustmentAmount();
                    balance += account.getBalance();
                    actionType = account.getAdjustmentMethod();
                }
            }
            if(actionType!=-1){
                UpdateLimitRequest updateLimitRequest = new UpdateLimitRequest(dedicatedBalanceRequest.getUserId(),
                        actionType,
                        totalsAmounts,
                        balance);
                updateLimitRequest.setToken(dedicatedBalanceRequest.getToken());
                CCATLogger.DEBUG_LOGGER.debug("UpdateDedicatedAccountsService -> updateDedicatedAccounts() : Starting Update User Limits ");
                userLimitsService.updateLimits(updateLimitRequest);
                CCATLogger.DEBUG_LOGGER.debug("UpdateDedicatedAccountsService -> updateDedicatedAccounts() : Ending Update User Limits ");
            }
             } catch (AIRServiceException | AIRException ex) {
            throw ex;
        } catch (IOException | SAXException ex) {
            CCATLogger.DEBUG_LOGGER.error(" Error while parsing response ", ex);
            CCATLogger.ERROR_LOGGER.error(" Error while parsing response ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Unknown error in updateDedicatedAccounts() | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Unknown error in updateDedicatedAccounts()", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    private String generateDedicatedValuesXML(List<UpdateDedicatedAccount> dedicatedAccounts) throws AIRServiceException {
        String dedicatedAccountXML = "";
        String dedicatedAccountValue = "";
        try {
            if (!dedicatedAccounts.isEmpty()) {
                CCATLogger.DEBUG_LOGGER.debug("# DedicatedAccounts = [{}] accounts.", dedicatedAccounts.size());
                dedicatedAccountXML = AIRDefines.AIR_TAGS.TAG_ARRAY_DATA;
                dedicatedAccountXML = dedicatedAccountXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.dedicatedAccountUpdateInformation);
                for (UpdateDedicatedAccount dedicatedAccount : dedicatedAccounts) {
                    String dedicatedAccountItem = generateRow(dedicatedAccount);
                    dedicatedAccountValue += dedicatedAccountItem;
                }
                if (dedicatedAccountValue.trim().equals("")) {
                    dedicatedAccountXML = "";
                } else {
                    dedicatedAccountXML = dedicatedAccountXML.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, dedicatedAccountValue);
                }
            }
            return dedicatedAccountXML;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("Exception in generateDedicatedValuesXML()");
            CCATLogger.ERROR_LOGGER.error("Exception in generateDedicatedValuesXML()", e);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_WHILE_PARSING_REQUEST);
        }
    }

    private String generateRow(UpdateDedicatedAccount dedicatedAccount) throws Exception {

        String dedicatedAccountItem = "";
        if (!dedicatedAccount.getIsDateEdited() && dedicatedAccount.getAdjustmentMethod() == 0) {
            CCATLogger.DEBUG_LOGGER.debug("dedicatedAccount of ID = {}, isDateEdited = {} and adjustmentMethod = {} --> should be skipped", dedicatedAccount.getId(), dedicatedAccount.getIsDateEdited(), dedicatedAccount.getAdjustmentMethod());
            return dedicatedAccountItem;
        }

        dedicatedAccountItem = AIRDefines.AIR_TAGS.TAG_STRUCT_1MEMBER;
        String dedicatedAccountID = AIRDefines.AIR_TAGS.TAG_MEMBER_I4;
        dedicatedAccountID
                = dedicatedAccountID.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.dedicatedAccountID);
        dedicatedAccountID = dedicatedAccountID.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, String.valueOf(dedicatedAccount.getId()));
        dedicatedAccountID += "\n";
        dedicatedAccountItem = dedicatedAccountItem.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_1, dedicatedAccountID + AIRDefines.AIR_TAGS.TAG_MEMBER_1);

        if (dedicatedAccount.getAdjustmentMethod() == AIRDefines.UPDATE_BALANCE_ADD) {
            String feesPT = aIRUtils.amountInPT(dedicatedAccount.getAdjustmentAmount());
            String adjustmentAmountRelative = AIRDefines.AIR_TAGS.TAG_MEMBER_STRING;
            adjustmentAmountRelative = adjustmentAmountRelative.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.adjustmentAmountRelative);
            adjustmentAmountRelative = adjustmentAmountRelative.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, feesPT);
            adjustmentAmountRelative += "\n";
            dedicatedAccountItem = dedicatedAccountItem.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_1, adjustmentAmountRelative + AIRDefines.AIR_TAGS.TAG_MEMBER_1);
        } else if (dedicatedAccount.getAdjustmentMethod() == AIRDefines.UPDATE_BALANCE_SUBTRACT) {
            String feesPT = aIRUtils.amountInPT(dedicatedAccount.getAdjustmentAmount());
            String adjustmentAmountRelative = AIRDefines.AIR_TAGS.TAG_MEMBER_STRING;
            adjustmentAmountRelative
                    = adjustmentAmountRelative.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.adjustmentAmountRelative);
            adjustmentAmountRelative
                    = adjustmentAmountRelative.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, "-" + feesPT);
            adjustmentAmountRelative
                    += "\n";
            dedicatedAccountItem = dedicatedAccountItem.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_1, adjustmentAmountRelative + AIRDefines.AIR_TAGS.TAG_MEMBER_1);
        } else if (dedicatedAccount.getAdjustmentMethod() == AIRDefines.UPDATE_BALANCE_SETAMT) {
            String feesPT = aIRUtils.amountInPT(dedicatedAccount.getAdjustmentAmount());
            String dedicatedAccountValueNew = AIRDefines.AIR_TAGS.TAG_MEMBER_STRING;
            dedicatedAccountValueNew = dedicatedAccountValueNew.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.dedicatedAccountValueNew);
            dedicatedAccountValueNew = dedicatedAccountValueNew.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, feesPT);
            dedicatedAccountValueNew += "\n";
            dedicatedAccountItem = dedicatedAccountItem.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_1, dedicatedAccountValueNew + AIRDefines.AIR_TAGS.TAG_MEMBER_1);
        }

        if (dedicatedAccount.getIsDateEdited()) {
            String expiryDate = AIRDefines.AIR_TAGS.TAG_MEMBER_DATE;
            expiryDate = expiryDate.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.expiryDate);
            if (dedicatedAccount.getExpiryDate() != null) {
                expiryDate = expiryDate.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, aIRUtils.formatNewAIR(dedicatedAccount.getExpiryDate()));
            } else {
                expiryDate = expiryDate.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, AIRDefines.INFINITY_DATE_AIR);
            }
            expiryDate += "\n";
            dedicatedAccountItem = dedicatedAccountItem.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_1, expiryDate + AIRDefines.AIR_TAGS.TAG_MEMBER_1);
        }
        if (properties.getIsCs5()) {
            String dedicatedAccountUnitType = AIRDefines.AIR_TAGS.TAG_MEMBER_I4;
            dedicatedAccountUnitType = dedicatedAccountUnitType.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.dedicatedAccountUnitType);
            dedicatedAccountUnitType = dedicatedAccountUnitType.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, dedicatedAccount.getUnitType());
            dedicatedAccountItem = dedicatedAccountItem.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_1, dedicatedAccountUnitType);
        } else {
            dedicatedAccountItem = dedicatedAccountItem.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_1, "");
        }
        CCATLogger.DEBUG_LOGGER.debug("The generated row of the [dedicatedAccountItem] = {}", dedicatedAccountItem);
        return dedicatedAccountItem;
    }
}
