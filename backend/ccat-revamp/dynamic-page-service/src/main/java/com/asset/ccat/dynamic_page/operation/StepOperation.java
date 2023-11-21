package com.asset.ccat.dynamic_page.operation;

import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.models.dynamic_page.DynamicPageStepOutputModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.StepConfigurationModel;

import java.util.HashMap;
import java.util.List;

public abstract class StepOperation implements Cloneable {
    protected StepConfigurationModel stepConfiguration;

    public abstract List<DynamicPageStepOutputModel> doOperate(HashMap<String, Object> inputParameters) throws DynamicPageException;

    @Override
    public StepOperation clone() throws CloneNotSupportedException {
        return (StepOperation) super.clone();
    }

    public StepOperation setStepConfiguration(StepConfigurationModel stepConfiguration) {
        this.stepConfiguration = stepConfiguration;
        return this;
    }
}
