package com.asset.ccat.procedureservice.managers;

import com.asset.ccat.procedureservice.configrations.Properties;
import com.asset.ccat.procedureservice.dto.models.datasource.FlexShareDatasourceModel;
import com.asset.ccat.procedureservice.logger.CCATLogger;
import com.asset.ccat.procedureservice.utils.PasswordEncryptorUtil;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

@Component
public class FlexShareDatasourceManager {

    private final Properties properties;
    private final DatasourceManager datasourceManager;
    private final PasswordEncryptorUtil passwordEncryptorUtil;

    public FlexShareDatasourceManager(Properties properties, DatasourceManager datasourceManager, PasswordEncryptorUtil passwordEncryptorUtil) {
        this.properties = properties;
        this.datasourceManager = datasourceManager;
        this.passwordEncryptorUtil = passwordEncryptorUtil;
    }

    public HikariDataSource register() {
        CCATLogger.DEBUG_LOGGER.debug("Start Registering Flex-share-datasource");
        try {
            FlexShareDatasourceModel datasourceModel = retrieveProps();
            HikariDataSource hikariDataSource = datasourceManager.createDataSource(datasourceModel);
            hikariDataSource.setConnectionTimeout(30000);
            CCATLogger.DEBUG_LOGGER.debug("Registering Flex-share-datasource Ended Successfully");
            return hikariDataSource;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Couldn't register Flex-share datasource due to " + ex);
            CCATLogger.ERROR_LOGGER.error("Couldn't register Flex-share datasource due to ", ex);
            throw ex;
        }
    }
    private FlexShareDatasourceModel retrieveProps(){
        CCATLogger.DEBUG_LOGGER.debug("Start Retrieving Flex-share-datasource required Data from database");
        String URL = properties.getVoucherPgDbUrl();
        String username = properties.getVoucherPgDbUsername();
        String password = passwordEncryptorUtil.decrypt(properties.getVoucherPgDbPassword(),"secrete");
        FlexShareDatasourceModel flexShareDatasourceModel = new FlexShareDatasourceModel(URL,username,password,null);
        CCATLogger.DEBUG_LOGGER.debug("Flex-share-datasource required Data retrieved from database Successfully");
        return flexShareDatasourceModel;
    }
}

