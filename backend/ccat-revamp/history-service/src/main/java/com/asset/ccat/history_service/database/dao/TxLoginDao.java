package com.asset.ccat.history_service.database.dao;

import com.asset.ccat.history_service.defines.DBStructs;
import com.asset.ccat.history_service.defines.Defines;
import com.asset.ccat.history_service.defines.ErrorCodes;
import com.asset.ccat.history_service.exceptions.HistoryException;
import com.asset.ccat.history_service.logger.CCATLogger;
import com.asset.ccat.rabbitmq.models.FootprintModel;
import com.asset.ccat.rabbitmq.models.TxLoginModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author assem.hassan
 */
@Component
public class TxLoginDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String insertTxLoginQuery;

    public boolean insertIntoTxLogin(TxLoginModel txLoginModel) throws HistoryException {
        CCATLogger.DEBUG_LOGGER.debug("Starting SubscriberAdjustmentDao - insertIntoAdjustment");
        try {
            if (insertTxLoginQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("INSERT INTO ").append(DBStructs.TX_LOGIN.TABLE_NAME)
                        .append("(")
                        .append(DBStructs.TX_LOGIN.MACHINE_NAME).append(", ")
                        .append(DBStructs.TX_LOGIN.DOMAIN_NAME).append(", ")
                        .append(DBStructs.TX_LOGIN.MESSAGE).append(", ")
                        .append(DBStructs.TX_LOGIN.STATUS).append(", ")
                        .append(DBStructs.TX_LOGIN.USER_ID)
                        .append(") VALUES(?,?,?,?,?)");
                insertTxLoginQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("insertTxLoginQuery = " + insertTxLoginQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending TxLoginDao - insertIntoTxLogin");

            return jdbcTemplate.update(insertTxLoginQuery, txLoginModel.getMachineName(),
                    txLoginModel.getDomainName(), txLoginModel.getMessage(),
                    txLoginModel.getStatus(), txLoginModel.getUserID()) != 0;

        } catch (Exception e) {
            CCATLogger.TX_LOGIN_LOGGER.error("Database error occurred while executing insert [" + insertTxLoginQuery + "]");
            CCATLogger.ERROR_LOGGER.error("Database error occurred while executing insert [" + insertTxLoginQuery + "]", e);
            throw new HistoryException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    public boolean insertTxLoginBatch(List<TxLoginModel> txLoginBatchList) throws HistoryException {
        CCATLogger.DEBUG_LOGGER.debug("Starting TxLoginDao - insertTxLoginBatch");
        int[] result;
        try {
            if (insertTxLoginQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("INSERT INTO ").append(DBStructs.TX_LOGIN.TABLE_NAME)
                        .append("(")
                        .append(DBStructs.TX_LOGIN.MACHINE_NAME).append(", ")
                        .append(DBStructs.TX_LOGIN.DOMAIN_NAME).append(", ")
                        .append(DBStructs.TX_LOGIN.MESSAGE).append(", ")
                        .append(DBStructs.TX_LOGIN.STATUS).append(", ")
                        .append(DBStructs.TX_LOGIN.USER_ID)
                        .append(") VALUES(?,?,?,?,?)");
                insertTxLoginQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("insertTxLoginQuery = " + insertTxLoginQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending TxLoginDao - insertTxLoginBatch");

            result = jdbcTemplate.batchUpdate(insertTxLoginQuery, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, txLoginBatchList.get(i).getMachineName());
                    ps.setString(2, txLoginBatchList.get(i).getDomainName());
                    ps.setString(3, txLoginBatchList.get(i).getMessage());
                    ps.setInt(4, 0);
                    ps.setInt(5, txLoginBatchList.get(i).getUserID());
                }

                @Override
                public int getBatchSize() {
                    return txLoginBatchList.size();
                }
            });

            return result.length != 0;
        } catch (Exception ex) {
            CCATLogger.TX_LOGIN_LOGGER.error("Database error occurred while executing batch insert [" + insertTxLoginQuery + "]");
            CCATLogger.ERROR_LOGGER.error("Database error occurred while executing batch insert [" + insertTxLoginQuery + "]", ex);
            throw new HistoryException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }

    }
}
