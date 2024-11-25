package com.asset.ccat.dynamic_page.database.row_mapper;

import com.asset.ccat.dynamic_page.defines.DatabaseStructs;
import com.asset.ccat.dynamic_page.models.dynamic_page.ProcedureCursorMappingModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class CursorMappingRowMapper implements RowMapper<ProcedureCursorMappingModel> {


    @Override
    public ProcedureCursorMappingModel mapRow(ResultSet resultSet, int i) throws SQLException {
        ProcedureCursorMappingModel procedureCursorMappingModel = new ProcedureCursorMappingModel();
        procedureCursorMappingModel.setId(resultSet.getInt(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.ID));
        procedureCursorMappingModel.setParameterId(resultSet.getInt(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.PARAMETER_ID));
        procedureCursorMappingModel.setActualColumnName(resultSet.getString(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.ACTUAL_COLUMN_NAME));
        procedureCursorMappingModel.setDisplayColumnName(resultSet.getString(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.DISPLAY_COLUMN_NAME));
        return procedureCursorMappingModel;
    }
}
