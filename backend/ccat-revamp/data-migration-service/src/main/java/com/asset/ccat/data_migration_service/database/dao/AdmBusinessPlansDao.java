package com.asset.ccat.data_migration_service.database.dao;

import com.asset.ccat.data_migration_service.cache.InsertQueriesCache;
import com.asset.ccat.data_migration_service.logger.CCATLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Assem.Hassan
 */
@Repository
public class AdmBusinessPlansDao implements BaseMigrationWriteDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private InsertQueriesCache insertQueriesCache;

    @Override
    public void insertData(String tableName, List<Object[]> rows) {
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            sql = insertQueriesCache.getQueriesMap().get(tableName);

            jdbcTemplate.batchUpdate(
                    sql,
                    rows,
                   100,
                    (ps, row) -> {
                        for (int i = 1; i <= 5; i++) {
                            ps.setObject(i, row[i - 1]);
                        }
                        ps.setObject(6, row[6]);
                        ps.setObject(7, row[7]);
                    });
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while inserting into " + tableName + " table, SQL=[ " + sql + " ]");
            CCATLogger.ERROR_LOGGER.error("Error while inserting into" + tableName + " table, SQL=[ " + sql + " ]", ex);
        } finally {
            CCATLogger.DEBUG_LOGGER.debug("Inserting data into table " + tableName
                    + " in [" + (System.currentTimeMillis() - startTime) + "]");
        }
    }
}
