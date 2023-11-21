package com.asset.ccat.balance_dispute_service.managers;

import com.asset.ccat.balance_dispute_service.configrations.Properties;
import com.asset.ccat.balance_dispute_service.dto.models.datasource.BasicDataSourceModel;
import com.asset.ccat.balance_dispute_service.logger.CCATLogger;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DatasourceManager {

    private final Properties properties;

    public DatasourceManager(Properties properties) {
        this.properties = properties;
    }

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
        hikariConfig.setMaximumPoolSize(properties.getHikariMaximumPoolSize());
        hikariConfig.setAutoCommit(false);
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        CCATLogger.DEBUG_LOGGER.debug("DatasourceManager -> createDataSource() : Ended Successfully");
        return dataSource ;
    }

}
