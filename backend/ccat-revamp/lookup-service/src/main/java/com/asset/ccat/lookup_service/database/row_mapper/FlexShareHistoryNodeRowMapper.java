package com.asset.ccat.lookup_service.database.row_mapper;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.models.ods_models.FlexShareHistoryNodeModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FlexShareHistoryNodeRowMapper implements RowMapper<FlexShareHistoryNodeModel> {

    @Override
    public FlexShareHistoryNodeModel mapRow(ResultSet rs, int i) throws SQLException {
        FlexShareHistoryNodeModel flexShareHistoryNode = new FlexShareHistoryNodeModel();
        flexShareHistoryNode.setId(rs.getInt(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.ID));
        flexShareHistoryNode.setAddress(rs.getString(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.ADDRESS));
        flexShareHistoryNode.setPort(rs.getInt(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.PORT));
        flexShareHistoryNode.setUsername(rs.getString(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.USERNAME));
        flexShareHistoryNode.setNumberOfSessions(rs.getInt(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.NUMBER_OF_SESSIONS));
        flexShareHistoryNode.setConcurrentCalls(rs.getInt(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.CONCURRENT_CALLS));
        flexShareHistoryNode.setConnectionTimeout(rs.getInt(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.CONCURRENT_CALLS));
        flexShareHistoryNode.setExtraConf(rs.getString(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.EXTRA_CONF));
        flexShareHistoryNode.setSchema(rs.getString(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.SCHEMA));

        return flexShareHistoryNode;
    }
}
