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
public class LkPamPriorityDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private String retrievePamPriorityQuery;
    private String deletePamPriorityByIdQuery;
    private String addPamPriorityQuery;
    private String updatePamPriorityQuery;

    public List<LkPamModel> retrievePamPrioritys() throws LookupException {
        List<LkPamModel> result = null;
        try {
            if (retrievePamPriorityQuery == null) {
                retrievePamPriorityQuery = "SELECT * FROM " + DatabaseStructs.LK_PAM_PRIORITY.TABLE_NAME
                        + " Order By "
                        + DatabaseStructs.LK_PAM_PRIORITY.PAM_PRIORITY_DESC
                        +" ASC ";
            }
            result = jdbcTemplate.query(retrievePamPriorityQuery, new LkPamRowMapper(PamType.PAM_PRIORITY));
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrievePamPriorityQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrievePamPriorityQuery, ex);
        }
        return result;
    }

    public boolean addPamPriority(Integer id, String description) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting LkPamPriorityDao - addMarquee");
        boolean isAdded;

        try {
            if (addPamPriorityQuery == null) {
                addPamPriorityQuery = "INSERT INTO "
                        + DatabaseStructs.LK_PAM_PRIORITY.TABLE_NAME
                        + " ("
                        + DatabaseStructs.LK_PAM_PRIORITY.PAM_PRIORITY_ID
                        + ", "
                        + DatabaseStructs.LK_PAM_PRIORITY.PAM_PRIORITY_DESC
                        + ") " + "VALUES (?,?)";
            }

            CCATLogger.DEBUG_LOGGER.debug("sqlStatement = " + addPamPriorityQuery);

            isAdded = jdbcTemplate.update(addPamPriorityQuery, id, description) != 0;
        } catch (Exception e) {
            CCATLogger.ERROR_LOGGER.error("EXCEPTION -->", e);
            CCATLogger.DEBUG_LOGGER.error("EXCEPTION -->", e);
            throw new LookupException(ErrorCodes.ERROR.ADD_FAILED);
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending LkPamPriorityDao - addMarquee");
        return isAdded;
    }

    public boolean updatePamPriority(Integer id, String description) throws LookupException {
        int rows = 0;
        try {
            if (updatePamPriorityQuery == null) {
                updatePamPriorityQuery
                        = "UPDATE " + DatabaseStructs.LK_PAM_PRIORITY.TABLE_NAME
                        + " SET "
                        + DatabaseStructs.LK_PAM_PRIORITY.PAM_PRIORITY_ID + " = ? , "
                        + DatabaseStructs.LK_PAM_PRIORITY.PAM_PRIORITY_DESC + " = ?"
                        + " WHERE "
                        + DatabaseStructs.LK_PAM_PRIORITY.PAM_PRIORITY_ID + " = ?";
            }

            CCATLogger.DEBUG_LOGGER.debug("updatePamPriorityQuery  = " + updatePamPriorityQuery);
            rows = jdbcTemplate.update(updatePamPriorityQuery, id, description, id);

        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + updatePamPriorityQuery + "\n ex " + ex);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + updatePamPriorityQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
        return rows > 0;
    }

    public boolean deletePamPriority(Integer id) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting LkPamPriorityDao - deletePamPriority");
        boolean res;
        try {
            if (deletePamPriorityByIdQuery == null) {
                deletePamPriorityByIdQuery
                        = "DELETE FROM "
                        + DatabaseStructs.LK_PAM_PRIORITY.TABLE_NAME
                        + " WHERE "
                        + DatabaseStructs.LK_PAM_PRIORITY.PAM_PRIORITY_ID
                        + " = ?";
            }

            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deletePamPriorityByIdQuery);
            res = jdbcTemplate.update(deletePamPriorityByIdQuery, id) != 0;
        } catch (Exception e) {
            CCATLogger.ERROR_LOGGER.error("LkPamPriorityDao - deletePamPriority EXCEPTION -->", e);
            CCATLogger.DEBUG_LOGGER.error("LkPamPriorityDao - deletePamPriority EXCEPTION -->", e);
            throw new LookupException(ErrorCodes.ERROR.DELETE_FAILED);
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending LkPamPriorityDao - deletePamPriority");
        return res;
    }
}
