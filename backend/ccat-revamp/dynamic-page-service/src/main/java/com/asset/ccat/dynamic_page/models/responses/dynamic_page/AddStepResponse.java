package com.asset.ccat.dynamic_page.models.responses.dynamic_page;

import com.asset.ccat.dynamic_page.models.dynamic_page.StepModel;

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
