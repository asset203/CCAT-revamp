package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.mappers.AccountGroupResponseMapper;
import com.asset.ccat.gateway.models.customer_care.AccountGroupModel;
import com.asset.ccat.gateway.models.requests.customer_care.account_group.GetAccountGroupsRequest;
import com.asset.ccat.gateway.models.requests.customer_care.account_group.UpdateAccountGroupRequest;
import com.asset.ccat.gateway.models.responses.customer_care.GetAllAccountGroupsResponse;
import com.asset.ccat.gateway.models.shared.AccountGroupWithBitsModel;
import com.asset.ccat.gateway.proxy.AccountGroupsProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class AccountGroupsService {

    @Autowired
    private LookupsService lookupsService;

    @Autowired
    private AccountGroupsProxy accountGroupsProxy;

    @Autowired
    private AccountGroupResponseMapper accountGroupResponseMapper;

    public GetAllAccountGroupsResponse getAllAccountGroups(GetAccountGroupsRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("AccountGroupsService -> getAllAccountGroups() : Started");
        CCATLogger.DEBUG_LOGGER.info("Start serving get all account groups request");

        CCATLogger.DEBUG_LOGGER.debug("Getting current account group model for subscriber [" + request.getMsisdn() + "]");
        AccountGroupModel currentPlan = accountGroupsProxy.getCurrentAccountGroup(request);

        CCATLogger.DEBUG_LOGGER.debug("Getting all accountGroups lookup");
        HashMap<Integer, AccountGroupWithBitsModel> lookup = lookupsService.getAccountGroupsWithBits();
        CCATLogger.DEBUG_LOGGER.debug("Mapping all account groups lookup response");
        List<AccountGroupModel> mappedLookup = accountGroupResponseMapper.map(lookup, request.getServiceClassId());

        //current offering must be at index zero
        List<AccountGroupModel> responseList = new ArrayList<>();
        responseList.add(currentPlan);
        responseList.addAll(mappedLookup);

        CCATLogger.DEBUG_LOGGER.info("Finished serving get all account groups request");
        CCATLogger.DEBUG_LOGGER.debug("AccountGroupsService -> getAllAccountGroups() : Ended Successfully");
        return new GetAllAccountGroupsResponse(responseList);
    }

    public void updateAccountGroup(UpdateAccountGroupRequest request) throws GatewayException {

        CCATLogger.DEBUG_LOGGER.debug("AccountGroupsService -> updateAccountGroup() : Started");
        CCATLogger.DEBUG_LOGGER.info("Start serving update account group request");
        accountGroupsProxy.updateAccountGroup(request);
        CCATLogger.DEBUG_LOGGER.info("Finished serving update account group request");
        CCATLogger.DEBUG_LOGGER.debug("AccountGroupsService -> updateAccountGroup() : Ended Successfully");

    }
}
