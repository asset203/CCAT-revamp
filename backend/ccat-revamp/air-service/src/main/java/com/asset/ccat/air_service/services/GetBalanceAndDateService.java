/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.services;

import com.asset.ccat.air_service.cache.AIRRequestsCache;
import com.asset.ccat.air_service.configurations.Properties;
import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.mappers.GetBalanceAndDateMapper;
import com.asset.ccat.air_service.models.DedicatedAccount;
import com.asset.ccat.air_service.models.SubscriberAccountModel;
import com.asset.ccat.air_service.models.requests.GetDedicatedAccountsRequest;
import com.asset.ccat.air_service.parsers.AIRParser;
import com.asset.ccat.air_service.proxy.AIRProxy;
import com.asset.ccat.air_service.utils.AIRUtils;
import com.asset.ccat.air_service.utils.ReplacePlaceholderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author Mahmoud Shehab
 */
@Component
public class GetBalanceAndDateService {
    private final AIRRequestsCache aIRRequestsCache;
    private final AIRProxy aIRProxy;
    private final AIRUtils aIRUtils;
    private final AIRParser aIRParser;
    private final GetBalanceAndDateMapper getBalanceAndDateMapper;
    private final LookupsService lookupsService;
    private final GetAccountDetailsService getAccountDetailsService;
    private final Properties properties;

    @Autowired
    public GetBalanceAndDateService(AIRRequestsCache aIRRequestsCache, AIRProxy aIRProxy, AIRUtils aIRUtils, AIRParser aIRParser, GetBalanceAndDateMapper getBalanceAndDateMapper, LookupsService lookupsService, GetAccountDetailsService getAccountDetailsService, Properties properties) {
        this.aIRRequestsCache = aIRRequestsCache;
        this.aIRProxy = aIRProxy;
        this.aIRUtils = aIRUtils;
        this.aIRParser = aIRParser;
        this.getBalanceAndDateMapper = getBalanceAndDateMapper;
        this.lookupsService = lookupsService;
        this.getAccountDetailsService = getAccountDetailsService;
        this.properties = properties;
    }

    public List<DedicatedAccount> getBalanceAndDate(GetDedicatedAccountsRequest request) throws AIRServiceException, AIRException {
        try {
            String xmlRequest = new ReplacePlaceholderBuilder()
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, request.getMsisdn())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, request.getUsername().toLowerCase())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, properties.getOriginNodeType())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME, properties.getOriginHostName())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1")
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, aIRUtils.getCurrentFormattedDate())
                    .buildUrl(aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.GET_BALANCE_AND_DATE));
            CCATLogger.DEBUG_LOGGER.debug(" AIR getBalanceAndDate request is\n{}", xmlRequest);
            String result = aIRProxy.sendAIRRequest(xmlRequest);
            HashMap resultMap = aIRParser.parse(result);
            List<DedicatedAccount> list = getBalanceAndDateMapper.map(request.getMsisdn(), resultMap);
            calculateUnits(list, request.getAccountModel());
            return list;
        } catch (AIRServiceException | AIRException ex) {
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception while getting Balance and Date from air. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception while getting Balance and Date from air. ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        }
    }

    private void calculateUnits(List<DedicatedAccount> dedicatedAccountList, SubscriberAccountModel accountModel) throws AIRServiceException {
        HashMap<Integer, HashMap<Integer, DedicatedAccount>> cachedDedicatedAccountsMap = lookupsService.getDedicatedAccountsMap();
        HashMap<Integer, DedicatedAccount> serviceClassDas = cachedDedicatedAccountsMap.get(accountModel.getServiceClass().getCode());
        if (Objects.isNull(serviceClassDas)) {
            return;
        }
        for (DedicatedAccount da : dedicatedAccountList) {
            DedicatedAccount cachedDa = serviceClassDas.get(Integer.valueOf(da.getId()));
            if (Objects.nonNull(cachedDa)
                    && Objects.nonNull(cachedDa.getRatingFactor())
                    && cachedDa.getRatingFactor() > 0.0F) {
                String units = Float.toString(da.getBalance() / cachedDa.getRatingFactor());
                da.setUnits(units);
                da.setDescription(cachedDa.getDescription());
            }
        }
    }
}
