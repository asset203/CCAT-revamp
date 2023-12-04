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
public class LkPamServiceDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private String retrievePamServiceQuery;
    private String deletePamServiceByIdQuery;
    private String addPamServiceQuery;
    private String updatePamServiceQuery;

    public List<LkPamModel> retrievePamServices() throws LookupException {
        List<LkPamModel> result = null;
        try {
            if (retrievePamServiceQuery == null) {
                retrievePamServiceQuery = "SELECT * FROM " + DatabaseStructs.LK_PAM_SERVICES.TABLE_NAME
                        + " Order By "
                        + DatabaseStructs.LK_PAM_SERVICES.PAM_SERVICE_DESC
                        +" ASC ";
            }
            result = jdbcTemplate.query(retrievePamServiceQuery, new LkPamRowMapper(PamType.PAM_SERVICE));
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrievePamServiceQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrievePamServiceQuery, ex);
        }
        return result;
    }

    public boolean addPamService(Integer id, String description) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting LkPamServiceDao - addMarquee");
        boolean isAdded;

        try {
            if (addPamServiceQuery == null) {
                addPamServiceQuery = "INSERT INTO "
                        + DatabaseStructs.LK_PAM_SERVICES.TABLE_NAME
                        + " ("
                        + DatabaseStructs.LK_PAM_SERVICES.PAM_SERVICE_ID
                        + ", "
                        + DatabaseStructs.LK_PAM_SERVICES.PAM_SERVICE_DESC
                        + ") " + "VALUES (?,?)";
            }

            CCATLogger.DEBUG_LOGGER.debug("sqlStatement = " + addPamServiceQuery);

            isAdded = jdbcTemplate.update(addPamServiceQuery, id, description) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("EXCEPTION -->", e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION -->", e);
            throw new LookupException(ErrorCodes.ERROR.ADD_FAILED);
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending LkPamServiceDao - addMarquee");
        return isAdded;
    }

    public boolean deletePamService(Integer id) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting LkPamServiceDao - deletePamService");
        boolean res;
        try {
            if (deletePamServiceByIdQuery == null) {
                deletePamServiceByIdQuery
                        = "DELETE FROM "
                        + DatabaseStructs.LK_PAM_SERVICES.TABLE_NAME
                        + " WHERE "
                        + DatabaseStructs.LK_PAM_SERVICES.PAM_SERVICE_ID
                        + " = ?";
            }

            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deletePamServiceByIdQuery);
            res = jdbcTemplate.update(deletePamServiceByIdQuery, id) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("LkPamServiceDao - deletePamService EXCEPTION -->", e);
            CCATLogger.ERROR_LOGGER.error("LkPamServiceDao - deletePamService EXCEPTION -->", e);
            throw new LookupException(ErrorCodes.ERROR.DELETE_FAILED);
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending LkPamServiceDao - deletePamService");
        return res;
    }

    public boolean updatePamService(Integer id, String description) throws LookupException {
        int rows = 0;
        try {
            if (updatePamServiceQuery == null) {
                updatePamServiceQuery
                        = "UPDATE " + DatabaseStructs.LK_PAM_SERVICES.TABLE_NAME
                        + " SET "
                        + DatabaseStructs.LK_PAM_SERVICES.PAM_SERVICE_ID + " = ? , "
                        + DatabaseStructs.LK_PAM_SERVICES.PAM_SERVICE_DESC + " = ?"
                        + " WHERE "
                        + DatabaseStructs.LK_PAM_SERVICES.PAM_SERVICE_ID + " = ?";
            }

            CCATLogger.DEBUG_LOGGER.debug("updatePamServiceQuery  = " + updatePamServiceQuery);
            rows = jdbcTemplate.update(updatePamServiceQuery, id, description, id);

        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + updatePamServiceQuery + "\n ex " + ex);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + updatePamServiceQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
        return rows > 0;
    }
}
