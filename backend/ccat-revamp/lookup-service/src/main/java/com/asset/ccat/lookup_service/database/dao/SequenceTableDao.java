/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.database.dao;

import com.asset.ccat.lookup_service.annotation.LogExecutionTime;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import java.sql.ResultSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

/**
 *
 * @author wael.mohamed
 */
@Repository
public class SequenceTableDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private String retrieveDedAccountSequenceQuery;
    private String retrieveAccumulatorSequenceQuery;

    @LogExecutionTime
    public List<Integer> getDedAccountSequence() throws LookupException {
        try {
            if (retrieveDedAccountSequenceQuery == null) {
                retrieveDedAccountSequenceQuery = "SELECT DA_ID FROM LK_DED_ACCOUNTS";
            }
            CCATLogger.DEBUG_LOGGER.debug("getDedAccountSequenceQuery = " + retrieveDedAccountSequenceQuery);
            return jdbcTemplate.query(retrieveDedAccountSequenceQuery, (ResultSet rs, int rowNum) -> {
                return rs.getInt("DA_ID");
            });
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveDedAccountSequenceQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveDedAccountSequenceQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR);
        }
    }

    @LogExecutionTime
    public List<Integer> getAccumulatorSequence() throws LookupException {
        try {
            if (retrieveAccumulatorSequenceQuery == null) {
                retrieveAccumulatorSequenceQuery = "SELECT ACC_ID FROM LK_ACCUMULATORS";
            }
            CCATLogger.DEBUG_LOGGER.debug("getAccumulatorSequenceQuery = " + retrieveAccumulatorSequenceQuery);
            return jdbcTemplate.query(retrieveAccumulatorSequenceQuery, (ResultSet rs, int rowNum) -> {
                return rs.getInt("ACC_ID");
            });
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveDedAccountSequenceQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveDedAccountSequenceQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public Integer getNextId(String sequenceName) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("getNextId : first of getNextId");
        String retrieveNextIdQuery = "Select " + sequenceName + ".NEXTVAL" + " from dual";
        try {
            final SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(retrieveNextIdQuery);
            if (sqlRowSet.next()) {
                int i = sqlRowSet.getInt(1);
                return i;
            }
            return -1;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveNextIdQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveNextIdQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

}
