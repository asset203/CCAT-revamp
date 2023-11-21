package com.asset.ccat.history_service.database.dao;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.asset.ccat.history_service.defines.DBStructs;
import com.asset.ccat.history_service.defines.Defines;
import com.asset.ccat.history_service.defines.ErrorCodes;
import com.asset.ccat.history_service.exceptions.HistoryException;
import com.asset.ccat.history_service.logger.CCATLogger;
import com.asset.ccat.rabbitmq.models.FootprintDetailsModel;

/**
 * @author Assem Hassan
 */
@Component
public class FootprintDetailsDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String insertFootprintDetailQuery;

    public int[][] insertFootPrintDetailsBatch(List<FootprintDetailsModel> footprintDetails) throws HistoryException {
        try {
            if (Objects.isNull(insertFootprintDetailQuery)) {
                StringBuilder query = new StringBuilder("");
                query.append("INSERT INTO ").append(DBStructs.TX_USER_FOOTPRINT_DETAILS.TABLE_NAME)
                        .append("(")
                        .append(DBStructs.TX_USER_FOOTPRINT.REQUEST_ID).append(", ")
                        .append(DBStructs.TX_USER_FOOTPRINT_DETAILS.PARAM_NAME).append(", ")
                        .append(DBStructs.TX_USER_FOOTPRINT_DETAILS.OLD_VALUE).append(", ")
                        .append(DBStructs.TX_USER_FOOTPRINT_DETAILS.NEW_VALUE)
                        .append(") VALUES(?,?,?,?)");
                insertFootprintDetailQuery = query.toString();
            }
            return jdbcTemplate.batchUpdate(
                    insertFootprintDetailQuery,
                    footprintDetails,
                    footprintDetails.size(),
                    (ps, argument) -> {
                        ps.setString(1, argument.getRequestId());
                        ps.setString(2, argument.getParamName());
                        ps.setString(3, argument.getOldValue());
                        ps.setString(4, argument.getNewValue());
                    });
        } catch (Exception ex) {
            CCATLogger.FOOTPRINT_LOGGER
                    .error("Database error occurred while executing insert [" + insertFootprintDetailQuery + "]");
            CCATLogger.ERROR_LOGGER
                    .error("Database error occurred while executing insert [" + insertFootprintDetailQuery + "]", ex);
            throw new HistoryException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }
}
