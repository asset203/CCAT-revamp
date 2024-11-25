package com.asset.ccat.history_service.database.dao;


import com.asset.ccat.history_service.database.extractors.FootPrintReportExtractor;
import com.asset.ccat.history_service.defines.DBStructs;

import com.asset.ccat.history_service.defines.ErrorCodes;
import com.asset.ccat.history_service.exceptions.HistoryException;
import com.asset.ccat.history_service.logger.CCATLogger;
import com.asset.ccat.history_service.models.requests.foot_print.GetFootPrintReportRequest;
import com.asset.ccat.rabbitmq.models.FootprintModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.HashMap;
import java.util.Objects;


/**
 * @author Assem.Hassan
 */
@Repository
public class FootPrintReportDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private FootPrintReportExtractor footPrintReportExtractor;

    public HashMap<String, FootprintModel> getFootPrintReport(GetFootPrintReportRequest getFootPrintReportRequest) throws HistoryException {
        StringBuilder getFootPrintReportQuery = new StringBuilder();
        boolean whereCondition = false;
        Long dateFrom = getFootPrintReportRequest.getSearchFootPrintReport().getDateFrom();
        Long dateTo = getFootPrintReportRequest.getSearchFootPrintReport().getDateTo();
        try {
            getFootPrintReportQuery.append(" SELECT ")
                    .append(DBStructs.TX_USER_FOOTPRINT.TABLE_NAME).append(".*")
                    .append(",")
                    .append(DBStructs.TX_USER_FOOTPRINT_DETAILS.TABLE_NAME).append(".*")
                    .append(" FROM ")
                    .append(DBStructs.TX_USER_FOOTPRINT.TABLE_NAME)
                    .append(" LEFT JOIN ")
                    .append(DBStructs.TX_USER_FOOTPRINT_DETAILS.TABLE_NAME)
                    .append(" ON ")
                    .append(DBStructs.TX_USER_FOOTPRINT.TABLE_NAME).append(".")
                    .append(DBStructs.TX_USER_FOOTPRINT.REQUEST_ID)
                    .append("=")
                    .append(DBStructs.TX_USER_FOOTPRINT_DETAILS.TABLE_NAME).append(".")
                    .append(DBStructs.TX_USER_FOOTPRINT_DETAILS.REQUEST_ID);

            if ((Objects.nonNull(getFootPrintReportRequest.getSearchFootPrintReport().getMsisdn())
                    && !("".equalsIgnoreCase(getFootPrintReportRequest.getSearchFootPrintReport().getMsisdn())))) {
                getFootPrintReportQuery.append(" WHERE ")
                        .append(DBStructs.TX_USER_FOOTPRINT.TABLE_NAME).append(".")
                        .append(DBStructs.TX_USER_FOOTPRINT.MSISDN).append(" = '")
                        .append(getFootPrintReportRequest.getSearchFootPrintReport().getMsisdn()).append("'");
                whereCondition = true;
            }

            if ((Objects.nonNull(getFootPrintReportRequest.getSearchFootPrintReport().getUserName())
                    && !("".equalsIgnoreCase(getFootPrintReportRequest.getSearchFootPrintReport().getUserName())))) {
                if (!whereCondition) {
                    getFootPrintReportQuery.append(" WHERE ");
                    whereCondition = true;
                } else {
                    getFootPrintReportQuery.append(" AND ");
                }
                getFootPrintReportQuery.append(" LOWER(")
                        .append(DBStructs.TX_USER_FOOTPRINT.TABLE_NAME).append(".")
                        .append(DBStructs.TX_USER_FOOTPRINT.USERNAME)
                        .append(") like LOWER('%")
                        .append(getFootPrintReportRequest.getSearchFootPrintReport().getUserName())
                        .append("%')");
            }

            if ((Objects.nonNull(dateFrom) && Objects.nonNull(dateTo))) {
                if (!whereCondition) {
                    getFootPrintReportQuery.append(" WHERE ");
                } else {
                    getFootPrintReportQuery.append(" AND ");
                }
                getFootPrintReportQuery.append("  TRUNC(")
                        .append(DBStructs.TX_USER_FOOTPRINT.TABLE_NAME).append(".")
                        .append(DBStructs.TX_USER_FOOTPRINT.ACTION_TIME)
                        .append(") ")
                        .append("BETWEEN  TO_DATE('")
                        .append(new Date(dateFrom))
                        .append("','YYYY/MM/DD')")
                        .append(" AND  TO_DATE('")
                        .append(new Date(dateTo))
                        .append("','YYYY/MM/DD')");
            } else if ((Objects.nonNull(dateFrom) && Objects.isNull(dateTo))) {
                final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
                Date threeDayBefore = new Date(dateFrom - MILLIS_IN_A_DAY);
                if (!whereCondition) {
                    getFootPrintReportQuery.append(" WHERE ");
                } else {
                    getFootPrintReportQuery.append(" AND ");
                }
                getFootPrintReportQuery.append("  TRUNC(")
                        .append(DBStructs.TX_USER_FOOTPRINT.TABLE_NAME).append(".")
                        .append(DBStructs.TX_USER_FOOTPRINT.ACTION_TIME)
                        .append(") ")
                        .append("BETWEEN  TO_DATE('")
                        .append(threeDayBefore)
                        .append("','YYYY/MM/DD')")
                        .append(" AND  TO_DATE('")
                        .append(new Date(dateFrom))
                        .append("','YYYY/MM/DD')");
            } else if ((Objects.isNull(dateFrom) && Objects.nonNull(dateTo))) {
                if (!whereCondition) {
                    getFootPrintReportQuery.append(" WHERE ");
                } else {
                    getFootPrintReportQuery.append(" AND ");
                }
                getFootPrintReportQuery.append("  TRUNC(")
                        .append(DBStructs.TX_USER_FOOTPRINT.TABLE_NAME).append(".")
                        .append(DBStructs.TX_USER_FOOTPRINT.ACTION_TIME)
                        .append(") <= TO_DATE('")
                        .append(new Date(dateTo))
                        .append("','YYYY/MM/DD')");
            }
            getFootPrintReportQuery.append(" ORDER BY ")
                    .append(DBStructs.TX_USER_FOOTPRINT.TABLE_NAME)
                    .append(".")
                    .append(DBStructs.TX_USER_FOOTPRINT.ID);
            CCATLogger.DEBUG_LOGGER.debug("getFootPrintReportQuery " + getFootPrintReportQuery);

            return jdbcTemplate.query(getFootPrintReportQuery.toString(), footPrintReportExtractor);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + getFootPrintReportQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + getFootPrintReportQuery, ex);
            throw new HistoryException(ErrorCodes.ERROR.DATABASE_ERROR, ex.getMessage());
        }
    }
}
