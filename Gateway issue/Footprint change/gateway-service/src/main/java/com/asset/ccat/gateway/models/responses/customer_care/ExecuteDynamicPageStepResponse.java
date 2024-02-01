package com.asset.ccat.gateway.models.responses.customer_care;

import com.asset.ccat.gateway.models.shared.DynamicPageStepOutputModel;

import java.util.List;

public class ExecuteDynamicPageStepResponse {

    private List<DynamicPageStepOutputModel> outputParameters;

    public ExecuteDynamicPageStepResponse() {
    }

    public ExecuteDynamicPageStepResponse(List<DynamicPageStepOutputModel> outputParameters) {
        this.outputParameters = outputParameters;
    }

    public List<DynamicPageStepOutputModel> getOutputParameters() {
        return outputParameters;
    }

    public void setOutputParameters(List<DynamicPageStepOutputModel> outputParameters) {
        this.outputParameters = outputParameters;
    }

    @Override
    public String toString() {
        return "ExecuteDynamicPageStepResponse{" +
                "outputParameters=" + outputParameters +
                '}';
    }
}
