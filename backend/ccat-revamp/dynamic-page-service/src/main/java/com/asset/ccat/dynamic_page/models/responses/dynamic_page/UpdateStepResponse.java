package com.asset.ccat.dynamic_page.models.responses.dynamic_page;

import com.asset.ccat.dynamic_page.models.dynamic_page.StepModel;

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
