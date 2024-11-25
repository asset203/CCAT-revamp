package com.asset.ccat.procedureservice.managers;

import com.asset.ccat.procedureservice.configrations.Properties;
import com.asset.ccat.procedureservice.dto.models.datasource.BasicDataSourceModel;
import com.asset.ccat.procedureservice.logger.CCATLogger;
import com.asset.ccat.procedureservice.dto.models.datasource.PaymentGatewayDatasourceModel;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DatasourceManager {

    public HikariDataSource createDataSource(BasicDataSourceModel dataSourceModel) {
        CCATLogger.DEBUG_LOGGER.debug("DatasourceManager -> createDataSource() : Started With datasource model :"+dataSourceModel);
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(dataSourceModel.getURL());
        if (Objects.nonNull(dataSourceModel.getSchema()) && !dataSourceModel.getSchema().isBlank()) {
            hikariConfig.setSchema(dataSourceModel.getSchema());
        }
        hikariConfig.setUsername(dataSourceModel.getUsername());
        hikariConfig.setPassword(dataSourceModel.getPassword());
        hikariConfig.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        hikariConfig.setPoolName("HikariPool-" + dataSourceModel.getUsername());
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setAutoCommit(false);
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        CCATLogger.DEBUG_LOGGER.debug("DatasourceManager -> createDataSource() : Ended Successfully");
        return dataSource ;
    }

}
