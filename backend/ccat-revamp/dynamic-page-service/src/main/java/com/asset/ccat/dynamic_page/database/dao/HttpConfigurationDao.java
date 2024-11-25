package com.asset.ccat.dynamic_page.database.dao;

import com.asset.ccat.dynamic_page.annotation.LogExecutionTime;
import com.asset.ccat.dynamic_page.database.extractors.HTTPStepsConfigurationsExtractor;
import com.asset.ccat.dynamic_page.defines.DatabaseStructs;
import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.HttpConfigurationModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;

@Repository
public class HttpConfigurationDao {
    private final JdbcTemplate jdbcTemplate;
    private final HTTPStepsConfigurationsExtractor httpStepsConfigurationsExtractor;

    public HttpConfigurationDao(JdbcTemplate jdbcTemplate, HTTPStepsConfigurationsExtractor httpStepsConfigurationsExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.httpStepsConfigurationsExtractor = httpStepsConfigurationsExtractor;
    }

    private String addHttpConfigurationQuery;
    private String retrieveHttpConfigurationWithParametersByStepIdQuery;
    private String retrieveAllHttpConfigurationWithParametersQuery;
    private String updateHTTPConfigurationByIdQuery;
    private String deleteHttpConfigurationByIdQuery;



    @LogExecutionTime
    public Integer addHttpConfiguration(HttpConfigurationModel httpConfigurationModel) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started HttpConfigurationDao - addHttpConfiguration()");
        try {
            if (addHttpConfigurationQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("INSERT INTO ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.TABLE_NAME)
                        .append(" (").append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.ID).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.STEP_ID).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.HTTP_URL).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.HTTP_METHOD).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.HEADERS).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.REQUEST_CONTENT_TYPE).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.RESPONSE_CONTENT_TYPE).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.RESPONSE_FORM).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.KEY_VALUE_DELIMITER).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.MAIN_DELIMITER).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.REQUEST_BODY).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.MAX_RECORDS).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.SUCCESS_CODE).append(") ")
                        .append("VALUES ( ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.SEQUENCE_NAME).append(".NEXTVAL, ")
                        .append(" ?, ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? )");
                addHttpConfigurationQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + addHttpConfigurationQuery);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((Connection connection) -> {
                PreparedStatement ps = connection.prepareStatement(
                        addHttpConfigurationQuery, new String[]{DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.ID});
                ps.setInt(1, httpConfigurationModel.getStepId());
                ps.setString(2, httpConfigurationModel.getHttpURL());
                ps.setObject(3, httpConfigurationModel.getHttpMethod());
                ps.setString(4, httpConfigurationModel.getHeaders());
                ps.setObject(5, httpConfigurationModel.getRequestContentType());
                ps.setObject(6, httpConfigurationModel.getResponseContentType());
                ps.setObject(7, httpConfigurationModel.getResponseForm());
                ps.setString(8, httpConfigurationModel.getKeyValueDelimiter());
                ps.setString(9, httpConfigurationModel.getMainDelimiter());
                ps.setString(10, httpConfigurationModel.getRequestBody());
                ps.setInt(11, httpConfigurationModel.getMaxRecords());
                ps.setString(12, httpConfigurationModel.getSuccessCode());
                return ps;
            }, keyHolder);
            CCATLogger.DEBUG_LOGGER.debug("Ending HttpConfigurationDao - addHttpConfiguration()");

            return keyHolder.getKey() == null ? null : keyHolder.getKey().intValue();
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + addHttpConfigurationQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + addHttpConfigurationQuery, e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }


    public HashMap<Integer, HttpConfigurationModel> retrieveHTTPConfigWithParametersByStepId(Integer stepId) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started HTTPConfigurationDao - retrieveHTTPConfigWithParametersByStepId()");
        try {
            if (retrieveHttpConfigurationWithParametersByStepIdQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("SELECT * ")
                        .append(" FROM ").append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.TABLE_NAME).append(" httpConfig")
                        .append(" INNER JOIN ").append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.TABLE_NAME).append(" httpParam")
                        .append(" ON ")
                        .append("httpConfig.").append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.ID)
                        .append(" = ")
                        .append("httpParam.").append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.CONFIG_ID)
                        .append(" LEFT JOIN ").append(DatabaseStructs.DYN_STEP_HTTP_RESPONSE_MAPPING.TABLE_NAME).append(" httpResponseMapping")
                        .append(" ON ")
                        .append("httpParam.").append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.ID)
                        .append(" = ")
                        .append("httpResponseMapping.").append(DatabaseStructs.DYN_STEP_HTTP_RESPONSE_MAPPING.PARAMETER_ID)
                        .append(" AND ")
                        .append("httpResponseMapping.").append(DatabaseStructs.DYN_STEP_HTTP_RESPONSE_MAPPING.IS_DELETED).append(" = 0")
                        .append(" WHERE ")
                        .append("httpConfig.").append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.STEP_ID).append(" = ? ")
                        .append(" AND ")
                        .append("httpConfig.").append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.IS_DELETED).append("= 0")
                        .append(" AND ")
                        .append("httpParam.").append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.IS_DELETED).append(" = 0");
                retrieveHttpConfigurationWithParametersByStepIdQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + retrieveHttpConfigurationWithParametersByStepIdQuery);
            HashMap<Integer, HttpConfigurationModel> res = jdbcTemplate.query(
                    retrieveHttpConfigurationWithParametersByStepIdQuery,
                    httpStepsConfigurationsExtractor, stepId);
            CCATLogger.DEBUG_LOGGER.debug("Ending HTTPConfigurationDao - retrieveHTTPConfigWithParametersByStepId()");

            return res;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while execute " + retrieveHttpConfigurationWithParametersByStepIdQuery);
            CCATLogger.ERROR_LOGGER.error("Error while execute " + retrieveHttpConfigurationWithParametersByStepIdQuery, ex);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public HashMap<Integer, HttpConfigurationModel> retrieveAllHTTPConfigWithParameters() throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started HTTPConfigurationDao -> retrieveAllHTTPConfigWithParameters()");
        try {
            if (retrieveAllHttpConfigurationWithParametersQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("SELECT * ")
                        .append(" FROM ").append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.TABLE_NAME).append(" httpConfig")
                        .append(" INNER JOIN ").append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.TABLE_NAME).append(" httpParam")
                        .append(" ON ")
                        .append("httpConfig.").append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.ID)
                        .append(" = ")
                        .append("httpParam.").append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.CONFIG_ID)
                        .append(" LEFT JOIN ").append(DatabaseStructs.DYN_STEP_HTTP_RESPONSE_MAPPING.TABLE_NAME).append(" httpResponseMapping")
                        .append(" ON ")
                        .append("httpParam.").append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.ID)
                        .append(" = ")
                        .append("httpResponseMapping.").append(DatabaseStructs.DYN_STEP_HTTP_RESPONSE_MAPPING.PARAMETER_ID)
                        .append(" AND ")
                        .append("httpResponseMapping.").append(DatabaseStructs.DYN_STEP_HTTP_RESPONSE_MAPPING.IS_DELETED).append(" = 0")
                        .append(" WHERE ")
                        .append("httpConfig.").append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.IS_DELETED).append("= 0")
                        .append(" AND ")
                        .append("httpParam.").append(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.IS_DELETED).append(" = 0");
                retrieveAllHttpConfigurationWithParametersQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + retrieveAllHttpConfigurationWithParametersQuery);
            HashMap<Integer, HttpConfigurationModel> res = jdbcTemplate.query(retrieveAllHttpConfigurationWithParametersQuery, httpStepsConfigurationsExtractor);
            CCATLogger.DEBUG_LOGGER.debug("Ending HTTPConfigurationDao -> retrieveAllHTTPConfigWithParameters()");

            return res;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while execute " + retrieveAllHttpConfigurationWithParametersQuery);
            CCATLogger.ERROR_LOGGER.error("Error while execute " + retrieveAllHttpConfigurationWithParametersQuery, ex);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }


    @LogExecutionTime
    public boolean updateHTTPConfiguration(HttpConfigurationModel httpConfigurationModel) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started HTTPConfigurationDao - updateHTTPConfiguration()");
        try {
            ArrayList<Object> queryParams = new ArrayList<>();
            StringBuilder query = new StringBuilder();
            query.append("UPDATE ")
                    .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.TABLE_NAME)
                    .append(" SET ")
                    .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.HTTP_URL).append(" = ? , ")
                    .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.HTTP_METHOD).append(" = ? ,")
                    .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.HEADERS).append(" = ? ,")
                    .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.REQUEST_CONTENT_TYPE).append(" = ? , ")
                    .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.RESPONSE_CONTENT_TYPE).append(" = ? , ")
                    .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.RESPONSE_FORM).append(" = ? , ")
                    .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.REQUEST_BODY).append(" = ? , ")
                    .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.KEY_VALUE_DELIMITER).append(" = ? , ")
                    .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.MAIN_DELIMITER).append(" = ? , ")
                    .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.MAX_RECORDS).append(" = ? , ")
                    .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.SUCCESS_CODE).append(" = ? ")
                    .append(" WHERE ").append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.ID).append(" = ? ")
                    .append(" AND ").append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.STEP_ID).append(" = ? ");
            updateHTTPConfigurationByIdQuery = query.toString();

            queryParams.add(httpConfigurationModel.getHttpURL());
            queryParams.add(httpConfigurationModel.getHttpMethod());
            queryParams.add(httpConfigurationModel.getHeaders());

            queryParams.add(httpConfigurationModel.getRequestContentType());
            queryParams.add(httpConfigurationModel.getResponseContentType());
            queryParams.add(httpConfigurationModel.getResponseForm());
            queryParams.add(httpConfigurationModel.getRequestBody());
            queryParams.add(httpConfigurationModel.getKeyValueDelimiter());
            queryParams.add(httpConfigurationModel.getMainDelimiter());

            queryParams.add(httpConfigurationModel.getMaxRecords());
            queryParams.add(httpConfigurationModel.getSuccessCode());
            queryParams.add(httpConfigurationModel.getId());
            queryParams.add(httpConfigurationModel.getStepId());

            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + updateHTTPConfigurationByIdQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending HTTPConfigurationDao - updateHTTPConfiguration()");
            return jdbcTemplate.update(updateHTTPConfigurationByIdQuery, queryParams.toArray()) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public boolean deleteHttpConfiguration(Integer httpConfigurationId) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started HttpConfigurationDao - deleteHttpConfiguration()");
        try {
            if (deleteHttpConfigurationByIdQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("UPDATE ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.IS_DELETED).append(" = 1")
                        .append(" WHERE ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.ID).append(" = ?");
                deleteHttpConfigurationByIdQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deleteHttpConfigurationByIdQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending HttpConfigurationDao - deleteHttpConfiguration()");

            return jdbcTemplate.update(deleteHttpConfigurationByIdQuery, httpConfigurationId) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

}
