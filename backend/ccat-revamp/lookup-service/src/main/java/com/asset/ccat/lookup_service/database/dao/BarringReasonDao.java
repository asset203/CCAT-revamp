package com.asset.ccat.lookup_service.database.dao;

import com.asset.ccat.lookup_service.database.row_mapper.BusinessPlanRowMapper;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.BarringReasonModel;
import com.asset.ccat.lookup_service.models.BusinessPlanModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BarringReasonDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String retrieveReasonQuery;
    private String addReasonQuery;
    private String deleteReasonQuery;

    public String retrieveReason(String msisdn, Integer barringType, String activePartition) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Starting BarringReasonDao - retrieveReason()");
        try {
            if (retrieveReasonQuery == null) {
                StringBuilder queryBuilder = new StringBuilder();
                queryBuilder.append("SELECT  ").append(DatabaseStructs.TX_CUSTOMERS_BARRINGS.REASON)
                        .append(" FROM ").append(DatabaseStructs.TX_CUSTOMERS_BARRINGS.TABLE_NAME)
                        .append(" PARTITION (").append(activePartition).append(")")
                        .append(" WHERE ").append(DatabaseStructs.TX_CUSTOMERS_BARRINGS.MSISDN)
                        .append(" = ? ")
                        .append(" AND ").append(DatabaseStructs.TX_CUSTOMERS_BARRINGS.BARRING_TYPE).append(" = ").append(barringType)
                        .append(" Order By ")
                        .append(DatabaseStructs.TX_CUSTOMERS_BARRINGS.REASON)
                        .append(" ASC ");
                retrieveReasonQuery = queryBuilder.toString();
            }
            CCATLogger.DEBUG_LOGGER.info("SqlStatement = " + retrieveReasonQuery);
            String reason = jdbcTemplate.queryForObject(retrieveReasonQuery, String.class, msisdn);
            CCATLogger.DEBUG_LOGGER.debug("Ending BarringReasonDao - retrieveReason()");
            return reason;

        } catch (EmptyResultDataAccessException ex) {
            CCATLogger.DEBUG_LOGGER.debug("No Barring Reasons Found");
            return null;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());

        }
    }

    public void addReason(BarringReasonModel reasonModel) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Starting BarringReasonDao - addReason()");
        try {
            if (addReasonQuery == null) {
                StringBuilder queryBuilder = new StringBuilder();
                queryBuilder.append("INSERT INTO ").append(DatabaseStructs.TX_CUSTOMERS_BARRINGS.TABLE_NAME).append("(")
                        .append(DatabaseStructs.TX_CUSTOMERS_BARRINGS.MSISDN)
                        .append(",").append(DatabaseStructs.TX_CUSTOMERS_BARRINGS.MSISDN_MOD_X)
                        .append(",").append(DatabaseStructs.TX_CUSTOMERS_BARRINGS.BARRING_TYPE)
                        .append(",").append(DatabaseStructs.TX_CUSTOMERS_BARRINGS.REASON)
                        .append(",").append(DatabaseStructs.TX_CUSTOMERS_BARRINGS.REQUESTED_BY)
                        .append(",").append(DatabaseStructs.TX_CUSTOMERS_BARRINGS.USER_ID).append(") ")
                        .append("values ( ?,?,?,?,?,?)");
                addReasonQuery = queryBuilder.toString();
            }
            CCATLogger.DEBUG_LOGGER.info("SqlStatement = " + addReasonQuery);
            jdbcTemplate.update(addReasonQuery, reasonModel.getMsisdn(),
                    reasonModel.getModMsisdn(),
                    reasonModel.getBarringType(),
                    reasonModel.getReason(),
                    reasonModel.getRequestedBy(),
                    reasonModel.getUserId());
            CCATLogger.DEBUG_LOGGER.debug("Ending BarringReasonDao - addReason()");
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());

        }
    }

    public void deleteReason(String msisdn, Integer barringType, String activePartition) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Starting BarringReasonDao - deleteReason()");
        try {
            if (deleteReasonQuery == null) {
                StringBuilder queryBuilder = new StringBuilder();
                queryBuilder.append("DELETE ").append(DatabaseStructs.TX_CUSTOMERS_BARRINGS.TABLE_NAME)
                        .append(" PARTITION (").append(activePartition).append(")")
                        .append(" WHERE ").append(DatabaseStructs.TX_CUSTOMERS_BARRINGS.MSISDN).append(" = ?")
                        .append(" AND ").append(DatabaseStructs.TX_CUSTOMERS_BARRINGS.BARRING_TYPE).append(" = ?");
                deleteReasonQuery = queryBuilder.toString();
            }
            CCATLogger.DEBUG_LOGGER.info("SqlStatement = " + deleteReasonQuery);
            jdbcTemplate.update(deleteReasonQuery, msisdn, barringType);
            CCATLogger.DEBUG_LOGGER.debug("Ending BarringReasonDao - deleteReason()");
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }
}
