package com.asset.ccat.dynamic_page.database.dao;

import com.asset.ccat.dynamic_page.annotation.LogExecutionTime;
import com.asset.ccat.dynamic_page.database.extractors.SPStepsConfigurationsExtractor;
import com.asset.ccat.dynamic_page.defines.DatabaseStructs;
import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.ProcedureConfigurationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author assem.hassan
 */
@Repository
public class ProcedureConfigurationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SPStepsConfigurationsExtractor spStepsConfigurationsExtractor;

    private String deleteProcedureConfigurationByIdQuery;
    private String updateProcedureConfigurationByIdQuery;
    private String addProcedureConfigurationQuery;
    private String retrieveAllProcedureConfigWithParametersQuery;
    private String retrieveProcedureConfigWithParametersByStepIdQuery;
    private String retrieveProcedureConfigQuery;

    @LogExecutionTime
    public HashMap<Integer, ProcedureConfigurationModel> retrieveAllProcedureConfigWithParameters() throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started ProcedureConfigurationDao - retrieveAllProcedureConfigWithParameters()");
        try {
            if (retrieveAllProcedureConfigWithParametersQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("SELECT * ")
                        .append(" FROM ").append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.TABLE_NAME).append(" spConfig")
                        .append(" INNER JOIN ").append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.TABLE_NAME).append(" spParam")
                        .append(" ON ")
                        .append("spConfig.").append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.ID)
                        .append(" = ")
                        .append("spParam.").append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.CONFIG_ID)
                        .append(" LEFT JOIN ").append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.TABLE_NAME).append(" spCursorMapping")
                        .append(" ON ")
                        .append("spParam.").append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.ID)
                        .append(" = ")
                        .append("spCursorMapping.").append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.PARAMETER_ID)
                        .append(" AND ")
                        .append("spCursorMapping.").append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.IS_DELETED).append(" = 0")
                        .append(" WHERE ")
                        .append("spConfig.").append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.IS_DELETED).append("= 0")
                        .append(" AND ")
                        .append("spParam.").append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.IS_DELETED).append(" = 0");
                retrieveAllProcedureConfigWithParametersQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + retrieveAllProcedureConfigWithParametersQuery);
            HashMap<Integer, ProcedureConfigurationModel> res = jdbcTemplate.query(retrieveAllProcedureConfigWithParametersQuery, spStepsConfigurationsExtractor);
            CCATLogger.DEBUG_LOGGER.debug("Ending ProcedureConfigurationDao - retrieveAllProcedureConfigWithParameters()");

            return res;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while execute " + retrieveAllProcedureConfigWithParametersQuery);
            CCATLogger.ERROR_LOGGER.error("Error while execute " + retrieveAllProcedureConfigWithParametersQuery, ex);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public HashMap<Integer, ProcedureConfigurationModel> retrieveProcedureConfigWithParametersByStepId(Integer stepId) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started ProcedureConfigurationDao - retrieveProcedureConfigWithParametersByStepId()");
        try {
            if (retrieveProcedureConfigWithParametersByStepIdQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("SELECT * ")
                        .append(" FROM ").append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.TABLE_NAME).append(" spConfig")
                        .append(" INNER JOIN ").append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.TABLE_NAME).append(" spParam")
                        .append(" ON ")
                        .append("spConfig.").append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.ID)
                        .append(" = ")
                        .append("spParam.").append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.CONFIG_ID)
                        .append(" LEFT JOIN ").append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.TABLE_NAME).append(" spCursorMapping")
                        .append(" ON ")
                        .append("spParam.").append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.ID)
                        .append(" = ")
                        .append("spCursorMapping.").append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.PARAMETER_ID)
                        .append(" AND ")
                        .append("spCursorMapping.").append(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.IS_DELETED).append(" = 0")
                        .append(" WHERE ")
                        .append("spConfig.").append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.STEP_ID).append(" = ? ")
                        .append(" AND ")
                        .append("spConfig.").append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.IS_DELETED).append("= 0")
                        .append(" AND ")
                        .append("spParam.").append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.IS_DELETED).append(" = 0");
                retrieveProcedureConfigWithParametersByStepIdQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + retrieveProcedureConfigWithParametersByStepIdQuery);
            HashMap<Integer, ProcedureConfigurationModel> res = jdbcTemplate.query(
                    retrieveProcedureConfigWithParametersByStepIdQuery,
                    spStepsConfigurationsExtractor, stepId);
            CCATLogger.DEBUG_LOGGER.debug("Ending ProcedureConfigurationDao - retrieveProcedureConfigWithParametersByStepId()");

            return res;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while execute " + retrieveProcedureConfigWithParametersByStepIdQuery);
            CCATLogger.ERROR_LOGGER.error("Error while execute " + retrieveProcedureConfigWithParametersByStepIdQuery, ex);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public Integer addProcedureConfiguration(ProcedureConfigurationModel procedureConfigurationModel) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started ProcedureConfigurationDao - addProcedureConfiguration()");
        try {
            if (addProcedureConfigurationQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("INSERT INTO ")
                        .append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.TABLE_NAME)
                        .append(" (").append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.ID).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.STEP_ID).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.PROCEDURE_NAME).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.DB_URL).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.DB_USERNAME).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.DB_PASSWORD).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.MAX_RECORDS).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.EXTRA_CONFIGURATIONS).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.SUCCESS_CODE).append(") ")
                        .append("VALUES ( ")
                        .append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.SEQUENCE_NAME).append(".NEXTVAL, ")
                        .append(" ?, ? , ? , ? , ? , ? , ? , ? )");
                addProcedureConfigurationQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + addProcedureConfigurationQuery);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((Connection connection) -> {
                PreparedStatement ps = connection.prepareStatement(
                        addProcedureConfigurationQuery, new String[]{DatabaseStructs.DYN_STEP_SP_CONFIGURATION.ID});
                ps.setInt(1, procedureConfigurationModel.getStepId());
                ps.setString(2, procedureConfigurationModel.getProcedureName());
                ps.setString(3, procedureConfigurationModel.getDatabaseURL());
                ps.setString(4, procedureConfigurationModel.getDatabaseUsername());
                ps.setString(5, procedureConfigurationModel.getDatabasePassword());
                ps.setInt(6, procedureConfigurationModel.getMaxRecords());
                ps.setString(7, procedureConfigurationModel.getExtraConfigurations());
                ps.setString(8, procedureConfigurationModel.getSuccessCode());
                return ps;
            }, keyHolder);
            CCATLogger.DEBUG_LOGGER.debug("Ending ProcedureConfigurationDao - addProcedureConfiguration()");

            return keyHolder.getKey() == null ? null : keyHolder.getKey().intValue();
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + addProcedureConfigurationQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + addProcedureConfigurationQuery, e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public boolean updateProcedureConfiguration(ProcedureConfigurationModel procedureConfigurationModel) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started ProcedureConfigurationDao - updateProcedureConfiguration()");
        try {
            ArrayList<Object> queryParams = new ArrayList<>();
            StringBuilder query = new StringBuilder();
            query.append("UPDATE ")
                    .append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.TABLE_NAME)
                    .append(" SET ")
                    .append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.PROCEDURE_NAME).append(" = ? ,")
                    .append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.DB_URL).append(" = ? ,")
                    .append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.DB_USERNAME).append(" = ? ,");
            if (procedureConfigurationModel.getDatabasePassword() != null
                    && !procedureConfigurationModel.getDatabasePassword().trim().isEmpty()) {
                query.append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.DB_PASSWORD).append(" = ? ,");
            }

            query.append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.MAX_RECORDS).append(" = ? ,")
                    .append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.EXTRA_CONFIGURATIONS).append(" = ? , ")
                    .append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.SUCCESS_CODE).append(" = ? ")
                    .append(" WHERE ").append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.ID).append(" = ?")
                    .append(" AND ").append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.STEP_ID).append(" = ?");
            updateProcedureConfigurationByIdQuery = query.toString();

            queryParams.add(procedureConfigurationModel.getProcedureName());
            queryParams.add(procedureConfigurationModel.getDatabaseURL());
            queryParams.add(procedureConfigurationModel.getDatabaseUsername());
            if (procedureConfigurationModel.getDatabasePassword() != null
                    && !procedureConfigurationModel.getDatabasePassword().trim().isEmpty()) {
                queryParams.add(procedureConfigurationModel.getDatabasePassword());
            }
            queryParams.add(procedureConfigurationModel.getMaxRecords());
            queryParams.add(procedureConfigurationModel.getExtraConfigurations());
            queryParams.add(procedureConfigurationModel.getSuccessCode());
            queryParams.add(procedureConfigurationModel.getId());
            queryParams.add(procedureConfigurationModel.getStepId());

            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + updateProcedureConfigurationByIdQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending ProcedureConfigurationDao - updateProcedureConfiguration()");

            return jdbcTemplate.update(updateProcedureConfigurationByIdQuery, queryParams.toArray()) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public boolean deleteProcedureConfiguration(Integer procedureConfigurationId) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started ProcedureConfigurationDao - deleteProcedureConfiguration()");
        try {
            if (deleteProcedureConfigurationByIdQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("UPDATE ")
                        .append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.IS_DELETED).append(" = 1")
                        .append(" WHERE ")
                        .append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.ID).append(" = ?");
                deleteProcedureConfigurationByIdQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deleteProcedureConfigurationByIdQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending ProcedureConfigurationDao - deleteProcedureConfiguration()");

            return jdbcTemplate.update(deleteProcedureConfigurationByIdQuery, procedureConfigurationId) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    public ProcedureConfigurationModel retrieveProcedureStepConfigurations(Integer configId) throws DynamicPageException{
        CCATLogger.DEBUG_LOGGER.debug("Started ProcedureConfigurationDao - retrieveProcedureStepConfigurations()");
        try {
            if (retrieveProcedureConfigQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("SELECT * FROM ")
                        .append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.ID).append(" = ?")
                        .append(" AND ")
                        .append(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.IS_DELETED).append(" = 0");
                retrieveProcedureConfigQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + retrieveProcedureConfigQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending ProcedureConfigurationDao - retrieveProcedureStepConfigurations()");

            return jdbcTemplate.query(retrieveProcedureConfigQuery, resultSet -> {
                ProcedureConfigurationModel configModel = null;
                while (resultSet.next()){
                    configModel = new ProcedureConfigurationModel();
                    configModel.setDatabaseURL(resultSet.getString(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.DB_URL));
                    configModel.setDatabaseUsername(resultSet.getString(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.DB_USERNAME));
                    configModel.setDatabasePassword(resultSet.getString(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.DB_PASSWORD));
                }
                return configModel;
            },configId);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }
}
