package com.asset.ccat.dynamic_page.operation;

import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.DynamicPageStepOutputModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.HttpConfigurationModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.ProcedureConfigurationModel;
import com.asset.ccat.dynamic_page.services.execution.HTTPStepExecutionService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class HTTPStepOperation extends StepOperation {

    private final HTTPStepExecutionService stepExecutionService;

    public HTTPStepOperation(HTTPStepExecutionService stepExecutionService) {
        this.stepExecutionService = stepExecutionService;
    }

    @Override
    public List<DynamicPageStepOutputModel> doOperate(HashMap<String, Object> inputParameters) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started HTTPStepOperation - doOperate()");
        CCATLogger.DEBUG_LOGGER.info("Start executing HTTP step operation");

        HttpConfigurationModel configurationModel = (HttpConfigurationModel) this.stepConfiguration;
        List<DynamicPageStepOutputModel> outputList = stepExecutionService.execute(configurationModel, inputParameters);

        CCATLogger.DEBUG_LOGGER.info("Finished executing HTTP step operation");
        CCATLogger.DEBUG_LOGGER.debug("Ended HTTPStepOperation - doOperate()");
        return outputList;
    }
}
