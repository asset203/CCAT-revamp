package com.asset.ccat.procedureservice.managers;

import com.asset.ccat.procedureservice.configrations.Properties;
import com.asset.ccat.procedureservice.dto.models.datasource.PaymentGatewayDatasourceModel;
import com.asset.ccat.procedureservice.logger.CCATLogger;
import com.asset.ccat.procedureservice.utils.PasswordEncryptorUtil;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

@Component
public class PGDatasourceManager {

    private final DatasourceManager datasourceManager;
    private final Properties properties;
    private final PasswordEncryptorUtil passwordEncryptorUtil;

    public PGDatasourceManager(DatasourceManager datasourceManager, Properties properties, PasswordEncryptorUtil passwordEncryptorUtil) {
        this.datasourceManager = datasourceManager;
        this.properties = properties;
        this.passwordEncryptorUtil = passwordEncryptorUtil;
    }

    public HikariDataSource register() {
        CCATLogger.DEBUG_LOGGER.debug("Start Registering PG-datasource");
        try {
            PaymentGatewayDatasourceModel datasourceModel = retrieveProps();
            HikariDataSource hikariDataSource = datasourceManager.createDataSource(datasourceModel);
            hikariDataSource.setConnectionTimeout(30000);
            CCATLogger.DEBUG_LOGGER.debug("Registering PG-datasource Ended Successfully");
            return hikariDataSource;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Couldn't register PG datasource due to " + ex);
            CCATLogger.ERROR_LOGGER.error("Couldn't register PG datasource due to ", ex);
            throw ex;
        }
    }
    private PaymentGatewayDatasourceModel retrieveProps(){
        CCATLogger.DEBUG_LOGGER.debug("Start Retrieving PG-datasource required Data from database");
        String URL = properties.getVoucherPgDbUrl();
        String username = properties.getVoucherPgDbUsername();
        String password = passwordEncryptorUtil.decrypt(properties.getVoucherPgDbPassword(),"secrete");
        PaymentGatewayDatasourceModel pgDatasourceModel = new PaymentGatewayDatasourceModel(URL,username,password,null);
        CCATLogger.DEBUG_LOGGER.debug("PG-datasource required Data retrieved from database Successfully");
        return pgDatasourceModel;
    }
}
