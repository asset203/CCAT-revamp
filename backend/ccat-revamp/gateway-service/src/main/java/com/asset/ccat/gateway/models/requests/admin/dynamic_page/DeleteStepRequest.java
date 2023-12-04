package com.asset.ccat.gateway.models.requests.admin.dynamic_page;

import com.asset.ccat.gateway.models.requests.BaseRequest;

public class DeleteStepRequest extends BaseRequest {

    private Integer pageId;
    private Integer stepId;

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getStepId() {
        return stepId;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }

    @Override
    public String toString() {
        return "DeleteStepRequest{" +
                "pageId=" + pageId +
                ", stepId=" + stepId +
                '}';
    }
}
