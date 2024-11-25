package com.asset.ccat.dynamic_page.database.dao;

import com.asset.ccat.dynamic_page.annotation.LogExecutionTime;
import com.asset.ccat.dynamic_page.database.row_mapper.CursorMappingRowMapper;
import com.asset.ccat.dynamic_page.defines.DatabaseStructs;
import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.ProcedureCursorMappingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * @author assem.hassan
 */
@Repository
public class ProcedureCursorMappingDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CursorMappingRowMapper cursorMappingRowMapper;

    private String updateProcedureCursorMappingByIdQuery;
    private String addProcedureCursorMappingQuery;
    private String deleteProcedureCursorMappingByIdQuery;
    private String deleteProcedureCursorMappingByParamIdQuery;
    private String dropProcedureCursorMappingByParamIdQuery;
    private String retrieveCursorMappingsByParameterIdQuery;


    @LogExecutionTime
    public Integer addProcedureCursorMapping(Integer parameterId, String actualColumnName, String displayColumnName) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started ProcedureCursorMappingDao - addProcedureCursorMapping()");
        try {
            if (addProcedureCursorMappingQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("INSERT INTO ")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.TABLE_NAME)
                        .append(" (").append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.ID).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.PARAMETER_ID).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.ACTUAL_COLUMN_NAME).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.DISPLAY_COLUMN_NAME).append(") ")
                        .append("VALUES ( ")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.SEQUENCE_NAME).append(".NEXTVAL, ")
                        .append(" ? , ? , ? )");
                addProcedureCursorMappingQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + addProcedureCursorMappingQuery);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((Connection connection) -> {
                PreparedStatement ps = connection.prepareStatement(
                        addProcedureCursorMappingQuery, new String[]{DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.ID});
                ps.setInt(1, parameterId);
                ps.setString(2, actualColumnName);
                ps.setString(3, displayColumnName);
                return ps;
            }, keyHolder);
            CCATLogger.DEBUG_LOGGER.debug("Ending ProcedureCursorMappingDao - addProcedureCursorMapping()");

            return keyHolder.getKey() == null ? null : keyHolder.getKey().intValue();
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + addProcedureCursorMappingQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + addProcedureCursorMappingQuery, e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public void batchInsertCursorMapping(final List<ProcedureCursorMappingModel> cursorParameterMappings,
                                         int batchSize, Integer parameterId) throws DynamicPageException {
        try {
            if (addProcedureCursorMappingQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("INSERT INTO ")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.TABLE_NAME)
                        .append(" (").append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.ID).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.PARAMETER_ID).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.ACTUAL_COLUMN_NAME).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.DISPLAY_COLUMN_NAME).append(") ")
                        .append("VALUES ( ")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.SEQUENCE_NAME).append(".NEXTVAL, ")
                        .append(" ? , ? , ? )");
                addProcedureCursorMappingQuery = query.toString();
            }

            jdbcTemplate.batchUpdate(
                    addProcedureCursorMappingQuery,
                    cursorParameterMappings,
                    batchSize,
                    (ps, model) -> {
                        ps.setInt(1, parameterId);
                        ps.setString(2, model.getActualColumnName());
                        ps.setString(3, model.getDisplayColumnName());
                    });
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + addProcedureCursorMappingQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + addProcedureCursorMappingQuery, e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public boolean updateProcedureCursorMapping(ProcedureCursorMappingModel cursorMappingModel) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started ProcedureCursorMappingDao - updateProcedureCursorMapping()");
        try {
            if (updateProcedureCursorMappingByIdQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("UPDATE ")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.PARAMETER_ID).append(" = ? ,")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.ACTUAL_COLUMN_NAME).append(" = = ? ,")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.DISPLAY_COLUMN_NAME).append(" = ? ")
                        .append(" WHERE ").append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.ID).append(" = ?");
                updateProcedureCursorMappingByIdQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + updateProcedureCursorMappingByIdQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending ProcedureCursorMappingDao - updateProcedureCursorMapping()");

            return jdbcTemplate.update(updateProcedureCursorMappingByIdQuery, cursorMappingModel.getParameterId()
                    , cursorMappingModel.getActualColumnName()
                    , cursorMappingModel.getDisplayColumnName()) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public void batchUpdateCursorMapping(final List<ProcedureCursorMappingModel> cursorParameterMappings,
                                         int batchSize, Integer parameterId) throws DynamicPageException {
        try {
            if (updateProcedureCursorMappingByIdQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("UPDATE ")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.PARAMETER_ID).append(" = ? ,")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.ACTUAL_COLUMN_NAME).append(" = = ? ,")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.DISPLAY_COLUMN_NAME).append(" = ? ")
                        .append(" WHERE ").append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.ID).append(" = ?");
                updateProcedureCursorMappingByIdQuery = query.toString();
            }
            jdbcTemplate.batchUpdate(
                    updateProcedureCursorMappingByIdQuery,
                    cursorParameterMappings,
                    batchSize,
                    (ps, model) -> {
                        ps.setInt(1, parameterId);
                        ps.setString(2, model.getActualColumnName());
                        ps.setString(3, model.getDisplayColumnName());
                        ps.setInt(4, model.getId());
                    });
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + updateProcedureCursorMappingByIdQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + updateProcedureCursorMappingByIdQuery, e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public void batchDeleterCursorMapping(Integer parameterId) throws DynamicPageException {
        try {
            if (deleteProcedureCursorMappingByParamIdQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("UPDATE ")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.IS_DELETED).append(" = 1")
                        .append(" WHERE ")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.PARAMETER_ID).append(" = ?");
                deleteProcedureCursorMappingByParamIdQuery = query.toString();
            }
            jdbcTemplate.update(
                    deleteProcedureCursorMappingByParamIdQuery,
                    parameterId);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + deleteProcedureCursorMappingByParamIdQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + deleteProcedureCursorMappingByParamIdQuery, e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }


    @LogExecutionTime
    public boolean deleteProcedureCursorMapping(Integer procedureCursorMappingId) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started ProcedureCursorMappingDao - deleteProcedureCursorMapping()");
        try {
            if (deleteProcedureCursorMappingByIdQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("UPDATE ")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.IS_DELETED).append(" = 1")
                        .append(" WHERE ")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.ID).append(" = ?");
                deleteProcedureCursorMappingByIdQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deleteProcedureCursorMappingByIdQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending ProcedureCursorMappingDao - deleteProcedureCursorMapping()");

            return jdbcTemplate.update(deleteProcedureCursorMappingByIdQuery, procedureCursorMappingId) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public void deleteParameterCursorMappings(Integer parameterId) throws DynamicPageException {
        try {
            if (dropProcedureCursorMappingByParamIdQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("DELETE FROM ")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.PARAMETER_ID).append(" = ?");
                dropProcedureCursorMappingByParamIdQuery = query.toString();
            }
            jdbcTemplate.update(
                    dropProcedureCursorMappingByParamIdQuery,
                    parameterId);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    public List<ProcedureCursorMappingModel> retrieveCursorMappingsByParameterId(Integer parameterId) throws DynamicPageException {
        try {
            if(retrieveCursorMappingsByParameterIdQuery==null){
                StringBuilder query = new StringBuilder();
                query.append("SELECT * FROM ")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.PARAMETER_ID).append(" = ?");
                retrieveCursorMappingsByParameterIdQuery = query.toString();
            }
            return jdbcTemplate.query(retrieveCursorMappingsByParameterIdQuery
                    ,cursorMappingRowMapper
                    , parameterId);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }

    }

}
