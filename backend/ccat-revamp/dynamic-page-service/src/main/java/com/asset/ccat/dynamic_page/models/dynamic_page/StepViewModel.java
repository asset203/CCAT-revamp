package com.asset.ccat.dynamic_page.models.dynamic_page;

import java.util.List;

public class StepViewModel {

    private Integer stepId;
    private String stepName;
    private Integer stepType;
    private Integer stepOrder;
    private Boolean isHidden;
    private List<ParameterModel> stepInputParameters;

    public StepViewModel() {
    }

    public StepViewModel(Integer stepId, String stepName, Integer stepType, Integer stepOrder, List<ParameterModel> stepInputParameters) {
        this.stepId = stepId;
        this.stepName = stepName;
        this.stepType = stepType;
        this.stepOrder = stepOrder;
        this.stepInputParameters = stepInputParameters;
    }

    public Integer getStepId() {
        return stepId;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public Integer getStepType() {
        return stepType;
    }

    public void setStepType(Integer stepType) {
        this.stepType = stepType;
    }

    public Integer getStepOrder() {
        return stepOrder;
    }

    public void setStepOrder(Integer stepOrder) {
        this.stepOrder = stepOrder;
    }

    public List<ParameterModel> getStepInputParameters() {
        return stepInputParameters;
    }

    public void setStepInputParameters(List<ParameterModel> stepInputParameters) {
        this.stepInputParameters = stepInputParameters;
    }

    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }
}
