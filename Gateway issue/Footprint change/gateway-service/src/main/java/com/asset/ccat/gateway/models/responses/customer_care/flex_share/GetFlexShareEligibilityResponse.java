package com.asset.ccat.gateway.models.responses.customer_care.flex_share;

import com.asset.ccat.gateway.models.shared.FlexShareEligibilityModel;

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
