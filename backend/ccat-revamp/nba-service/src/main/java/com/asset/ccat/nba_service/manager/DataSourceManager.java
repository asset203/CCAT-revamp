/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.nba_service.manager;

import com.asset.ccat.nba_service.configurations.Properties;
import com.asset.ccat.nba_service.utils.PasswordEncryptorUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Mahmoud Shehab
 */
@Component
public class DataSourceManager {

    @Autowired
    Properties properties;

    private HikariDataSource hikariDataSource;
    private final PasswordEncryptorUtil passwordEncryptorUtil;

    public DataSourceManager(PasswordEncryptorUtil passwordEncryptorUtil) {
        this.passwordEncryptorUtil = passwordEncryptorUtil;
    }

    private void createDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(properties.getNbaDbUrl());
        if (properties.getNbaDbSchema() != null && !properties.getNbaDbSchema().isBlank()) {
            hikariConfig.setSchema(properties.getNbaDbSchema());
        }
        hikariConfig.setUsername(properties.getNbaDbUsername());

        hikariConfig.setPassword(passwordEncryptorUtil.decrypt(properties.getNbaDbPassword(),"secrete"));
        hikariConfig.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.setPoolName("HikariPool-NBA");
        hikariDataSource = new HikariDataSource(hikariConfig);
    }

    public void init() {
        //createDataSource();
    }

    public HikariDataSource retrieveDataSource() {
        return hikariDataSource;
    }

}
