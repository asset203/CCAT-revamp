package com.asset.ccat.dynamic_page.database.dao;

import com.asset.ccat.dynamic_page.annotation.LogExecutionTime;
import com.asset.ccat.dynamic_page.defines.DatabaseStructs;
import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.StepModel;
import com.asset.ccat.dynamic_page.models.requests.dynamic_page.AddStepRequest;
import com.asset.ccat.dynamic_page.models.requests.dynamic_page.UpdateStepRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author assem.hassan
 */
@Repository
public class StepDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String deleteStepByIdQuery;
    private String updateStepByIdQuery;
    private String addStepQuery;
    private String getStepByIdQuery;

    @LogExecutionTime
    public Integer addStep(AddStepRequest addStepRequest) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started StepDao - addStep()");
        try {
            if (addStepQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("INSERT INTO ")
                        .append(DatabaseStructs.DYN_PAGES_STEPS.TABLE_NAME)
                        .append(" (").append(DatabaseStructs.DYN_PAGES_STEPS.ID).append(",")
                        .append(DatabaseStructs.DYN_PAGES_STEPS.PAGE_ID).append(",")
                        .append(DatabaseStructs.DYN_PAGES_STEPS.STEP_TYPE).append(",")
                        .append(DatabaseStructs.DYN_PAGES_STEPS.STEP_NAME).append(",")
                        .append(DatabaseStructs.DYN_PAGES_STEPS.IS_HIDDEN).append(",")
                        .append(DatabaseStructs.DYN_PAGES_STEPS.STEP_ORDER).append(") ")
                        .append("VALUES ( ")
                        .append(DatabaseStructs.DYN_PAGES_STEPS.SEQUENCE_NAME).append(".NEXTVAL, ")
                        .append("? , ? , ? , ? ,?)");
                addStepQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + addStepQuery);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((Connection connection) -> {
                PreparedStatement ps = connection.prepareStatement(
                        addStepQuery, new String[]{DatabaseStructs.DYN_PAGES_STEPS.ID});
                ps.setInt(1, addStepRequest.getPageId());
                ps.setInt(2, addStepRequest.getStep().getStepType());
                ps.setString(3, addStepRequest.getStep().getStepName());
                ps.setInt(4, addStepRequest.getStep().getIsHidden() != null && addStepRequest.getStep().getIsHidden() ? 1 : 0);
                ps.setInt(5, addStepRequest.getStep().getStepOrder());
                return ps;
            }, keyHolder);
            CCATLogger.DEBUG_LOGGER.debug("Ending StepDao - addStep()");

            return keyHolder.getKey() == null ? null : keyHolder.getKey().intValue();
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + addStepQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + addStepQuery, e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public boolean updateStep(UpdateStepRequest updateStepRequest) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started StepDao - updateStep()");
        try {
            if (updateStepByIdQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("UPDATE ")
                        .append(DatabaseStructs.DYN_PAGES_STEPS.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.DYN_PAGES_STEPS.STEP_NAME).append(" = ? ,")
                        .append(DatabaseStructs.DYN_PAGES_STEPS.STEP_ORDER).append(" = ?, ")
                        .append(DatabaseStructs.DYN_PAGES_STEPS.IS_HIDDEN).append(" = ? ")
                        .append(" WHERE ").append(DatabaseStructs.DYN_PAGES_STEPS.ID).append(" = ?")
                        .append(" AND ").append(DatabaseStructs.DYN_PAGES_STEPS.PAGE_ID).append(" = ?");
                updateStepByIdQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + updateStepByIdQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending StepDao - updateStep()");

            return jdbcTemplate.update(updateStepByIdQuery, updateStepRequest.getStep().getStepName()
                    , updateStepRequest.getStep().getStepOrder()
                    , updateStepRequest.getStep().getIsHidden() != null && updateStepRequest.getStep().getIsHidden() ? 1 : 0
                    , updateStepRequest.getStep().getId()
                    , updateStepRequest.getPageId()) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public boolean deleteStep(Integer stepId) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started StepDao - deleteStep()");
        try {
            if (deleteStepByIdQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("UPDATE ")
                        .append(DatabaseStructs.DYN_PAGES_STEPS.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.DYN_PAGES_STEPS.IS_DELETED).append(" = 1")
                        .append(" WHERE ")
                        .append(DatabaseStructs.DYN_PAGES_STEPS.ID).append(" = ?");
                deleteStepByIdQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deleteStepByIdQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending StepDao - deleteStep()");

            return jdbcTemplate.update(deleteStepByIdQuery, stepId) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public StepModel retrieveStepById(Integer stepId) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started StepDao - retrieveStepById()");

        try {
            if (getStepByIdQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT * FROM ").append(DatabaseStructs.DYN_PAGES_STEPS.TABLE_NAME)
                        .append(" WHERE ").append(DatabaseStructs.DYN_PAGES_STEPS.ID)
                        .append(" = ?")
                        .append(" AND ")
                        .append(DatabaseStructs.DYN_PAGES.IS_DELETED).append("= 0");
                getStepByIdQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("Ending StepDao - retrieveStepById()");

            return jdbcTemplate.queryForObject(getStepByIdQuery
                    , new BeanPropertyRowMapper<>(StepModel.class)
                    , stepId);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + getStepByIdQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + getStepByIdQuery, ex);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }
}
