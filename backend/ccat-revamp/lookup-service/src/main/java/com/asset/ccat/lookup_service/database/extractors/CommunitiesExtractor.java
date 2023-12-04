package com.asset.ccat.lookup_service.database.extractors;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.models.CommunityAdminModel;
import com.asset.ccat.lookup_service.models.FafPlanModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@Component
public class CommunitiesExtractor implements ResultSetExtractor<HashMap<Integer, CommunityAdminModel>> {

    @Override
    public HashMap<Integer, CommunityAdminModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
        HashMap<Integer, CommunityAdminModel> communities = new HashMap<>();
        CommunityAdminModel community;
        while (rs.next()) {
            community = new CommunityAdminModel();
            community.setCommunityId(rs.getInt(DatabaseStructs.ADM_COMMUNITIES.COMMUNITY_ID));
            community.setCommunityDescription(rs.getString(DatabaseStructs.ADM_COMMUNITIES.COMMUNITY_DESCRIPTION));
            communities.put(community.getCommunityId(), community);
        }
        return communities;
    }
}