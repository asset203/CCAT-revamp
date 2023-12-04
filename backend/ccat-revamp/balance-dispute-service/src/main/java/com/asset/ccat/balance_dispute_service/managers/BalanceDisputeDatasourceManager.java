package com.asset.ccat.balance_dispute_service.managers;

import com.asset.ccat.balance_dispute_service.configrations.Properties;
import com.asset.ccat.balance_dispute_service.database.dao.BalanceDisputeDao;
import com.asset.ccat.balance_dispute_service.dto.models.BalanceDisputeInterfaceDataModel;
import com.asset.ccat.balance_dispute_service.dto.models.datasource.BalanceDisputeDatasourceModel;
import com.asset.ccat.balance_dispute_service.exceptions.BalanceDisputeException;
import com.asset.ccat.balance_dispute_service.utils.PasswordEncryptorUtil;


import com.asset.ccat.balance_dispute_service.logger.CCATLogger;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class BalanceDisputeDatasourceManager {

    private final DatasourceManager datasourceManager;
    private final BalanceDisputeDao balanceDisputeDao;
    private final Properties properties;
    private final PasswordEncryptorUtil passwordEncryptorUtil;

    public BalanceDisputeDatasourceManager(DatasourceManager datasourceManager, BalanceDisputeDao balanceDisputeDao, Properties properties, PasswordEncryptorUtil passwordEncryptorUtil) {
        this.datasourceManager = datasourceManager;
        this.balanceDisputeDao = balanceDisputeDao;
        this.properties = properties;
        this.passwordEncryptorUtil = passwordEncryptorUtil;
    }

    public HikariDataSource register() {
        CCATLogger.DEBUG_LOGGER.debug("Start Registering Balance Dispute datasource");
        try {
            BalanceDisputeDatasourceModel datasourceModel = retrieveProps();
            HikariDataSource hikariDataSource = datasourceManager.createDataSource(datasourceModel);
            hikariDataSource.setConnectionTimeout(30000);
            CCATLogger.DEBUG_LOGGER.debug("Registering Balance Dispute datasource Ended Successfully");
            return hikariDataSource;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Couldn't register Balance Dispute datasource due to " + ex);
            CCATLogger.ERROR_LOGGER.error("Couldn't register Balance Dispute datasource due to ", ex);
            throw ex;
        }
    }
    private BalanceDisputeDatasourceModel retrieveProps(){
        CCATLogger.DEBUG_LOGGER.debug("Start Retrieving Balance Dispute datasource required Data from database");
        String URL = properties.getBalanceDisputeDatabaseURL();
        String username = properties.getBalanceDisputeDatabaseUsername();
        String password = properties.getBalanceDisputeDatabasePassword();
        BalanceDisputeDatasourceModel pgDatasourceModel = new BalanceDisputeDatasourceModel(URL,username,password,null);
        CCATLogger.DEBUG_LOGGER.debug("Balance Dispute datasource required Data retrieved from database Successfully");
        return pgDatasourceModel;
    }

    public HashMap<Integer,BalanceDisputeInterfaceDataModel> retrieveAllSPsData() throws BalanceDisputeException {
        List<BalanceDisputeInterfaceDataModel> balanceDisputeInterfaceDataModelList = balanceDisputeDao.retrieveAllSPsData();
        HashMap<Integer,BalanceDisputeInterfaceDataModel> map =  new HashMap<>();
        for (BalanceDisputeInterfaceDataModel model: balanceDisputeInterfaceDataModelList){
            map.put(model.getId(),model);
        }
        return map;
    }
}
