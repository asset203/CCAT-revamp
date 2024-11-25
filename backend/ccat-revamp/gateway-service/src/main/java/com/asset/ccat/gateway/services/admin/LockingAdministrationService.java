/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.services.admin;

import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.locking_admin.LockingAdministrationRequest;
import com.asset.ccat.gateway.models.responses.admin.locking_admin.GetAllLockingAdministrationsResponse;
import com.asset.ccat.gateway.redis.model.LockingAdministration;
import com.asset.ccat.gateway.redis.repository.AccountHistoryRepository;
import com.asset.ccat.gateway.redis.repository.LockingAdministrationRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.asset.ccat.gateway.services.AccountHistoryService;
import com.asset.ccat.gateway.services.BalanceDisputeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wael.mohamed
 */
@Service
public class LockingAdministrationService {

    @Autowired
    private Properties properties;

    @Autowired
    private LockingAdministrationRepository repository;

    @Autowired
    private AccountHistoryService accountHistoryService;

    @Autowired
    private BalanceDisputeService balanceDisputeService;

    public LockingAdministration isAdministrationLocking(String msisdn) {
        return repository.findById(msisdn).orElse(null);
    }

    public void LockAdministration(LockingAdministrationRequest request) throws GatewayException {
        LockingAdministration administration = new LockingAdministration(
                request.getMsisdn(),
                request.getUsername(),
                request.getDate()
        );
        //time live for each record stored in redis based on token time
        administration.setTimeToLive(properties.getAccessTokenValidity());
        CCATLogger.DEBUG_LOGGER.debug("Start locking subscriber for time = {} ms", administration.getTimeToLive());
        LockingAdministration lockedBy = isAdministrationLocking(administration.getMsisdn());
        CCATLogger.DEBUG_LOGGER.debug("Previously Locked By: {}", lockedBy);
        if (lockedBy != null && !Objects.equals(lockedBy.getUsername(), request.getUsername())) {
            throw new GatewayException(ErrorCodes.ERROR.IS_LOCKED, null, administration.getMsisdn(), lockedBy.getUsername());
        }
        repository.save(administration);
    }

    public void unLockAdministration(String msisdn) {
        repository.deleteById(msisdn);
        accountHistoryService.deleteSubscriberHistory(msisdn);
        balanceDisputeService.deleteBalanceDisputeReport(msisdn);
    }

    public GetAllLockingAdministrationsResponse getAllLockingAdministrations() {
        List<LockingAdministration> lockingList = new ArrayList<>();
        repository.findAll().forEach(lockingList::add);
        lockingList.removeAll(Collections.singleton(null));
        return new GetAllLockingAdministrationsResponse(lockingList);
    }
}
