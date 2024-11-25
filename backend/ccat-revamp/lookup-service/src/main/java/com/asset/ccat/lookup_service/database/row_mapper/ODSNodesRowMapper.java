package com.asset.ccat.lookup_service.database.row_mapper;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.models.ods_models.ODSNodeModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ODSNodesRowMapper implements RowMapper<ODSNodeModel> {
    @Override
    public ODSNodeModel mapRow(ResultSet rs, int i) throws SQLException {
        ODSNodeModel odsNodeModel = new ODSNodeModel();
        odsNodeModel.setId(rs.getInt(DatabaseStructs.ODS_NODES.ID));
        odsNodeModel.setAddress(rs.getString(DatabaseStructs.ODS_NODES.ADDRESS));
        odsNodeModel.setPort(rs.getInt(DatabaseStructs.ODS_NODES.PORT));
        odsNodeModel.setUserName(rs.getString(DatabaseStructs.ODS_NODES.USER_NAME));
        odsNodeModel.setNumberOfSessions(rs.getInt(DatabaseStructs.ODS_NODES.NUMBER_OF_SESSIONS));
        odsNodeModel.setConcurrentCalls(rs.getInt(DatabaseStructs.ODS_NODES.CONCURRENT_CALLS));
        odsNodeModel.setConnectionTimeout(rs.getInt(DatabaseStructs.ODS_NODES.CONNECTION_TIMEOUT));
        odsNodeModel.setExtraConf(rs.getString(DatabaseStructs.ODS_NODES.EXTRA_CONF));
        odsNodeModel.setSchema(rs.getString(DatabaseStructs.ODS_NODES.SCHEMA));

        return odsNodeModel;
    }
}
