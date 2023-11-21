package com.asset.ccat.lookup_service.database.row_mapper;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.models.ods_models.DSSNodeModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DSSNodesRowMapper implements RowMapper<DSSNodeModel> {
    @Override
    public DSSNodeModel mapRow(ResultSet rs, int i) throws SQLException {
        DSSNodeModel dssNodeModel = new DSSNodeModel();
        dssNodeModel.setId(rs.getInt(DatabaseStructs.DSS_NODES.ID));
        dssNodeModel.setAddress(rs.getString(DatabaseStructs.DSS_NODES.ADDRESS));
        dssNodeModel.setPort(rs.getInt(DatabaseStructs.DSS_NODES.PORT));
        dssNodeModel.setUserName(rs.getString(DatabaseStructs.DSS_NODES.USER_NAME));
        dssNodeModel.setNumberOfSessions(rs.getInt(DatabaseStructs.DSS_NODES.NUMBER_OF_SESSIONS));
        dssNodeModel.setConcurrentCalls(rs.getInt(DatabaseStructs.DSS_NODES.CONCURRENT_CALLS));
        dssNodeModel.setConnectionTimeout(rs.getInt(DatabaseStructs.DSS_NODES.CONCURRENT_CALLS));
        dssNodeModel.setExtraConf(rs.getString(DatabaseStructs.DSS_NODES.EXTRA_CONF));
        dssNodeModel.setSchema(rs.getString(DatabaseStructs.DSS_NODES.SCHEMA));

        return dssNodeModel;
    }
}
