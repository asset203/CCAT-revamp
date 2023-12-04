package com.asset.ccat.lookup_service.database.row_mapper;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.models.CommunityAdminModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommunityAdminRowMapper implements RowMapper<CommunityAdminModel> {
    @Override
    public CommunityAdminModel mapRow(ResultSet rs, int rowNum) throws SQLException {

        CommunityAdminModel community = new CommunityAdminModel();
        community.setCommunityId(rs.getInt(DatabaseStructs.ADM_COMMUNITIES.COMMUNITY_ID));
        community.setCommunityDescription(rs.getString(DatabaseStructs.ADM_COMMUNITIES.COMMUNITY_DESCRIPTION));

        return community;
    }
}
