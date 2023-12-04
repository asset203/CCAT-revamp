package com.asset.ccat.gateway.models.requests.customer_care.customer_dynamic_page;

import com.asset.ccat.gateway.models.requests.BaseRequest;

import java.util.HashMap;

public class ExecuteDynamicPageStepRequest extends BaseRequest {
    private Integer pageId;
    private Integer privilegeId;
    private Integer stepId;
    private HashMap<String, Object> inputParameters;

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Integer privilegeId) {
        this.privilegeId = privilegeId;
    }

    public Integer getStepId() {
        return stepId;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }

    public HashMap<String, Object> getInputParameters() {
        return inputParameters;
    }

    public void setInputParameters(HashMap<String, Object> inputParameters) {
        this.inputParameters = inputParameters;
    }

    @Override
    public String toString() {
        return "ExecuteDynamicPageStepRequest{" +
                "pageId=" + pageId +
                ", privilegeId=" + privilegeId +
                ", stepId=" + stepId +
                ", inputParameters=" + inputParameters +
                '}';
    }
}
