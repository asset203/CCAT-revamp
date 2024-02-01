package com.asset.ccat.gateway.models.admin.dynamic_page;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author assem.hassan
 */
public class StepModel {

    private Integer id;
    private Integer pageId;
    private Integer stepType;
    private String stepName;
    private Integer stepOrder;
    private Boolean isHidden;
    private StepConfigurationModel stepConfiguration;

    public StepModel() {
    }

    public StepModel(Integer id, Integer pageId, Integer stepType, String stepName, Integer stepOrder) {
        this.id = id;
        this.pageId = pageId;
        this.stepType = stepType;
        this.stepName = stepName;
        this.stepOrder = stepOrder;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getStepType() {
        return stepType;
    }

    public void setStepType(Integer stepType) {
        this.stepType = stepType;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public Integer getStepOrder() {
        return stepOrder;
    }

    public void setStepOrder(Integer stepOrder) {
        this.stepOrder = stepOrder;
    }

    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "stepType", include = JsonTypeInfo.As.EXTERNAL_PROPERTY)
    @JsonSubTypes({
            @JsonSubTypes.Type(value = ProcedureConfigurationModel.class, name = "1"),
            @JsonSubTypes.Type(value = HttpConfigurationModel.class, name = "2")
    })
    public StepConfigurationModel getStepConfiguration() {
        return stepConfiguration;
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "stepType", include = JsonTypeInfo.As.EXTERNAL_PROPERTY)
    @JsonSubTypes({
            @JsonSubTypes.Type(value = ProcedureConfigurationModel.class, name = "1"),
            @JsonSubTypes.Type(value = HttpConfigurationModel.class, name = "2")
    })
    public void setStepConfiguration(StepConfigurationModel stepConfiguration) {
        this.stepConfiguration = stepConfiguration;
    }

    @Override
    public String toString() {
        return "StepModel{" +
                "id=" + id +
                ", pageId=" + pageId +
                ", stepType=" + stepType +
                ", stepName='" + stepName + '\'' +
                ", stepOrder=" + stepOrder +
                ", isHidden=" + isHidden +
                ", stepConfiguration=" + stepConfiguration +
                '}';
    }
}
