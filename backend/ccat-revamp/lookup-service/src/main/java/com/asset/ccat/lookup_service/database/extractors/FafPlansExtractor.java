package com.asset.ccat.lookup_service.database.extractors;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.models.FafPlanModel;
import com.asset.ccat.lookup_service.models.responses.AdmServiceClassResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class FafPlansExtractor implements ResultSetExtractor<HashMap<Integer, FafPlanModel>> {

    @Override
    public HashMap<Integer, FafPlanModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
        HashMap<Integer, FafPlanModel> fafPlans = new HashMap<>();
        Integer planId;
        FafPlanModel planModel;
        while (rs.next()) {
            planModel = new FafPlanModel();
            planId = rs.getInt(DatabaseStructs.ADM_FAF_PLANS.PLAN_ID);

            planModel.setPlanId(planId);
            planModel.setFafIndicatorId(rs.getInt(DatabaseStructs.ADM_FAF_PLANS.INDICATOR_ID));
            planModel.setMappedFafIndicatorId(rs.getInt(DatabaseStructs.ADM_FAF_INDICATORS.MAPPED_INDICATOR_ID));
            planModel.setName(rs.getString(DatabaseStructs.ADM_FAF_PLANS.NAME));

            fafPlans.put(planId, planModel);
        }
        return fafPlans;
    }
}
