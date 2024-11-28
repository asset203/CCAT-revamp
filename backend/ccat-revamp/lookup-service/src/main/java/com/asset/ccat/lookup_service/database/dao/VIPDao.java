package com.asset.ccat.lookup_service.database.dao;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class VIPDao {
    private final JdbcTemplate jdbcTemplate;

    public VIPDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getVIPList() throws LookupException {
        try {
            String sqlQuery = "SELECT " + DatabaseStructs.ADM_VIP_MSISDN.VIP_MSISDN + " FROM " +
                    DatabaseStructs.ADM_VIP_MSISDN.TABLE_NAME;
            CCATLogger.DEBUG_LOGGER.debug("SQL-Query = {}", sqlQuery);
            return jdbcTemplate.queryForList(sqlQuery, String.class);
        } catch (DataAccessException ex) {
            return new ArrayList<>();
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception while retrieving VIP_MSISDN. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception while retrieving VIP_MSISDN. ", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR);
        }
    }

    public List<String> getVIPPagesList() throws LookupException {
        try {
            String sqlQuery = "SELECT " + DatabaseStructs.ADM_VIP_PAGES.PAGE_NAME + " FROM " +
                    DatabaseStructs.ADM_VIP_PAGES.TABLE_NAME;
            CCATLogger.DEBUG_LOGGER.debug("SQL-Query = {}", sqlQuery);
            return jdbcTemplate.queryForList(sqlQuery, String.class);
        } catch (DataAccessException ex) {
            return new ArrayList<>();
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception while retrieving VIP_PAGES. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception while retrieving VIP_PAGES. ", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR);
        }
    }

    public int addVIPMsisdn(String msisdn) throws LookupException {
        try {
            String sqlQuery = "INSERT INTO " + DatabaseStructs.ADM_VIP_MSISDN.TABLE_NAME + " VALUES(?) ";
            CCATLogger.DEBUG_LOGGER.debug("SQL-Query = {}", sqlQuery);
            return jdbcTemplate.update(sqlQuery, msisdn);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception while retrieving VIP_MSISDN. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception while retrieving VIP_MSISDN. ", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR);
        }
    }

    public int deleteVIPMsisdn(String msisdn) throws LookupException {
        try {
            String sqlQuery = "DELETE FROM " + DatabaseStructs.ADM_VIP_MSISDN.TABLE_NAME +
                    " WHERE " + DatabaseStructs.ADM_VIP_MSISDN.VIP_MSISDN + " = ?";
            CCATLogger.DEBUG_LOGGER.debug("SQL-Query = {}", sqlQuery);
            return jdbcTemplate.update(sqlQuery, msisdn);
        }catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception while Deletion of a VIP_MSISDN. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception while Deletion of a VIP_MSISDN. ", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR);
        }
    }

    public void updateVIPPages(List<String> pages){

    }
}
