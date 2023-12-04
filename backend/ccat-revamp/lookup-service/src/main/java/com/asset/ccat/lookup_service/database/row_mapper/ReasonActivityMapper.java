package com.asset.ccat.lookup_service.database.row_mapper;

import com.asset.ccat.lookup_service.constants.ActivityType;
import com.asset.ccat.lookup_service.models.ReasonActivityModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReasonActivityMapper implements RowMapper<ReasonActivityModel> {


    @Override
    public ReasonActivityModel mapRow(ResultSet rs, int rowNum) throws SQLException {

        ReasonActivityModel reason = new ReasonActivityModel();
        reason.setActivityId(rs.getInt("ACTIVITY_ID"));
        reason.setParentId(rs.getInt("PARENT_ID"));
        reason.setActivityName(rs.getString("ACTIVITY_NAME"));
        reason.setActivityType(ActivityType.valueOf( rs.getString("ACTIVITY_TYPE")));

        return reason;

    }

}
