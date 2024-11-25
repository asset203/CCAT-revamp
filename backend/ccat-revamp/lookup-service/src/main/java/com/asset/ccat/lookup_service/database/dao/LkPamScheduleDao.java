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
public class LkPamScheduleDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private String retrievePamScheduleQuery;
    private String deletePamScheduleByIdQuery;
    private String addPamScheduleQuery;
    private String updatePamScheduleQuery;

    public List<LkPamModel> retrievePamSchedules() throws LookupException {
        List<LkPamModel> result = null;
        try {
            if (retrievePamScheduleQuery == null) {
                retrievePamScheduleQuery = "SELECT * FROM " + DatabaseStructs.LK_PAM_SCHEDULES.TABLE_NAME
                        + " Order By "
                        + DatabaseStructs.LK_PAM_SCHEDULES.PAM_SCHEDULE_DESC
                        +" ASC ";
            }
            result = jdbcTemplate.query(retrievePamScheduleQuery, new LkPamRowMapper(PamType.PAM_SCHEDULE));
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrievePamScheduleQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrievePamScheduleQuery, ex);
        }
        return result;
    }

    public boolean addPamSchedule(Integer id, String description) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting LkPamScheduleDao - addMarquee");
        boolean isAdded;

        try {
            if (addPamScheduleQuery == null) {
                addPamScheduleQuery = "INSERT INTO "
                        + DatabaseStructs.LK_PAM_SCHEDULES.TABLE_NAME
                        + " ("
                        + DatabaseStructs.LK_PAM_SCHEDULES.PAM_SCHEDULE_ID
                        + ", "
                        + DatabaseStructs.LK_PAM_SCHEDULES.PAM_SCHEDULE_DESC
                        + ") " + "VALUES (?,?)";
            }

            CCATLogger.DEBUG_LOGGER.debug("sqlStatement = " + addPamScheduleQuery);

            isAdded = jdbcTemplate.update(addPamScheduleQuery, id, description) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("EXCEPTION -->", e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION -->", e);
            throw new LookupException(ErrorCodes.ERROR.ADD_FAILED);
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending LkPamScheduleDao - addMarquee");
        return isAdded;
    }

    public boolean updatePamSchedule(Integer id, String description) throws LookupException {
        int rows = 0;
        try {
            if (updatePamScheduleQuery == null) {
                updatePamScheduleQuery
                        = "UPDATE " + DatabaseStructs.LK_PAM_SCHEDULES.TABLE_NAME
                        + " SET "
                        + DatabaseStructs.LK_PAM_SCHEDULES.PAM_SCHEDULE_ID + " = ? , "
                        + DatabaseStructs.LK_PAM_SCHEDULES.PAM_SCHEDULE_DESC + " = ?"
                        + " WHERE "
                        + DatabaseStructs.LK_PAM_SCHEDULES.PAM_SCHEDULE_ID + " = ?";
            }

            CCATLogger.DEBUG_LOGGER.debug("updatePamScheduleQuery  = " + updatePamScheduleQuery);
            rows = jdbcTemplate.update(updatePamScheduleQuery, id, description, id);

        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + updatePamScheduleQuery + "\n ex " + ex);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + updatePamScheduleQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
        return rows > 0;
    }

    public boolean deletePamSchedule(Integer id) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting LkPamScheduleDao - deletePamSchedule");
        boolean res;
        try {
            if (deletePamScheduleByIdQuery == null) {
                deletePamScheduleByIdQuery
                        = "DELETE FROM "
                        + DatabaseStructs.LK_PAM_SCHEDULES.TABLE_NAME
                        + " WHERE "
                        + DatabaseStructs.LK_PAM_SCHEDULES.PAM_SCHEDULE_ID
                        + " = ?";
            }

            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deletePamScheduleByIdQuery);
            res = jdbcTemplate.update(deletePamScheduleByIdQuery, id) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("LkPamScheduleDao - deletePamSchedule EXCEPTION -->", e);
            CCATLogger.ERROR_LOGGER.error("LkPamScheduleDao - deletePamSchedule EXCEPTION -->", e);
            throw new LookupException(ErrorCodes.ERROR.DELETE_FAILED);
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending LkPamScheduleDao - deletePamSchedule");
        return res;
    }

}
