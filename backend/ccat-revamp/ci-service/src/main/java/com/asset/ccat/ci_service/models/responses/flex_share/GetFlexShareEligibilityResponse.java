package com.asset.ccat.ci_service.models.responses.flex_share;


import com.asset.ccat.ci_service.models.shared.FlexShareEligibilityModel;

public class GetFlexShareEligibilityResponse {

    private FlexShareEligibilityModel flexShareEligibilityModel;

    public GetFlexShareEligibilityResponse() {
    }

    public GetFlexShareEligibilityResponse(FlexShareEligibilityModel flexShareEligibilityModel) {
        this.flexShareEligibilityModel = flexShareEligibilityModel;
    }

    public FlexShareEligibilityModel getFlexShareEligibilityModel() {
        return flexShareEligibilityModel;
    }

    public void setFlexShareEligibilityModel(FlexShareEligibilityModel flexShareEligibilityModel) {
        this.flexShareEligibilityModel = flexShareEligibilityModel;
    }

    @Override
    public String toString() {
        return "GetFlexShareEligibilityResponse{" +
                "flexShareEligibilityModel=" + flexShareEligibilityModel +
                '}';
    }
}
