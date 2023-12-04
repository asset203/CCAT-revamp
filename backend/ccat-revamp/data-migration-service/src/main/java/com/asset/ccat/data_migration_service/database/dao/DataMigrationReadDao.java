package com.asset.ccat.data_migration_service.database.dao;

import com.asset.ccat.data_migration_service.cache.ReadQueriesCache;
import com.asset.ccat.data_migration_service.database.extractors.GenericExtractor;
import com.asset.ccat.data_migration_service.logger.CCATLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author Assem.Hassan
 */
@Repository
public class DataMigrationReadDao {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ReadQueriesCache readQueriesCache;

    public List<LinkedHashMap<String, Object>> extractGenericData(String tableName) {
        List<LinkedHashMap<String, Object>> result = null;
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            sql = "SELECT * FROM " + tableName;
            if (Objects.nonNull(readQueriesCache.getQueriesMap().get(tableName))) {
                result = jdbcTemplate.query(readQueriesCache.getQueriesMap().get(tableName), new GenericExtractor());
            } else {
                result = jdbcTemplate.query(sql, new GenericExtractor());
            }
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while retrieve data from " + tableName + " table, SQL=[ " + sql + " ]");
            CCATLogger.ERROR_LOGGER.error("Error while retrieve data from " + tableName + " table, SQL=[ " + sql + " ]", ex);
        } finally {
            CCATLogger.DEBUG_LOGGER.debug("Retrieve data from table " + tableName
                    + " in [" + (System.currentTimeMillis() - startTime) + "]");
        }
        return result;
    }
}
