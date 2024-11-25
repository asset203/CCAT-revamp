package com.asset.ccat.dynamic_page.database.row_mapper;

import com.asset.ccat.dynamic_page.defines.DatabaseStructs;
import com.asset.ccat.dynamic_page.models.dynamic_page.HttpResponseMappingModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.ProcedureCursorMappingModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class HttpResponseMappingRowMapper implements RowMapper<HttpResponseMappingModel> {


    @Override
    public HttpResponseMappingModel mapRow(ResultSet resultSet, int i) throws SQLException {
        HttpResponseMappingModel httpResponseMappingModel = new HttpResponseMappingModel();
        httpResponseMappingModel.setId(resultSet.getInt(DatabaseStructs.DYN_STEP_HTTP_RESPONSE_MAPPING.ID));
        httpResponseMappingModel.setParameterId(resultSet.getInt(DatabaseStructs.DYN_STEP_HTTP_RESPONSE_MAPPING.PARAMETER_ID));
        httpResponseMappingModel.setHeaderName(resultSet.getString(DatabaseStructs.DYN_STEP_HTTP_RESPONSE_MAPPING.HEADER_NAME));
        httpResponseMappingModel.setHeaderDisplayName(resultSet.getString(DatabaseStructs.DYN_STEP_HTTP_RESPONSE_MAPPING.HEADER_DISPLAY_NAME));
        return httpResponseMappingModel;
    }
}
