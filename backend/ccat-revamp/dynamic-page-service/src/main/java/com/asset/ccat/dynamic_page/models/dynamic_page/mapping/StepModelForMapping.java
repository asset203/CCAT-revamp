package com.asset.ccat.dynamic_page.models.dynamic_page.mapping;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Map;

public class StepModelForMapping {

    private Integer id;
    private Integer pageId;
    private Integer stepType;
    private String stepName;
    private Integer stepOrder;
    private Boolean isHidden;
    private Map<String,Object> stepConfiguration;

    public StepModelForMapping() {
    }

    public StepModelForMapping(Integer id, Integer pageId, Integer stepType, String stepName, Integer stepOrder) {
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

    public Boolean getHidden() {
        return isHidden;
    }

    public void setHidden(Boolean hidden) {
        isHidden = hidden;
    }

    public Map<String, Object> getStepConfiguration() {
        return stepConfiguration;
    }

    public void setStepConfiguration(Map<String, Object> stepConfiguration) {
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
                '}';
    }
}
