package com.asset.ccat.history_service.database.dao;

import com.asset.ccat.history_service.defines.DBStructs;
import com.asset.ccat.history_service.defines.Defines;
import com.asset.ccat.history_service.defines.ErrorCodes;
import com.asset.ccat.history_service.exceptions.HistoryException;
import com.asset.ccat.history_service.logger.CCATLogger;
import com.asset.ccat.rabbitmq.models.FootprintModel;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

/**
 * @author Assem Hassan
 */
@Component
public class FootprintDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String insertFootprintBatchQuery;

    public int[][] insertFootprintBatch(List<FootprintModel> footprintBatchList) throws HistoryException {
        int[][] result;
        try {
            if (Objects.isNull(insertFootprintBatchQuery)) {
                StringBuilder batchQuery = new StringBuilder("");
                batchQuery.append("INSERT INTO ")
                        .append(DBStructs.TX_USER_FOOTPRINT.TABLE_NAME).append("(")
                        .append(DBStructs.TX_USER_FOOTPRINT.REQUEST_ID).append(", ")
                        .append(DBStructs.TX_USER_FOOTPRINT.PAGE_NAME).append(", ")
                        .append(DBStructs.TX_USER_FOOTPRINT.TAB_NAME).append(", ")
                        .append(DBStructs.TX_USER_FOOTPRINT.ACTION_NAME).append(", ")
                        .append(DBStructs.TX_USER_FOOTPRINT.ACTION_TYPE).append(", ")
                        .append(DBStructs.TX_USER_FOOTPRINT.USERNAME).append(", ")
                        .append(DBStructs.TX_USER_FOOTPRINT.PROFILE_NAME).append(", ")
                        .append(DBStructs.TX_USER_FOOTPRINT.MSISDN).append(", ")
                        .append(DBStructs.TX_USER_FOOTPRINT.STATUS).append(", ")
                        .append(DBStructs.TX_USER_FOOTPRINT.ERROR_MESSAGE).append(", ")
                        .append(DBStructs.TX_USER_FOOTPRINT.ERROR_CODE).append(", ")
                        .append(DBStructs.TX_USER_FOOTPRINT.SESSION_ID).append(", ")
                        .append(DBStructs.TX_USER_FOOTPRINT.MACHINE_NAME).append(", ")
                        .append(DBStructs.TX_USER_FOOTPRINT.SEND_SMS).append(", ")
                        .append(DBStructs.TX_USER_FOOTPRINT.ID)
                        .append(") VALUES(")
                        .append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                insertFootprintBatchQuery = batchQuery.toString();
            }
            result = jdbcTemplate.batchUpdate(
                    insertFootprintBatchQuery,
                    footprintBatchList,
                    footprintBatchList.size(),
                    (ps, argument) -> {

                        ps.setString(1, argument.getRequestId());
                        ps.setString(2, argument.getPageName());
                        ps.setString(3, argument.getTabName());
                        ps.setString(4, argument.getActionName());
                        ps.setString(5, argument.getActionType());
                        ps.setString(6, argument.getUserName());
                        ps.setString(7, argument.getProfileName());
                        ps.setString(8, argument.getMsisdn());
                        ps.setString(9, argument.getStatus());
                        ps.setString(10, argument.getErrorMessage());
                        ps.setString(11, argument.getErrorCode());
                        ps.setString(12, argument.getSessionId());
                        ps.setString(13, argument.getMachineName());
                        ps.setObject(14, argument.getSendSms());
                        try {
                            ps.setInt(15, getNextId());
                        } catch (HistoryException ex) {
                            CCATLogger.FOOTPRINT_LOGGER
                                    .error("Database error occurred while executing batch insert [" + insertFootprintBatchQuery + "]");
                            CCATLogger.ERROR_LOGGER
                                    .error("Database error occurred while executing batch insert [" + insertFootprintBatchQuery + "]", ex);
                        }
                    });
        } catch (Exception ex) {
            CCATLogger.FOOTPRINT_LOGGER
                    .error("Database error occurred while executing batch insert [" + insertFootprintBatchQuery + "]");
            CCATLogger.ERROR_LOGGER
                    .error("Database error occurred while executing batch insert [" + insertFootprintBatchQuery + "]", ex);
            throw new HistoryException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
        return result;
    }

    private Integer getNextId() throws HistoryException {
        String retrieveNextIdQuery = "Select " + DBStructs.SEQUENCE.S_TX_USER_FOOTPRINT + ".NEXTVAL" + " from dual";
        try {
            final SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(retrieveNextIdQuery);
            if (sqlRowSet.next()) {
                return sqlRowSet.getInt(1);
            }
            return -1;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveNextIdQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveNextIdQuery, ex);
            throw new HistoryException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }
}
