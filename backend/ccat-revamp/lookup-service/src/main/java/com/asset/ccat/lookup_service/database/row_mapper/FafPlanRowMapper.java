package com.asset.ccat.lookup_service.database.row_mapper;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.models.FafPlanModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FafPlanRowMapper  implements RowMapper<FafPlanModel> {
    @Override
    public FafPlanModel mapRow(ResultSet rs, int rowNum) throws SQLException {

        FafPlanModel fafPlan = new FafPlanModel();
        fafPlan.setPlanId(rs.getInt(DatabaseStructs.ADM_FAF_PLANS.PLAN_ID));
        fafPlan.setFafIndicatorId(rs.getInt(DatabaseStructs.ADM_FAF_PLANS.INDICATOR_ID));
        fafPlan.setName(rs.getString(DatabaseStructs.ADM_FAF_PLANS.NAME));

        return fafPlan;
    }
}