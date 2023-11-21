package com.asset.ccat.dynamic_page.models.requests.dynamic_page;

import com.asset.ccat.dynamic_page.models.requests.BaseRequest;

public class DeleteStepRequest extends BaseRequest {

    private Integer pageId;
    private Integer stepId;

    public DeleteStepRequest() {
    }

    public DeleteStepRequest(Integer pageId, Integer stepId) {
        this.pageId = pageId;
        this.stepId = stepId;
    }

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
}
