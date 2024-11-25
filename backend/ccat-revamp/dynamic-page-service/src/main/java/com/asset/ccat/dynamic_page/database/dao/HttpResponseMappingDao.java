package com.asset.ccat.dynamic_page.database.dao;

import com.asset.ccat.dynamic_page.annotation.LogExecutionTime;
import com.asset.ccat.dynamic_page.database.row_mapper.HttpResponseMappingRowMapper;
import com.asset.ccat.dynamic_page.defines.DatabaseStructs;
import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.HttpResponseMappingModel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HttpResponseMappingDao {

    private final JdbcTemplate jdbcTemplate;
    private final HttpResponseMappingRowMapper httpResponseMappingRowMapper;

    public HttpResponseMappingDao(JdbcTemplate jdbcTemplate, HttpResponseMappingRowMapper httpResponseMappingRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.httpResponseMappingRowMapper = httpResponseMappingRowMapper;
    }

    private String addHttpResponseMappingQuery;
    private String deleteHttpResponseMappingByParamIdQuery;
    private String dropHttpResponseMappingByParamIdQuery;
    private String retrieveHttpResponseMappingsByParameterIdQuery;

    @LogExecutionTime
    public void batchInsertResponseMapping(final List<HttpResponseMappingModel> httpParameterMappings,
                                         int batchSize, Integer parameterId) throws DynamicPageException {
        try {
            if (addHttpResponseMappingQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("INSERT INTO ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_RESPONSE_MAPPING.TABLE_NAME)
                        .append(" (").append(DatabaseStructs.DYN_STEP_HTTP_RESPONSE_MAPPING.ID).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_RESPONSE_MAPPING.PARAMETER_ID).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_RESPONSE_MAPPING.HEADER_NAME).append(",")
                        .append(DatabaseStructs.DYN_STEP_HTTP_RESPONSE_MAPPING.HEADER_DISPLAY_NAME).append(") ")
                        .append("VALUES ( ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_RESPONSE_MAPPING.SEQUENCE_NAME).append(".NEXTVAL, ")
                        .append(" ? , ? , ? )");
                addHttpResponseMappingQuery = query.toString();
            }

            jdbcTemplate.batchUpdate(
                    addHttpResponseMappingQuery,
                    httpParameterMappings,
                    batchSize,
                    (ps, model) -> {
                        ps.setInt(1, parameterId);
                        ps.setString(2, model.getHeaderName());
                        ps.setString(3, model.getHeaderDisplayName());
                    });
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + addHttpResponseMappingQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + addHttpResponseMappingQuery, e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }


    @LogExecutionTime
    public void batchDeleterHttpResponseMapping(Integer parameterId) throws DynamicPageException {
        try {
            if (deleteHttpResponseMappingByParamIdQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("UPDATE ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_RESPONSE_MAPPING.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_RESPONSE_MAPPING.IS_DELETED).append(" = 1")
                        .append(" WHERE ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_RESPONSE_MAPPING.PARAMETER_ID).append(" = ?");
                deleteHttpResponseMappingByParamIdQuery = query.toString();
            }
            jdbcTemplate.update(
                    deleteHttpResponseMappingByParamIdQuery,
                    parameterId);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + deleteHttpResponseMappingByParamIdQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + deleteHttpResponseMappingByParamIdQuery, e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public void deleteParameterResponseMappings(Integer parameterId) throws DynamicPageException {
        try {
            if (dropHttpResponseMappingByParamIdQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("DELETE FROM ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_RESPONSE_MAPPING.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_RESPONSE_MAPPING.PARAMETER_ID).append(" = ?");
                dropHttpResponseMappingByParamIdQuery = query.toString();
            }
            jdbcTemplate.update(
                    dropHttpResponseMappingByParamIdQuery,
                    parameterId);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    public List<HttpResponseMappingModel> retrieveHttpResponseMappingsByParameterId(Integer parameterId) throws DynamicPageException {
        try {
            if(retrieveHttpResponseMappingsByParameterIdQuery==null){
                StringBuilder query = new StringBuilder();
                query.append("SELECT * FROM ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_RESPONSE_MAPPING.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.DYN_STEP_HTTP_RESPONSE_MAPPING.PARAMETER_ID).append(" = ?");
                retrieveHttpResponseMappingsByParameterIdQuery = query.toString();
            }
            return jdbcTemplate.query(retrieveHttpResponseMappingsByParameterIdQuery
                    , httpResponseMappingRowMapper
                    , parameterId);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }

    }


}
