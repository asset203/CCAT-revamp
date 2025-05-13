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
import com.asset.ccat.air_service.utils.ReplacePlaceholderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

import static com.asset.ccat.air_service.defines.AIRDefines.adjustmentAmountRelative;

/**
 * @author Mahmoud Shehab
 */
@Component
public class UpdateDedicatedAccountsService {
    private final Properties properties;
    private final AIRRequestsCache aIRRequestsCache;
    private final AIRProxy aIRProxy;
    private final AIRUtils aIRUtils;
    private final AIRParser aIRParser;
    private final UpdateBalanceAndDateMapper balanceAndDateMapper;

    private final UserLimitsService userLimitsService;

    @Autowired
    public UpdateDedicatedAccountsService(Properties properties, AIRRequestsCache aIRRequestsCache, AIRProxy aIRProxy, AIRUtils aIRUtils, AIRParser aIRParser, UpdateBalanceAndDateMapper balanceAndDateMapper, UserLimitsService userLimitsService) {
        this.properties = properties;
        this.aIRRequestsCache = aIRRequestsCache;
        this.aIRProxy = aIRProxy;
        this.aIRUtils = aIRUtils;
        this.aIRParser = aIRParser;
        this.balanceAndDateMapper = balanceAndDateMapper;
        this.userLimitsService = userLimitsService;
    }


    public void updateDedicatedAccounts(UpdateDedicatedBalanceRequest dedicatedBalanceRequest) throws AIRServiceException, AIRException {
        try {
            String dedValue = generateDedicatedValuesXML(dedicatedBalanceRequest.getList());
            CCATLogger.DEBUG_LOGGER.debug("prepared dedicated-value = {}", dedValue);
            String xmlRequest = new ReplacePlaceholderBuilder()
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, dedicatedBalanceRequest.getMsisdn())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, dedicatedBalanceRequest.getUsername().toLowerCase())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1")
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, aIRUtils.getCurrentFormattedDate())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, properties.getOriginNodeType())
                    .addPlaceholder(AIRDefines.UPDATE_BALANCE_PLACEHOLDER.ADJUSTMENT_AMOUNT_RELATIVE, adjustmentAmountRelative)
                    .addPlaceholder(AIRDefines.UPDATE_BALANCE_PLACEHOLDER.EXTERNAL_DATA_1, dedicatedBalanceRequest.getTransactionType())
                    .addPlaceholder(AIRDefines.UPDATE_BALANCE_PLACEHOLDER.EXTERNAL_DATA_2, dedicatedBalanceRequest.getTransactionCode())
                    .addPlaceholder(AIRDefines.UPDATE_DEDICATED_PLACEHOLDER.DEDICATED_ACCOUNTS_VALUE, dedValue)
                    .addPlaceholder(AIRDefines.UPDATE_DEDICATED_PLACEHOLDER.ALLOW_COMPOSE, "0")
                    .buildUrl(aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.UPDATE_DEDICATED_ACCOUNTS));

            CCATLogger.DEBUG_LOGGER.debug(" AIR update dedicated account request is {}", xmlRequest);
            String result = aIRProxy.sendAIRRequest(xmlRequest);
            HashMap resultMap = aIRParser.parse(result);
            balanceAndDateMapper.map(dedicatedBalanceRequest.getMsisdn(), resultMap);
            BigDecimal totalsAmounts = BigDecimal.ZERO;
            BigDecimal balance = BigDecimal.ZERO;
            Integer actionType = -1;
            for (UpdateDedicatedAccount account : dedicatedBalanceRequest.getList()) {
                if (Objects.nonNull(account.getAdjustmentAmount()) && Objects.nonNull(account.getAdjustmentMethod())) {
                    totalsAmounts= totalsAmounts.add(account.getAdjustmentAmount());
                    balance = balance.add(account.getBalance());
                    actionType = account.getAdjustmentMethod();
                }
            }
            if (actionType != -1) {
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
            CCATLogger.DEBUG_LOGGER.error("Exception in updateDedicatedAccounts()", ex);
            CCATLogger.ERROR_LOGGER.error("Exception in updateDedicatedAccounts()", ex);
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
            CCATLogger.DEBUG_LOGGER.error("Exception in generateDedicatedValuesXML() ", e);
            CCATLogger.ERROR_LOGGER.error("Exception in generateDedicatedValuesXML()", e);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_WHILE_PARSING_REQUEST);
        }
    }

    private String generateRow(UpdateDedicatedAccount dedicatedAccount) throws ParseException {
        String dedicatedAccountItem = "";
        if (Boolean.TRUE.equals(!dedicatedAccount.getIsDateEdited()) && dedicatedAccount.getAdjustmentMethod() == 0) {
            CCATLogger.DEBUG_LOGGER.debug("dedicatedAccount of ID = {}, isDateEdited = {} and adjustmentMethod = {} --> should be skipped", dedicatedAccount.getId(), dedicatedAccount.getIsDateEdited(), dedicatedAccount.getAdjustmentMethod());
            return dedicatedAccountItem;
        }

        dedicatedAccountItem = AIRDefines.AIR_TAGS.TAG_STRUCT_1MEMBER;
        String dedicatedAccountID = new ReplacePlaceholderBuilder()
                .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.dedicatedAccountID)
                .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, String.valueOf(dedicatedAccount.getId()))
                .buildUrl(AIRDefines.AIR_TAGS.TAG_MEMBER_I4);
        dedicatedAccountItem = dedicatedAccountItem.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_1, dedicatedAccountID + "\n"+ AIRDefines.AIR_TAGS.TAG_MEMBER_1);

        if (dedicatedAccount.getAdjustmentMethod() == AIRDefines.UPDATE_BALANCE_ADD) {
            String feesPT = aIRUtils.amountInPT(dedicatedAccount.getAdjustmentAmount());
            String adjustmentAmountRelative = new ReplacePlaceholderBuilder()
                    .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.adjustmentAmountRelative)
                    .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, feesPT)
                    .buildUrl(AIRDefines.AIR_TAGS.TAG_MEMBER_STRING);
            dedicatedAccountItem = dedicatedAccountItem.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_1, adjustmentAmountRelative + "\n"+ AIRDefines.AIR_TAGS.TAG_MEMBER_1);
        } else if (dedicatedAccount.getAdjustmentMethod() == AIRDefines.UPDATE_BALANCE_SUBTRACT) {
            String feesPT = aIRUtils.amountInPT(dedicatedAccount.getAdjustmentAmount());
            String adjustmentAmountRelative = new ReplacePlaceholderBuilder()
                    .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.adjustmentAmountRelative)
                    .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, "-" + feesPT)
                    .buildUrl(AIRDefines.AIR_TAGS.TAG_MEMBER_STRING);
            dedicatedAccountItem = dedicatedAccountItem.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_1, adjustmentAmountRelative + "\n"+ AIRDefines.AIR_TAGS.TAG_MEMBER_1);
        } else if (dedicatedAccount.getAdjustmentMethod() == AIRDefines.UPDATE_BALANCE_SETAMT) {
            String feesPT = aIRUtils.amountInPT(dedicatedAccount.getAdjustmentAmount());
            String dedicatedAccountValueNew = new ReplacePlaceholderBuilder()
                    .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.dedicatedAccountValueNew)
                    .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, feesPT)
                    .buildUrl(AIRDefines.AIR_TAGS.TAG_MEMBER_STRING);
            dedicatedAccountItem = dedicatedAccountItem.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_1, dedicatedAccountValueNew + "\n"+ AIRDefines.AIR_TAGS.TAG_MEMBER_1);
        }

        if (Boolean.TRUE.equals(dedicatedAccount.getIsDateEdited())) {
            String expiryDate = new ReplacePlaceholderBuilder()
                    .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.expiryDate)
                    .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, dedicatedAccount.getExpiryDate() != null ?
                            aIRUtils.formatNewAIR(dedicatedAccount.getExpiryDate()) : AIRDefines.INFINITY_DATE_AIR)
                    .buildUrl(AIRDefines.AIR_TAGS.TAG_MEMBER_DATE);
            dedicatedAccountItem = dedicatedAccountItem.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_1, expiryDate + "\n" + AIRDefines.AIR_TAGS.TAG_MEMBER_1);
        }
        if (Boolean.TRUE.equals(properties.getIsCs5())) {
            String dedicatedAccountUnitType = new ReplacePlaceholderBuilder()
                    .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.dedicatedAccountUnitType)
                    .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, dedicatedAccount.getUnitType())
                    .buildUrl(AIRDefines.AIR_TAGS.TAG_MEMBER_I4);
            dedicatedAccountItem = dedicatedAccountItem.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_1, dedicatedAccountUnitType);
        } else
            dedicatedAccountItem = dedicatedAccountItem.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_1, "");

        CCATLogger.INTERFACE_LOGGER.debug("The generated row of the [dedicatedAccountItem] = {}", dedicatedAccountItem);
        return dedicatedAccountItem;
    }
}
