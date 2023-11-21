package com.asset.ccat.gateway.models.responses.lookup;

import com.asset.ccat.gateway.models.shared.FafIndicatorModel;

import java.util.List;

/**
 * @author mohamed.metwaly
 */
public class GetAllFafIndicatorsResponse {
    private List<FafIndicatorModel> fafIndicators;

    public GetAllFafIndicatorsResponse(List<FafIndicatorModel> fafIndicators) {
        this.fafIndicators = fafIndicators;
    }

    public List<FafIndicatorModel> getFafIndicators() {
        return fafIndicators;
    }

    public void setFafIndicators(List<FafIndicatorModel> fafIndicators) {
        this.fafIndicators = fafIndicators;
    }
}
