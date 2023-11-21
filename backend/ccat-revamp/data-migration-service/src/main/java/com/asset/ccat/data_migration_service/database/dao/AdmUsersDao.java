package com.asset.ccat.data_migration_service.database.dao;

import com.asset.ccat.data_migration_service.cache.InsertQueriesCache;
import com.asset.ccat.data_migration_service.logger.CCATLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

/**
 * @author Assem.Hassan
 */
@Repository
public class AdmUsersDao implements BaseMigrationWriteDao {

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
                        ps.setObject(1, row[0]);
                        ps.setObject(2, row[1]);
                        ps.setObject(3, row[2]);
                        ps.setObject(4, row[3]);
                        ps.setObject(5, row[4]);
                        ps.setObject(6, row[6]);
                        ps.setObject(7, row[7]);
                        ps.setObject(8, row[8]);
                        ps.setObject(9, row[9]);
                        ps.setObject(10, ((String) row[10]).split(" ")[0]);
                        ps.setObject(11, ((String) row[11]).split(" ")[0]);
                        if ("null".equals(row[12])) {
                            ps.setNull(12, Types.DATE);
                        } else {
                            ps.setObject(12, ((String) row[12]).split(" ")[0]);
                        }
                    });
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while inserting into " + tableName + " table, SQL=[ " + sql + " ]");
            CCATLogger.ERROR_LOGGER.error("Error while inserting into " + tableName + " table, SQL=[ " + sql + " ]", ex);
        } finally {
            CCATLogger.DEBUG_LOGGER.debug("Inserting data into table " + tableName
                    + " in [" + (System.currentTimeMillis() - startTime) + "]");
        }
    }
}
