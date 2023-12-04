/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.database.dao;

import com.asset.ccat.lookup_service.constants.PamType;
import com.asset.ccat.lookup_service.database.row_mapper.LkPamRowMapper;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.LkPamModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author wael.mohamed
 */
@Repository
public class LkPamPeriodDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private String retrievePamPeriodQuery;
    private String deletePamPeriodByIdQuery;
    private String addPamPeriodQuery;
    private String updatePamPeriodQuery;

    public List<LkPamModel> retrievePamPeriods() throws LookupException {
        List<LkPamModel> result = null;
        try {
            if (retrievePamPeriodQuery == null) {
                retrievePamPeriodQuery = "SELECT * FROM " + DatabaseStructs.LK_PAM_PERIOD.TABLE_NAME
                        + " Order By "
                        + DatabaseStructs.LK_PAM_PERIOD.PAM_PERIOD_DESC
                        +" ASC ";
            }
            result = jdbcTemplate.query(retrievePamPeriodQuery, new LkPamRowMapper(PamType.PAM_PERIOD));
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrievePamPeriodQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrievePamPeriodQuery, ex);
        }
        return result;
    }

    public boolean addPamPeriod(Integer id, String description) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting LkPamPeriodDao - addPamPeriod");
        boolean isAdded;

        try {
            if (addPamPeriodQuery == null) {
                addPamPeriodQuery = "INSERT INTO "
                        + DatabaseStructs.LK_PAM_PERIOD.TABLE_NAME
                        + " ("
                        + DatabaseStructs.LK_PAM_PERIOD.PAM_PERIOD_ID
                        + ", "
                        + DatabaseStructs.LK_PAM_PERIOD.PAM_PERIOD_DESC
                        + ") " + "VALUES (?,?)";
            }

            CCATLogger.DEBUG_LOGGER.debug("sqlStatement = " + addPamPeriodQuery);

            isAdded = jdbcTemplate.update(addPamPeriodQuery, id, description) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("EXCEPTION -->", e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION -->", e);
            throw new LookupException(ErrorCodes.ERROR.ADD_FAILED);
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending LkPamPeriodDao - addPamPeriod");
        return isAdded;
    }

    public boolean updatePamPeriod(Integer id, String description) throws LookupException {
        int rows = 0;
        try {
            if (updatePamPeriodQuery == null) {
                updatePamPeriodQuery
                        = "UPDATE " + DatabaseStructs.LK_PAM_PERIOD.TABLE_NAME
                        + " SET "
                        + DatabaseStructs.LK_PAM_PERIOD.PAM_PERIOD_ID + " = ? , "
                        + DatabaseStructs.LK_PAM_PERIOD.PAM_PERIOD_DESC + " = ?"
                        + " WHERE "
                        + DatabaseStructs.LK_PAM_PERIOD.PAM_PERIOD_ID + " = ?";
            }

            CCATLogger.DEBUG_LOGGER.debug("updatePamPeriodQuery  = " + updatePamPeriodQuery);
            rows = jdbcTemplate.update(updatePamPeriodQuery, id, description, id);

        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + updatePamPeriodQuery + "\n ex " + ex);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + updatePamPeriodQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
        return rows > 0;
    }

    public boolean deletePamPeriod(Integer id) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting LkPamPeriodDao - deletePamPeriod");
        boolean res;
        try {
            if (deletePamPeriodByIdQuery == null) {
                deletePamPeriodByIdQuery
                        = "DELETE FROM "
                        + DatabaseStructs.LK_PAM_PERIOD.TABLE_NAME
                        + " WHERE "
                        + DatabaseStructs.LK_PAM_PERIOD.PAM_PERIOD_ID
                        + " = ?";
            }

            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deletePamPeriodByIdQuery);
            res = jdbcTemplate.update(deletePamPeriodByIdQuery, id) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("LkPamPeriodDao - deletePamPeriod EXCEPTION -->", e);
            CCATLogger.ERROR_LOGGER.error("LkPamPeriodDao - deletePamPeriod EXCEPTION -->", e);
            throw new LookupException(ErrorCodes.ERROR.DELETE_FAILED);
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending LkPamPeriodDao - deletePamPeriod");
        return res;
    }
}
