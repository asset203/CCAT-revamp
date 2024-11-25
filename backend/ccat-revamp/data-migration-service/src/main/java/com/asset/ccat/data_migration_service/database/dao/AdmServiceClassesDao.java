package com.asset.ccat.data_migration_service.database.dao;

import com.asset.ccat.data_migration_service.cache.InsertQueriesCache;
import com.asset.ccat.data_migration_service.logger.CCATLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Assem.Hassan
 */
@Repository
public class AdmServiceClassesDao implements BaseMigrationWriteDao {

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
            List<Object[]> sortedRows = rows.stream()
                    .sorted(Comparator.comparingInt(row -> Integer.parseInt((String) row[0])))
                    .collect(Collectors.toList());

            jdbcTemplate.batchUpdate(
                    sql,
                    sortedRows,
                    100,
                    (ps, row) -> {
                        ps.setObject(1, row[1]);
                        ps.setObject(2, row[2]);
                        ps.setObject(3, row[7]);
                        ps.setObject(4, row[8]);
                        if ("null".equals(row[10])) {
                            ps.setNull(5, Types.INTEGER);

                        } else {
                            ps.setObject(5, row[10]);
                        }
                        ps.setObject(6, row[11]);
                        ps.setObject(7, row[12]);
                        ps.setObject(8, row[13]);
                        ps.setObject(9, row[14]);
                        if ("null".equals(row[16])) {
                            ps.setNull(10, Types.INTEGER);

                        } else {
                            ps.setObject(10, row[16]);
                        }

                        ps.setObject(11, row[17]);

                        if ("null".equals(row[18])) {
                            ps.setNull(12, Types.INTEGER);

                        } else {
                            ps.setObject(12, row[18]);
                        }
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
