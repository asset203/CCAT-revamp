package com.asset.ccat.dynamic_page.manager;

import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DynamicPageConnectionManager {

    private static Map<String, HikariDataSource> dataSourceMap;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        CCATLogger.DEBUG_LOGGER.info("Intitializing Database Connection Manager...");
        dataSourceMap = new ConcurrentHashMap<>();
    }

//    public synchronized Connection getConnection(String dbURL, String userName, String password) throws DynamicPageException {
//        Connection conn;
//        CCATLogger.DEBUG_LOGGER.debug("Start getDPDBConnection () | DynamicPageConnectionManager");
//        CCATLogger.DEBUG_LOGGER.debug("Getting Dynamic Page Database Connection URL: " + dbURL + "UserName: " + userName + "Password:*****");
//        try {
//            long t1 = System.currentTimeMillis();
//            conn = createDataSource(dbURL, userName, password).getConnection();
//            CCATLogger.DEBUG_LOGGER.debug("Dynamic Page Database Connection retrieved in "
//                    + (System.currentTimeMillis() - t1) + "ms");
//        } catch (Exception ex) {
//            CCATLogger.DEBUG_LOGGER.error("Exception in getDPDBConnection() Ex=", ex);
//            CCATLogger.ERROR_LOGGER.error("Exception in getDPDBConnection() Ex=", ex);
//            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR);
//        }
//        CCATLogger.DEBUG_LOGGER.debug("End getDPDBConnection () | DynamicPageConnectionManager ");
//
//        return conn;
//    }

//    public static Connection getConnection(String dsName) throws DynamicPageException {
//        Connection conn;
//        HikariDataSource dataSource = null;
//        CCATLogger.DEBUG_LOGGER.debug("Start getDPDSConnection () | DynamicPageConnectionManager ");
//        CCATLogger.DEBUG_LOGGER.debug("Getting Dynamic Page Datasource Connection , DataSource name: " + dsName);
//        try {
//            if (dataSourceMap != null && dataSourceMap.containsKey(dsName)) {
//                dataSource = dataSourceMap.get(dsName);
//            }
//            if (dataSource == null) {
//                //todo
//                //dataSourceMap.put(dsName, dataSource);
//            }
//            long t1 = System.currentTimeMillis();
//            conn = dataSource.getConnection();
//            CCATLogger.DEBUG_LOGGER.debug("Dynamic Page Datasource Connection retrieved in "
//                    + (System.currentTimeMillis() - t1) + "ms");
//        } catch (Exception ex) {
//            CCATLogger.DEBUG_LOGGER.error("Exception in getDPDSConnection() Ex=", ex);
//            CCATLogger.ERROR_LOGGER.error("Exception in getDPDSConnection() Ex=", ex);
//            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR);
//        }
//        CCATLogger.DEBUG_LOGGER.debug("End getDPDSConnection () | DynamicPageConnectionManager");
//
//        return conn;
//    }

    public synchronized HikariDataSource getDataSource(String address, String username, String password, String extraConfig) {
        HikariDataSource dataSource = dataSourceMap.get(address);
        if (!isDataSourceAlive(dataSource)) {
            createDataSource(address, username, password, extraConfig);
        }
        return dataSourceMap.get(address);
    }

    public void testDBConnection(String url, String username, String password) throws DynamicPageException {
        Connection conn = null;
        try {
            CCATLogger.DEBUG_LOGGER.info("Start testing DB connection for [" + url + "]");
            conn = DriverManager.getConnection(url, username, password);
            if (conn == null) {
                CCATLogger.DEBUG_LOGGER.info("Failed to get DB connection");
                throw new DynamicPageException(ErrorCodes.ERROR.DB_CONN_TEST_FAIL, Defines.SEVERITY.VALIDATION, "");
            }
            CCATLogger.DEBUG_LOGGER.info("Testing DB connection ended with success");
        } catch (SQLException e) {
            CCATLogger.DEBUG_LOGGER.info("Failed to get DB Connection");
            throw new DynamicPageException(ErrorCodes.ERROR.DB_CONN_TEST_FAIL, Defines.SEVERITY.VALIDATION, e.getMessage());
        } finally {
            closeResource(conn);
        }
    }

    private void closeResource(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                CCATLogger.ERROR_LOGGER.error("Failed to close Connection", ex);
            }
        }
    }

    private boolean isDataSourceAlive(HikariDataSource dataSource) {
        if (dataSource == null || dataSource.isClosed()) {
            return false;
        } else {
            return true;
        }
    }

    public synchronized void createDataSource(String address, String username, String password, String extraConfig) {
        try {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(address);
            hikariConfig.setUsername(username);
            hikariConfig.setPassword(password);
            hikariConfig.setDriverClassName("oracle.jdbc.driver.OracleDriver");
            hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
            hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
            hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            hikariConfig.setConnectionTimeout(30000);
            hikariConfig.setIdleTimeout(1);
            hikariConfig.setMaximumPoolSize(10);
            hikariConfig.setPoolName("HikariPool-" + username);
            hikariConfig.setAutoCommit(false);
            if (extraConfig != null && !extraConfig.isBlank()) {
                List<Map> extraConfigMap = parseExtraConfigurations(extraConfig);
                if (extraConfig != null && !extraConfigMap.isEmpty()) {
                    extraConfigMap.forEach((map) -> {
                        if (!map.entrySet().iterator().hasNext()) {
                            return;
                        }
                        Map.Entry entry = (Map.Entry) map.entrySet().iterator().next();
                        hikariConfig.addDataSourceProperty((String) entry.getKey(), (String) entry.getValue());
                    });
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("Extra Configuration [" + extraConfig + "] parsing failed, using default datasource configurations");
                }
            } else {
                CCATLogger.DEBUG_LOGGER.debug("No extra configurations is defined for datasource, using default datasource configurations");
            }
            HikariDataSource dataSource = new HikariDataSource(hikariConfig);
            dataSourceMap.put(address, dataSource);
        } catch (Throwable th) {
            CCATLogger.DEBUG_LOGGER.debug("Failed to create datasource with address " +
                    "[" + address + "], " + th.getMessage());
            CCATLogger.DEBUG_LOGGER.error("Failed to create datasource with address " +
                    "[" + address + "]", th);
        }
    }

    public synchronized void destroyDataSource(String address) {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Start destroying datasource [" + address + "]");
            if (isDataSourceAlive(dataSourceMap.get(address))) {
                dataSourceMap.get(address).close();
            }
            CCATLogger.DEBUG_LOGGER.debug("Finished destroying datasource [" + address + "]");
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("Failed to destroy datasource with address [" + address + "], " + ex.getMessage());
            CCATLogger.DEBUG_LOGGER.error("Failed to destroy datasource with address [" + address + "]", ex);
        }
    }

    public synchronized void updateDataSource(String oldDS, String address, String username, String password, String extraConfig) {
        CCATLogger.DEBUG_LOGGER.debug("Destroying datasource with address [" + oldDS + "]");
        destroyDataSource(oldDS);
        CCATLogger.DEBUG_LOGGER.debug("Creating datasource with address [" + address + "]");
        createDataSource(address, username, password, extraConfig);
    }

    @PreDestroy
    public void shutdown() {
        CCATLogger.DEBUG_LOGGER.info("Shutting down Database Connection Manager...");
        if (dataSourceMap != null && !dataSourceMap.isEmpty()) {
            dataSourceMap.forEach((address, datasource) -> {
                destroyDataSource(address);
            });
        }
    }

    public List<Map> parseExtraConfigurations(String extraConfigurations) {
        CCATLogger.DEBUG_LOGGER.debug("Start preparing extra configurations map");
        List<Map> configurationsMap = null;
        try {
            configurationsMap = objectMapper.readValue(extraConfigurations, new TypeReference<List<Map>>() {
            });
        } catch (JsonProcessingException e) {
            CCATLogger.DEBUG_LOGGER.debug("Failed to parse extraConfigurations string to map >> " + e.getMessage());
            CCATLogger.DEBUG_LOGGER.error("Failed to parse extraConfigurations string to map >> " + e.getMessage(), e);
            CCATLogger.DEBUG_LOGGER.debug("Request extraConfigurations will be ignored");
        }
        return configurationsMap;
    }
}
