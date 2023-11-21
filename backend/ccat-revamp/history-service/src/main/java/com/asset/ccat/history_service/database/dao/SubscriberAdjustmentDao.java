/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.history_service.database.dao;

import com.asset.ccat.history_service.defines.DBStructs;
import com.asset.ccat.history_service.defines.Defines;
import com.asset.ccat.history_service.defines.ErrorCodes;
import com.asset.ccat.history_service.exceptions.HistoryException;
import com.asset.ccat.history_service.logger.CCATLogger;
import com.asset.ccat.rabbitmq.models.TxAdjustmentModel;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author wael.mohamed
 */
@Repository
public class SubscriberAdjustmentDao {

    @Autowired
    JdbcTemplate jdbcTemplate;
    private String insertAdjustmentQuery;

    public boolean insertIntoAdjustment(String msisdn, String msisdnMod) throws HistoryException {
        CCATLogger.DEBUG_LOGGER.debug("Starting SubscriberAdjustmentDao - insertIntoAdjustment");
        try {
            if (insertAdjustmentQuery == null) {
                insertAdjustmentQuery
                        = "INSERT INTO "
                        + DBStructs.TX_SUBSCRIBER_ADJUSTMENTS.TABLE_NAME
                        + " ( "
                        + DBStructs.TX_SUBSCRIBER_ADJUSTMENTS.ID + " , "
                        + DBStructs.TX_SUBSCRIBER_ADJUSTMENTS.ADJ_DATE + " , "
                        + DBStructs.TX_SUBSCRIBER_ADJUSTMENTS.MSISDN + " , "
                        + DBStructs.TX_SUBSCRIBER_ADJUSTMENTS.MSISDN_MOD_X
                        + " ) "
                        + " VALUES ( " + DBStructs.SEQUENCE.S_TX_SUBSCRIBER_ADJUSTMENTS + ".nextVal , sysdate , ? , ? ) ";
            }
            CCATLogger.DEBUG_LOGGER.debug("insertAdjustmentQuery = " + insertAdjustmentQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending TransactionsDao - insertIntoAdjustment");
            return jdbcTemplate.update(insertAdjustmentQuery, msisdn, msisdnMod) != 0;

        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("Exception in insertIntoAdjustment \n" + e);
            CCATLogger.ERROR_LOGGER.error("Exception in insertIntoAdjustment ", e);
            throw new HistoryException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());

        }
    }

    public boolean insertAdjustmentBatch(List<TxAdjustmentModel> adjustments) throws HistoryException {
        CCATLogger.DEBUG_LOGGER.debug("Starting SubscriberAdjustmentDao - insertAdjustmentBatch");
        int[] rows;
        try {
            if (insertAdjustmentQuery == null) {
                insertAdjustmentQuery
                        = "INSERT INTO "
                        + DBStructs.TX_SUBSCRIBER_ADJUSTMENTS.TABLE_NAME
                        + " ( "
                        + DBStructs.TX_SUBSCRIBER_ADJUSTMENTS.ID + " , "
                        + DBStructs.TX_SUBSCRIBER_ADJUSTMENTS.ADJ_DATE + " , "
                        + DBStructs.TX_SUBSCRIBER_ADJUSTMENTS.MSISDN + " , "
                        + DBStructs.TX_SUBSCRIBER_ADJUSTMENTS.MSISDN_MOD_X
                        + " ) "
                        + " VALUES ( " + DBStructs.SEQUENCE.S_TX_SUBSCRIBER_ADJUSTMENTS + ".nextVal , sysdate , ? , ? ) ";
            }
            CCATLogger.DEBUG_LOGGER.debug("insertAdjustmentQuery = " + insertAdjustmentQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending SubscriberAdjustmentDao - insertAdjustmentBatch");
            rows = jdbcTemplate.batchUpdate(insertAdjustmentQuery, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, adjustments.get(i).getMsisdn());
                    ps.setString(2, adjustments.get(i).getMsisdn().substring(adjustments.get(i).getMsisdn().length() - 1));
                }

                @Override
                public int getBatchSize() {
                    return adjustments.size();
                }
            });
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("Exception in insertAdjustmentBatch while execute batch " + e);
            CCATLogger.ERROR_LOGGER.error("Exception in insertAdjustmentBatch while execute batch ", e);
            throw new HistoryException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
        return rows.length > 0;
    }

    public Integer getNumberOfTransactionsInTimePeriod(String msisdn, String fromDate, String toDate) throws HistoryException {
        CCATLogger.DEBUG_LOGGER.debug("Starting SubscriberAdjustmentDao - getNumberOfTransactionsInTimePeriod");
        StringBuilder getNumberOfTransactionsInTimePeriodQuery = new StringBuilder();
        try {
            getNumberOfTransactionsInTimePeriodQuery.append(" SELECT COUNT(*) FROM ");
            getNumberOfTransactionsInTimePeriodQuery.append(" ( ");
            getNumberOfTransactionsInTimePeriodQuery.append(" SELECT * FROM ");
            getNumberOfTransactionsInTimePeriodQuery.append(DBStructs.TX_SUBSCRIBER_ADJUSTMENTS.TABLE_NAME);
            getNumberOfTransactionsInTimePeriodQuery.append(" WHERE ");
            getNumberOfTransactionsInTimePeriodQuery.append(DBStructs.TX_SUBSCRIBER_ADJUSTMENTS.MSISDN);
            getNumberOfTransactionsInTimePeriodQuery.append(" = ? AND ");
            getNumberOfTransactionsInTimePeriodQuery.append(DBStructs.TX_SUBSCRIBER_ADJUSTMENTS.ADJ_DATE);
            getNumberOfTransactionsInTimePeriodQuery.append(" BETWEEN TO_DATE( ? ,'MM/dd/yyyy') AND TO_DATE( ? ,'MM/dd/yyyy')");
            getNumberOfTransactionsInTimePeriodQuery.append(" ) ");

            CCATLogger.DEBUG_LOGGER.debug("getNumberOfTransactionsInTimePeriodQuery = " + getNumberOfTransactionsInTimePeriodQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending SubscriberAdjustmentDao - getNumberOfTransactionsInTimePeriodQuery");

            return jdbcTemplate.queryForObject(getNumberOfTransactionsInTimePeriodQuery.toString(), Integer.class,
                    msisdn, fromDate, toDate);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("Exception in getNumberOfTransactionsInTimePeriodQuery \n" + e);
            CCATLogger.ERROR_LOGGER.error("Exception in getNumberOfTransactionsInTimePeriodQuery ", e);
            throw new HistoryException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());

        }
    }
}
