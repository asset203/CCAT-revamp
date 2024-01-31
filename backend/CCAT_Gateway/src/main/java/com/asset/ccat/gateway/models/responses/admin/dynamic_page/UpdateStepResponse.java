package com.asset.ccat.gateway.models.responses.admin.dynamic_page;

import com.asset.ccat.gateway.models.admin.dynamic_page.StepModel;

public class UpdateStepResponse {
    private StepModel updatedStep;

    public UpdateStepResponse() {
    }

    public UpdateStepResponse(StepModel updatedStep) {
        this.updatedStep = updatedStep;
    }

    public StepModel getUpdatedStep() {
        return updatedStep;
    }

    public void setUpdatedStep(StepModel updatedStep) {
        this.updatedStep = updatedStep;
    }
}
