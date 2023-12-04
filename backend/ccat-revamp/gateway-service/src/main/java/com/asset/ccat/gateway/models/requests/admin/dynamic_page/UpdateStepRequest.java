package com.asset.ccat.gateway.models.requests.admin.dynamic_page;

import com.asset.ccat.gateway.models.admin.dynamic_page.StepModel;
import com.asset.ccat.gateway.models.requests.BaseRequest;

public class UpdateStepRequest extends BaseRequest {

    private Integer pageId;
    private StepModel step;

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public StepModel getStep() {
        return step;
    }

    public void setStep(StepModel step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "UpdateStepRequest{" +
                "pageId=" + pageId +
                ", step=" + step +
                '}';
    }
}
