package com.asset.ccat.dynamic_page.database.dao;

import com.asset.ccat.dynamic_page.annotation.LogExecutionTime;
import com.asset.ccat.dynamic_page.defines.DatabaseStructs;
import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.HttpParameterModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashSet;

@Repository
public class HttpParameterDao {

    private final JdbcTemplate jdbcTemplate;
    private String addHttpParameterQuery;
    private String deleteHttpParameterByIdQuery;
    private String updateHttpParameterByIdQuery;

    public HttpParameterDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @LogExecutionTime
    public Integer addHttpParameter(HttpParameterModel parameterModel) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started HttpParameterDao -> addHttpParameter()");
        try {
            if (addHttpParameterQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("INSERT INTO ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.TABLE_NAME)
                        .append(" (").append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.ID).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.CONFIG_ID).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.PARAMETER_NAME).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.PARAMETER_DATA_TYPE).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.PARAMETER_TYPE).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.PARAMETER_ORDER).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.INPUT_METHOD).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.DEFAULT_VALUE).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.IS_RESPONSE_CODE).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.IS_RESPONSE_DESCRIPTION).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.DISPLAY_NAME).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.IS_HIDDEN).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.SOURCE_STEP_PARAMETER_ID).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.SOURCE_STEP_PARAMETER_NAME).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.DISPLAY_ORDER).append(", ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.DATE_FORMAT).append(", ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.XPATH).append(", ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.JSON_PATH).append(", ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.STATIC_DATA).append(") ")
                        .append("VALUES ( ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.SEQUENCE_NAME).append(".NEXTVAL, ")
                        .append(" ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?, ? ,? )");
                addHttpParameterQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + addHttpParameterQuery);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((Connection connection) -> {
                PreparedStatement ps = connection.prepareStatement(
                        addHttpParameterQuery, new String[]{DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.ID});
                ps.setInt(1, parameterModel.getConfigId());
                ps.setString(2, parameterModel.getParameterName());
                ps.setInt(3, parameterModel.getParameterDataType());
                ps.setInt(4, parameterModel.getParameterType());
                ps.setInt(5, parameterModel.getParameterOrder());
                ps.setObject(6, parameterModel.getInputMethod());
                ps.setObject(7, parameterModel.getDefaultValue());
                ps.setInt(8, parameterModel.getResponseCode() != null && parameterModel.getResponseCode() ? 1 : 0);
                ps.setInt(9, parameterModel.getResponseDescription() != null && parameterModel.getResponseDescription() ? 1 : 0);
                ps.setString(10, parameterModel.getDisplayName() == null || parameterModel.getDisplayName().isEmpty() ?
                        parameterModel.getParameterName() : parameterModel.getDisplayName());
                ps.setInt(11, parameterModel.getHidden() != null && parameterModel.getHidden() ? 1 : 0);
                ps.setObject(12, parameterModel.getSourceStepParameterId());
                ps.setObject(13, parameterModel.getSourceStepParameterName());
                ps.setInt(14, parameterModel.getDisplayOrder());
                ps.setString(15, parameterModel.getDateFormat());
                ps.setString(16, parameterModel.getxPath());
                ps.setString(17, parameterModel.getJsonPath());
                ps.setString(18, parameterModel.getStaticData());
                return ps;
            }, keyHolder);
            CCATLogger.DEBUG_LOGGER.debug("Ending Started HttpParameterDao -> addHttpParameter()");

            return keyHolder.getKey() == null ? null : keyHolder.getKey().intValue();
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + addHttpParameterQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + addHttpParameterQuery, e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }


    @LogExecutionTime
    public HashSet<Integer> retrieveAllParametersIdsByConfigId(Integer configId) throws DynamicPageException {
        try {
            String query = "SELECT " + DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.ID + " FROM "
                    + DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.TABLE_NAME
                    + " WHERE " + DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.CONFIG_ID + "= ?"
                    + " AND " + DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.IS_DELETED + " = 0";
            return jdbcTemplate.query(query, resultSet -> {
                HashSet<Integer> idSet = new HashSet<>();
                while (resultSet.next()) {
                    idSet.add(resultSet.getInt(1));
                }
                return idSet;
            }, configId);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());

        }


    }

    @LogExecutionTime
    public boolean deleteHttpParameter(Integer httpParameterId) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started HttpParameterDao - deleteHttpParameter()");
        try {
            if (deleteHttpParameterByIdQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("UPDATE ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.IS_DELETED).append(" = 1")
                        .append(" WHERE ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.ID).append(" = ?");
                deleteHttpParameterByIdQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deleteHttpParameterByIdQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending  HttpParameterDao - deleteHttpParameter()");

            return jdbcTemplate.update(deleteHttpParameterByIdQuery, httpParameterId) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public Long getDependentHttpParametersCount(Integer parameterId) throws DynamicPageException{
        try {
            String query = "SELECT COUNT(*) FROM "
                    + DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.TABLE_NAME
                    + " WHERE " + DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.SOURCE_STEP_PARAMETER_ID + "= ?"
                    + " AND " + DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.IS_DELETED + " = 0";
            return jdbcTemplate.queryForObject(query, Long.class, parameterId);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());

        }
    }

    @LogExecutionTime
    public boolean updateHttpParameter(HttpParameterModel httpParameterModel) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started HttpParameterDao - updateHttpParameter()");
        try {
            if (updateHttpParameterByIdQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("UPDATE ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.CONFIG_ID).append(" = ? ,")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.PARAMETER_NAME).append(" = ? ,")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.PARAMETER_DATA_TYPE).append(" = ? ,")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.PARAMETER_TYPE).append(" = ? ,")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.PARAMETER_ORDER).append(" = ? ,")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.INPUT_METHOD).append(" = ? ,")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.DEFAULT_VALUE).append(" = ? ,")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.IS_RESPONSE_CODE).append(" = ? ,")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.IS_RESPONSE_DESCRIPTION).append(" = ? ,")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.DISPLAY_NAME).append(" = ? ,")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.IS_HIDDEN).append(" = ? ,")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.SOURCE_STEP_PARAMETER_ID).append(" = ? , ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.SOURCE_STEP_PARAMETER_NAME).append(" = ? , ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.DATE_FORMAT).append(" = ? , ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.XPATH).append(" = ? , ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.JSON_PATH).append(" = ? , ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.STATIC_DATA).append(" = ? ")
                        .append(" WHERE ").append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.ID).append(" = ?");
                updateHttpParameterByIdQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + updateHttpParameterByIdQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending HttpParameterDao - updateHttpParameter()");

            return jdbcTemplate.update(updateHttpParameterByIdQuery, httpParameterModel.getConfigId()
                    , httpParameterModel.getParameterName()
                    , httpParameterModel.getParameterDataType()
                    , httpParameterModel.getParameterType()
                    , httpParameterModel.getParameterOrder()
                    , httpParameterModel.getInputMethod()
                    , httpParameterModel.getDefaultValue()
                    , httpParameterModel.getResponseCode()
                    , httpParameterModel.getResponseDescription()
                    , httpParameterModel.getDisplayName()
                    , httpParameterModel.getHidden()
                    , httpParameterModel.getSourceStepParameterId()
                    , httpParameterModel.getSourceStepParameterName()
                    , httpParameterModel.getDateFormat()
                    , httpParameterModel.getxPath()
                    , httpParameterModel.getJsonPath()
                    , httpParameterModel.getStaticData()
                    , httpParameterModel.getId()) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

}
