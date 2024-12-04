package com.asset.ccat.ods_service.managers;

import com.asset.ccat.ods_service.cache.CachedLookups;
import com.asset.ccat.ods_service.configurations.Properties;
import com.asset.ccat.ods_service.defines.ErrorCodes;
import com.asset.ccat.ods_service.exceptions.ODSException;
import com.asset.ccat.ods_service.logger.CCATLogger;
import com.asset.ccat.ods_service.models.DSSNodesModel;
import com.asset.ccat.ods_service.models.RoundRobin;
import com.asset.ccat.ods_service.models.ods_models.FlexShareHistoryNodeModel;
import com.asset.ccat.ods_service.models.ods_models.ODSNodesModel;
import com.asset.ccat.ods_service.utils.CryptoUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;
import java.util.*;

/**
 * @author assem.hassan
 */
@Component
public class DatasourceManager {

    private final Map<Integer, ODSNodesModel> cashedOdsNodesList = new HashMap<>();
    private final Map<Integer, DSSNodesModel> cashedDssNodesList = new HashMap<>();
    private final Map<Integer, FlexShareHistoryNodeModel> cachedFlexHistoryNodesList = new HashMap<>();
    public Map<String, Map<Integer, HikariDataSource>> activeDataSourcesMap = new HashMap<>();
    @Autowired
    CachedLookups cachedLookups;
    @Autowired
    CryptoUtils cryptoUtils;
    @Autowired
    Properties properties;

    @PreDestroy
    public void releaseEachNode() {
        unregisterDataSourceForAllNodes();
    }

    public void init() {
        this.activeDataSourcesMap.put("ODS_NODES", new HashMap<>());
        this.activeDataSourcesMap.put("DSS_NODES", new HashMap<>());
        this.activeDataSourcesMap.put("FLEX_HISTORY", new HashMap<>());
        registerODSDatasourceForEachNode();
        registerDSSDatasourceForEachNode();
        //registerFlexHistoryDatasourceForEachNode();
    }

    private void registerODSDatasourceForEachNode() {
        List<ODSNodesModel> tempList = cachedLookups.getODSNodes();
        CCATLogger.DEBUG_LOGGER.info("Start register cached nodes of the ODS ");

        for (ODSNodesModel currentODSNode : tempList) {
            try {
                ODSNodesModel oldModel = cashedOdsNodesList.get(currentODSNode.getId());
                if (Objects.isNull(oldModel) || !Objects.equals(oldModel, currentODSNode)) {
                    if (activeDataSourcesMap.get("ODS_NODES") != null && activeDataSourcesMap.get("ODS_NODES").get(currentODSNode.getId()) != null) {
                        HikariDataSource currentDataSource = activeDataSourcesMap.get("ODS_NODES").remove(currentODSNode.getId());
                        unregisterDataSource(currentDataSource);
                    }
                    HikariDataSource hikariDataSource = createDataSource(currentODSNode.getAddress(), currentODSNode.getSchema(), currentODSNode.getUserName(), currentODSNode.getPassword());
                    hikariDataSource.setConnectionTimeout(currentODSNode.getConnectionTimeout());
                    activeDataSourcesMap.get("ODS_NODES").put(currentODSNode.getId(), hikariDataSource);
                    CCATLogger.DEBUG_LOGGER.debug("End register cached nodes of the ods with id " + currentODSNode.getId());
                    cashedOdsNodesList.put(currentODSNode.getId(), currentODSNode);
                }
            } catch (Exception ex) {
                CCATLogger.DEBUG_LOGGER.info("Couldn't register ODS datasource " + currentODSNode.getId() + " due to " + ex);
                CCATLogger.ERROR_LOGGER.error("Couldn't register ODS datasource due to ", ex);
            }
        }
        CCATLogger.DEBUG_LOGGER.info("End register cached nodes of the ODS ");
    }

    private void registerDSSDatasourceForEachNode() {

        List<DSSNodesModel> tempList = cachedLookups.getDSSNodes();
        CCATLogger.DEBUG_LOGGER.info("Start register cached nodes of the DSS ");
        for (DSSNodesModel currentDSSNode : tempList) {
            try {
                DSSNodesModel oldModel = cashedDssNodesList.get(currentDSSNode.getId());
                if (Objects.isNull(oldModel) || !Objects.equals(oldModel, currentDSSNode)) {
                    if (activeDataSourcesMap.get("DSS_NODES").get(currentDSSNode.getId()) != null) {
                        HikariDataSource currentDataSource = activeDataSourcesMap.get("DSS_NODES").remove(currentDSSNode.getId());
                        unregisterDataSource(currentDataSource);
                    }
                    HikariDataSource hikariDataSource = createDataSource(currentDSSNode.getAddress(), currentDSSNode.getSchema(), currentDSSNode.getUserName(), currentDSSNode.getPassword());
                    hikariDataSource.setConnectionTimeout(currentDSSNode.getConnectionTimeout());
                    activeDataSourcesMap.get("DSS_NODES").put(currentDSSNode.getId(), hikariDataSource);
                    cashedDssNodesList.put(currentDSSNode.getId(), currentDSSNode);
                }
            } catch (Exception ex) {
                CCATLogger.DEBUG_LOGGER.info("Couldn't register DSS datasource due to " + ex);
                CCATLogger.ERROR_LOGGER.error("Couldn't register DSS datasource due to ", ex);
            }
        }
        CCATLogger.DEBUG_LOGGER.info("End register cached nodes of the DSS ");
    }

    private void registerFlexHistoryDatasourceForEachNode() {
        List<FlexShareHistoryNodeModel> tempList = cachedLookups.getFlexHistoryNodes();
        CCATLogger.DEBUG_LOGGER.info("Start register cached nodes of the FlexHistory ");
        for (FlexShareHistoryNodeModel flexShareHistoryNodeModel : tempList) {
            try {
                CCATLogger.DEBUG_LOGGER.info("End register cached nodes of the FlexHistory ");
                FlexShareHistoryNodeModel oldModel = cachedFlexHistoryNodesList.get(flexShareHistoryNodeModel.getId());
                if (Objects.isNull(oldModel) || !Objects.equals(oldModel, flexShareHistoryNodeModel)) {
                    if (activeDataSourcesMap.get("FLEX_HISTORY").get(flexShareHistoryNodeModel.getId()) != null) {
                        HikariDataSource currentDataSource = activeDataSourcesMap.get("FLEX_HISTORY").remove(flexShareHistoryNodeModel.getId());
                        unregisterDataSource(currentDataSource);
                    }
                    HikariDataSource hikariDataSource = createDataSource(flexShareHistoryNodeModel.getAddress(), flexShareHistoryNodeModel.getSchema(), flexShareHistoryNodeModel.getUsername(), flexShareHistoryNodeModel.getPassword());
                    hikariDataSource.setConnectionTimeout(flexShareHistoryNodeModel.getConnectionTimeout());
                    activeDataSourcesMap.get("FLEX_HISTORY").put(flexShareHistoryNodeModel.getId(), hikariDataSource);
                    cachedFlexHistoryNodesList.put(flexShareHistoryNodeModel.getId(), flexShareHistoryNodeModel);
                }
            } catch (Exception ex) {
                CCATLogger.DEBUG_LOGGER.info("Couldn't register FlexHistory datasource due to " + ex.getMessage());
                CCATLogger.ERROR_LOGGER.error("Couldn't register FlexHistory datasource due to ", ex);
            }
        }
        CCATLogger.DEBUG_LOGGER.info("End register cached nodes of the FlexHistory ");
    }

    private HikariDataSource createDataSource(String address, String schema, String username, String password) throws ODSException {
        HikariConfig hikariConfig = new HikariConfig();
        try {
            hikariConfig.setJdbcUrl(address);
            if (Objects.nonNull(schema) && !schema.isBlank()) {
                hikariConfig.setSchema(schema);
            }
            hikariConfig.setUsername(username);
            if (Objects.nonNull(password) && !password.trim().isEmpty()) {
                hikariConfig.setPassword(cryptoUtils.decrypt(password, properties.getEncryptionKey()));
            }
            hikariConfig.setDriverClassName("oracle.jdbc.driver.OracleDriver");
            hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
            hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
            hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            hikariConfig.setPoolName("HikariPool-" + username);
            hikariConfig.setAutoCommit(false);
            return new HikariDataSource(hikariConfig);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Couldn't create datasource due to " + ex);
            CCATLogger.ERROR_LOGGER.error("Couldn't create datasource due to ", ex);
            throw new ODSException(ErrorCodes.ERROR.INVALID_DATA_SOURCE);
        }
    }

    public HikariDataSource getHikariDataSource(String nodeType) {
        HikariDataSource hikariDataSource = null;
        Object[] hikariDataSourceArray = null;
        try {
            HashMap<Integer, HikariDataSource> dsMap = (HashMap<Integer, HikariDataSource>) activeDataSourcesMap.get(nodeType);
            if (Objects.nonNull(dsMap)) {
                hikariDataSourceArray = dsMap.values().toArray();
            }
            if (Objects.nonNull(hikariDataSourceArray) && hikariDataSourceArray.length > 0) {
                RoundRobin roundRobin = new RoundRobin(hikariDataSourceArray.length - 1);
                int randomSequence = roundRobin.newSequence();
                hikariDataSource = (HikariDataSource) hikariDataSourceArray[randomSequence];
                CCATLogger.DEBUG_LOGGER.debug("Calling StoredProcedure on hikariDataSource [" + hikariDataSource + "]");
            }
            CCATLogger.DEBUG_LOGGER.debug("ODS_NODES datasource : { DB_URL : {}  \nschema_name : {} \nuser_name : {} \nconnection_time_out : {} \npool_name : {} }",
                    hikariDataSource.getJdbcUrl(),
                    hikariDataSource.getSchema(),
                    hikariDataSource.getUsername(),
                    hikariDataSource.getConnectionTimeout(),
                    hikariDataSource.getPoolName()
            );
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception while creating hikariDS ", ex);
            CCATLogger.ERROR_LOGGER.error("Couldn't create new hikariDataSource ",  ex);
            throw ex;
        }
        return hikariDataSource;
    }

    public void unregisterDataSourceForAllNodes() {
        activeDataSourcesMap.forEach((nodeType, DataSourcesMap) -> {
            DataSourcesMap.forEach((nodeId, dataSource) -> {
                try {
                    if (Objects.nonNull(dataSource)) {
                        dataSource.close();
                    }
                } catch (Exception ex) {
                    CCATLogger.ERROR_LOGGER.error("Failed to close hikariDataSource with URL" + dataSource.getJdbcUrl());
                    CCATLogger.ERROR_LOGGER.debug("Failed to close hikariDataSource with URL" + dataSource.getJdbcUrl());

                }
            });
        });
        activeDataSourcesMap.clear();
    }

    private void unregisterDataSource(HikariDataSource dataSource) {
        try {
            if (Objects.nonNull(dataSource)) {
                CCATLogger.DEBUG_LOGGER.info("Start close old Datasource" + dataSource.getJdbcUrl());
                dataSource.close();
            }
        } catch (Exception ex) {
            CCATLogger.ERROR_LOGGER.info("Failed to close hikariDataSource with URL" + dataSource.getJdbcUrl());
            CCATLogger.ERROR_LOGGER.error("Failed to close hikariDataSource with URL" + dataSource.getJdbcUrl(), ex);
            throw ex;
        }
    }

    public void refreshDataSourceNodes() {

        List<ODSNodesModel> oldCachedList = new ArrayList<>(cashedOdsNodesList.values());
        if (!Objects.equals(oldCachedList, cachedLookups.getODSNodes())) {
            CCATLogger.DEBUG_LOGGER.info("start refresh ods nodes");
            registerODSDatasourceForEachNode();
        }

        List<DSSNodesModel> oldCachedDssList = new ArrayList<>(cashedDssNodesList.values());
        if (!Objects.equals(oldCachedDssList, cachedLookups.getDSSNodes())) {
            CCATLogger.DEBUG_LOGGER.info("start refresh dss nodes");
            registerDSSDatasourceForEachNode();
        }

        List<FlexShareHistoryNodeModel> oldCachedFlesShareList = new ArrayList<>(cachedFlexHistoryNodesList.values());
        if (!Objects.equals(oldCachedFlesShareList, cachedLookups.getFlexHistoryNodes())) {
            CCATLogger.DEBUG_LOGGER.info("start refresh flex history nodes");
            //registerFlexHistoryDatasourceForEachNode();
        }
    }
}
