package com.asset.usagesimulator.dao;

import com.asset.usagesimulator.loggers.AppLogger;
import com.asset.usagesimulator.models.BonusAndDedicatedAdjustmentL;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

@Repository
public class BonusAndDedicatedDao {
    private final JdbcTemplate jdbcTemplate;

    public BonusAndDedicatedDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int[] insertBonusAndDedicatedAdjustments(String msisdn, BonusAndDedicatedAdjustmentL l){
        String query = "insert into ADJ_FN (MOBILE_NUMBER, TYPE, TRX_DATE, DA_IDS, DA_AMOUNTS, TRANS_TYPE, TRANS_CODE) "
                 + "values(?, ?, SYSDATE, ?, ?, ?, ?)";
        try {
            return jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setObject(1, msisdn);
                    ps.setObject(2, 4);
                    ps.setObject(3, l.results.get(i).DA_IDS);
                    ps.setObject(4, l.results.get(i).DA_AMOUNTS);
                    ps.setObject(5, l.results.get(i).TRANS_TYPE);
                    ps.setObject(6, l.results.get(i).TRANS_CODE);
                }

                @Override
                public int getBatchSize() {
                    return l.results.size();
                }
            });
        } catch (Exception ex){
            AppLogger.DEBUG_LOGGER.error("DB_Exception occurred: ", ex);
            ex.printStackTrace();
            return new int[0];
        }
    }
}
