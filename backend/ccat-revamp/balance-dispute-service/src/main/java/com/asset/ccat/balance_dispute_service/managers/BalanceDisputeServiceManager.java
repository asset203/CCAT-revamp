package com.asset.ccat.balance_dispute_service.managers;

import com.asset.ccat.balance_dispute_service.dto.models.BalanceDisputeInterfaceDataModel;
import com.asset.ccat.balance_dispute_service.exceptions.BalanceDisputeException;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class BalanceDisputeServiceManager {

    private final BalanceDisputeDatasourceManager balanceDisputeDatasourceManager;
    public static HikariDataSource BALANCE_DISPUTE_DATASOURCE;
    public static HashMap<Integer,BalanceDisputeInterfaceDataModel> BALANCE_DISPUTE_INTERFACE_DATA_MODEL_LIST;


    public BalanceDisputeServiceManager(BalanceDisputeDatasourceManager balanceDisputeDatasourceManager) {
        this.balanceDisputeDatasourceManager = balanceDisputeDatasourceManager;

    }

    @EventListener
    public void startUpEvent(ApplicationReadyEvent event) throws BalanceDisputeException {
        BALANCE_DISPUTE_DATASOURCE = balanceDisputeDatasourceManager.register();
        BALANCE_DISPUTE_INTERFACE_DATA_MODEL_LIST = balanceDisputeDatasourceManager.retrieveAllSPsData();

    }
}
