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
public class LkPamClassDao {

    @Autowired
    JdbcTemplate jdbcTemplate;
    private String retrievePamClassQuery;
    private String deletePamClassByIdQuery;
    private String addPamClassQuery;
    private String updatePamClassQuery;

    public List<LkPamModel> retrievePamClasses() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting LkPamClassDao - retrievePamClasses");
        List<LkPamModel> result = null;
        try {
            if (retrievePamClassQuery == null) {
                retrievePamClassQuery = "SELECT * FROM " + DatabaseStructs.LK_PAM_CLASSES.TABLE_NAME
                        + " Order By "
                        + DatabaseStructs.LK_PAM_CLASSES.PAM_CLASS_DESC
                        +" ASC ";
            }
            result = jdbcTemplate.query(retrievePamClassQuery, new LkPamRowMapper(PamType.PAM_CLASS));
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrievePamClassQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrievePamClassQuery, ex);
        }
        return result;
    }

    public boolean addPamClass(Integer id, String description) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting LkPamClassDao - addPamClass");
        boolean isAdded;

        try {
            if (addPamClassQuery == null) {
                addPamClassQuery = "INSERT INTO "
                        + DatabaseStructs.LK_PAM_CLASSES.TABLE_NAME
                        + " ("
                        + DatabaseStructs.LK_PAM_CLASSES.PAM_CLASS_ID
                        + ", "
                        + DatabaseStructs.LK_PAM_CLASSES.PAM_CLASS_DESC
                        + ") " + "VALUES (?,?)";
            }

            CCATLogger.DEBUG_LOGGER.debug("sqlStatement = " + addPamClassQuery);

            isAdded = jdbcTemplate.update(addPamClassQuery, id, description) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("EXCEPTION -->", e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION -->", e);
            throw new LookupException(ErrorCodes.ERROR.ADD_FAILED);
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending LkPamClassDao - addPamClass");
        return isAdded;
    }

    public boolean updatePamClass(Integer id, String description) throws LookupException {
        int rows;
        try {
            if (updatePamClassQuery == null) {
                updatePamClassQuery
                        = "UPDATE " + DatabaseStructs.LK_PAM_CLASSES.TABLE_NAME
                        + " SET "
                        + DatabaseStructs.LK_PAM_CLASSES.PAM_CLASS_ID + " = ? , "
                        + DatabaseStructs.LK_PAM_CLASSES.PAM_CLASS_DESC + " = ? "
                        + " WHERE "
                        + DatabaseStructs.LK_PAM_CLASSES.PAM_CLASS_ID + " = ?";
            }

            CCATLogger.DEBUG_LOGGER.debug("updatePamClassQuery  = " + updatePamClassQuery);
            rows = jdbcTemplate.update(updatePamClassQuery, id, description, id);

        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + updatePamClassQuery + "\n ex " + ex);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + updatePamClassQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
        return rows > 0;
    }

    public boolean deletePamClass(Integer id) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting LkPamClassDao - deletePamClass");
        boolean res;
        try {
            if (deletePamClassByIdQuery == null) {
                deletePamClassByIdQuery
                        = "DELETE FROM "
                        + DatabaseStructs.LK_PAM_CLASSES.TABLE_NAME
                        + " WHERE "
                        + DatabaseStructs.LK_PAM_CLASSES.PAM_CLASS_ID
                        + " = ?";
            }

            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deletePamClassByIdQuery);
            res = jdbcTemplate.update(deletePamClassByIdQuery, id) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("LkPamClassDao - deletePamClass EXCEPTION -->", e);
            CCATLogger.ERROR_LOGGER.error("LkPamClassDao - deletePamClass EXCEPTION -->", e);
            throw new LookupException(ErrorCodes.ERROR.DELETE_FAILED);
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending LkPamClassDao - deletePamClass");
        return res;
    }
}
