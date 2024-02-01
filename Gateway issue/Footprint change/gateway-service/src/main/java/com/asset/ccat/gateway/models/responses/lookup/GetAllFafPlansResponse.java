package com.asset.ccat.gateway.models.responses.lookup;

import com.asset.ccat.gateway.models.shared.FafPlanModel;

import java.util.List;

public class GetAllFafPlansResponse {
    private List<FafPlanModel> fafPlans;

    public GetAllFafPlansResponse() {
    }

    public GetAllFafPlansResponse(List<FafPlanModel> fafPlans) {
        this.fafPlans = fafPlans;
    }

    public List<FafPlanModel> getFafPlans() {
        return fafPlans;
    }

    public void setFafPlans(List<FafPlanModel> fafPlans) {
        this.fafPlans = fafPlans;
    }
}
