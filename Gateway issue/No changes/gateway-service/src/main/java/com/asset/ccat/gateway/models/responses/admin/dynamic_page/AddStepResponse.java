package com.asset.ccat.gateway.models.responses.admin.dynamic_page;

import com.asset.ccat.gateway.models.admin.dynamic_page.StepModel;

public class AddStepResponse {
    private StepModel addedStep;

    public AddStepResponse() {
    }

    public AddStepResponse(StepModel addedStep) {
        this.addedStep = addedStep;
    }

    public StepModel getAddedStep() {
        return addedStep;
    }

    public void setAddedStep(StepModel addedStep) {
        this.addedStep = addedStep;
    }
}
