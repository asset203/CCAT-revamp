package com.asset.ccat.dynamic_page.models.requests.dynamic_page;


import com.asset.ccat.dynamic_page.models.dynamic_page.StepModel;
import com.asset.ccat.dynamic_page.models.requests.BaseRequest;

public class AddStepRequest extends BaseRequest {

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
}
