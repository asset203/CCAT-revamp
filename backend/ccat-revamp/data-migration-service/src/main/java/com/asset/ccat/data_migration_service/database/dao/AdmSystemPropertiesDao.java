package com.asset.ccat.data_migration_service.database.dao;

import com.asset.ccat.data_migration_service.cache.UpdateQueriesCache;
import com.asset.ccat.data_migration_service.configurations.Properties;
import com.asset.ccat.data_migration_service.logger.CCATLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Assem.Hassan
 */
@Repository
public class AdmSystemPropertiesDao implements BaseMigrationWriteDao {

    @Autowired
    Properties properties;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UpdateQueriesCache updateQueriesCache;

    @Override
    public void insertData(String tableName, List<Object[]> rows) {
        List<Object[]> filteredRows = new ArrayList<>();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            sql = updateQueriesCache.getQueriesMap().get(tableName);
            Set<String> codesSet = Arrays.stream(properties.getAdmSystemPropertiesCodes().split(",")).collect(Collectors.toSet());
            filteredRows = rows.stream()
                    .filter(row -> codesSet.contains(row[0].toString()))
                    .collect(Collectors.toList());

            jdbcTemplate.batchUpdate(
                    sql,
                    filteredRows,
                    100,
                    (ps, row) -> {
                        ps.setObject(1, row[1]);
                        ps.setObject(2, row[0]);
                        ps.setObject(3, properties.getAdmSystemPropertiesProfile());
                        ps.setObject(4, properties.getAdmSystemPropertiesLabel());
                    });
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while updating " + tableName + " table, SQL=[ " + sql + " ]");
            CCATLogger.ERROR_LOGGER.error("Error while updating " + tableName + " table, SQL=[ " + sql + " ]", ex);
        } finally {
            CCATLogger.DEBUG_LOGGER.debug("Updating data in table " + tableName
                    + " in [" + (System.currentTimeMillis() - startTime) + "]");
        }
    }
}
