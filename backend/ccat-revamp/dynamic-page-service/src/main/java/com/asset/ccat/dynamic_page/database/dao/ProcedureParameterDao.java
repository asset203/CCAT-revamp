package com.asset.ccat.dynamic_page.database.dao;

import com.asset.ccat.dynamic_page.annotation.LogExecutionTime;
import com.asset.ccat.dynamic_page.defines.DatabaseStructs;
import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.ProcedureParameterModel;
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
import java.util.HashSet;

/**
 * @author assem.hassan
 */
@Repository
public class ProcedureParameterDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String deleteProcedureParameterByIdQuery;
    private String updateProcedureParameterByIdQuery;
    private String addProcedureParameterQuery;

    @LogExecutionTime
    public Integer addProcedureParameter(ProcedureParameterModel procedureParameterModel) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started ProcedureParameterDao - addProcedureParameter()");
        try {
            if (addProcedureParameterQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("INSERT INTO ")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.TABLE_NAME)
                        .append(" (").append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.ID).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.CONFIG_ID).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.PARAMETER_NAME).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.PARAMETER_DATA_TYPE).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.PARAMETER_TYPE).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.PARAMETER_ORDER).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.INPUT_METHOD).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.DEFAULT_VALUE).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.IS_RESPONSE_CODE).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.IS_RESPONSE_DESCRIPTION).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.DISPLAY_NAME).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.IS_HIDDEN).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.SOURCE_STEP_PARAMETER_ID).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.SOURCE_STEP_PARAMETER_NAME).append(",")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.DISPLAY_ORDER).append(", ")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.DATE_FORMAT).append(", ")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.STATIC_DATA).append(") ")
                        .append("VALUES ( ")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.SEQUENCE_NAME).append(".NEXTVAL, ")
                        .append(" ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? ,?)");
                addProcedureParameterQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + addProcedureParameterQuery);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((Connection connection) -> {
                PreparedStatement ps = connection.prepareStatement(
                        addProcedureParameterQuery, new String[]{DatabaseStructs.DYN_STEP_SP_PARAMETERS.ID});
                ps.setInt(1, procedureParameterModel.getConfigId());
                ps.setString(2, procedureParameterModel.getParameterName());
                ps.setInt(3, procedureParameterModel.getParameterDataType());
                ps.setInt(4, procedureParameterModel.getParameterType());
                ps.setInt(5, procedureParameterModel.getParameterOrder());
                ps.setObject(6, procedureParameterModel.getInputMethod());
                ps.setObject(7, procedureParameterModel.getDefaultValue());
                ps.setInt(8, procedureParameterModel.getResponseCode() != null && procedureParameterModel.getResponseCode() ? 1 : 0);
                ps.setInt(9, procedureParameterModel.getResponseDescription() != null && procedureParameterModel.getResponseDescription() ? 1 : 0);
                ps.setString(10, procedureParameterModel.getDisplayName() == null || procedureParameterModel.getDisplayName().isEmpty() ?
                        procedureParameterModel.getParameterName() : procedureParameterModel.getDisplayName());
                ps.setInt(11, procedureParameterModel.getHidden() != null && procedureParameterModel.getHidden() ? 1 : 0);
                ps.setObject(12, procedureParameterModel.getSourceStepParameterId());
                ps.setObject(13, procedureParameterModel.getSourceStepParameterName());
                ps.setInt(14, procedureParameterModel.getDisplayOrder());
                ps.setString(15, procedureParameterModel.getDateFormat());
                ps.setString(16, procedureParameterModel.getStaticData());
                return ps;
            }, keyHolder);
            CCATLogger.DEBUG_LOGGER.debug("Ending ProcedureParameterDao - addProcedureParameter()");

            return keyHolder.getKey() == null ? null : keyHolder.getKey().intValue();
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + addProcedureParameterQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + addProcedureParameterQuery, e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public boolean updateProcedureParameter(ProcedureParameterModel procedureParameterModel) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started ProcedureParameterDao - updateProcedureParameter()");
        try {
            if (updateProcedureParameterByIdQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("UPDATE ")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.CONFIG_ID).append(" = ? ,")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.PARAMETER_NAME).append(" = ? ,")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.PARAMETER_DATA_TYPE).append(" = ? ,")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.PARAMETER_TYPE).append(" = ? ,")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.PARAMETER_ORDER).append(" = ? ,")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.INPUT_METHOD).append(" = ? ,")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.DEFAULT_VALUE).append(" = ? ,")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.IS_RESPONSE_CODE).append(" = ? ,")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.IS_RESPONSE_DESCRIPTION).append(" = ? ,")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.DISPLAY_NAME).append(" = ? ,")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.IS_HIDDEN).append(" = ? ,")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.SOURCE_STEP_PARAMETER_ID).append(" = ?, ")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.SOURCE_STEP_PARAMETER_NAME).append(" = ?,")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.STATIC_DATA).append(" = ? ")
                        .append(" WHERE ").append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.ID).append(" = ?");
                updateProcedureParameterByIdQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + updateProcedureParameterByIdQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending ProcedureParameterDao - updateProcedureParameter()");

            return jdbcTemplate.update(updateProcedureParameterByIdQuery, procedureParameterModel.getConfigId()
                    , procedureParameterModel.getParameterName()
                    , procedureParameterModel.getParameterDataType()
                    , procedureParameterModel.getParameterType()
                    , procedureParameterModel.getParameterOrder()
                    , procedureParameterModel.getInputMethod()
                    , procedureParameterModel.getDefaultValue()
                    , procedureParameterModel.getResponseCode()
                    , procedureParameterModel.getResponseDescription()
                    , procedureParameterModel.getDisplayName()
                    , procedureParameterModel.getHidden()
                    , procedureParameterModel.getSourceStepParameterId()
                    , procedureParameterModel.getSourceStepParameterName()
                    , procedureParameterModel.getStaticData()
                    , procedureParameterModel.getId()) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public boolean deleteProcedureParameter(Integer procedureParameterId) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started ProcedureParameterDao - deleteProcedureParameter()");
        try {
            if (deleteProcedureParameterByIdQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("UPDATE ")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.IS_DELETED).append(" = 1")
                        .append(" WHERE ")
                        .append(DatabaseStructs.DYN_STEP_SP_PARAMETERS.ID).append(" = ?");
                deleteProcedureParameterByIdQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deleteProcedureParameterByIdQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending ProcedureParameterDao - deleteProcedureParameter()");

            return jdbcTemplate.update(deleteProcedureParameterByIdQuery, procedureParameterId) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public HashSet<Integer> retrieveAllParametersIdsByConfigId(Integer configId) throws DynamicPageException {
        try {
            String query = "SELECT " + DatabaseStructs.DYN_STEP_SP_PARAMETERS.ID + " FROM "
                    + DatabaseStructs.DYN_STEP_SP_PARAMETERS.TABLE_NAME
                    + " WHERE " + DatabaseStructs.DYN_STEP_SP_PARAMETERS.CONFIG_ID + "= ?"
                    + " AND " + DatabaseStructs.DYN_STEP_SP_PARAMETERS.IS_DELETED + " = 0";
            return jdbcTemplate.query(query, new ResultSetExtractor<HashSet<Integer>>() {
                @Override
                public HashSet<Integer> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                    HashSet<Integer> idSet = new HashSet<>();
                    while (resultSet.next()) {
                        idSet.add(resultSet.getInt(1));
                    }
                    return idSet;
                }
            }, configId);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());

        }
    }

    @LogExecutionTime
    public Long getDependentParametersCount(Integer parameterId) throws DynamicPageException{
        try {
            String query = "SELECT COUNT(*) FROM "
                    + DatabaseStructs.DYN_STEP_SP_PARAMETERS.TABLE_NAME
                    + " WHERE " + DatabaseStructs.DYN_STEP_SP_PARAMETERS.SOURCE_STEP_PARAMETER_ID + "= ?"
                    + " AND " + DatabaseStructs.DYN_STEP_SP_PARAMETERS.IS_DELETED + " = 0";
            return jdbcTemplate.queryForObject(query, Long.class, parameterId);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());

        }
    }
}
