package com.asset.ccat.dynamic_page.operation;

import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.*;
import com.asset.ccat.dynamic_page.services.execution.SPStepExecutionService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SPStepOperation extends StepOperation{


    private final SPStepExecutionService spStepExecutionService;

    public SPStepOperation(SPStepExecutionService spStepExecutionService) {
        this.spStepExecutionService = spStepExecutionService;
    }

    @Override
    public List<DynamicPageStepOutputModel> doOperate(HashMap<String, Object> inputParameters) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started SPStepOperation - doOperate()");
        CCATLogger.DEBUG_LOGGER.info("Start executing stored procedure step operation");

        ProcedureConfigurationModel configurationModel = (ProcedureConfigurationModel) this.stepConfiguration;
        List<DynamicPageStepOutputModel> outputList = spStepExecutionService.execute(configurationModel, inputParameters);

        CCATLogger.DEBUG_LOGGER.info("Finished executing stored procedure step operation");
        CCATLogger.DEBUG_LOGGER.debug("Ended SPStepOperation - doOperate()");
        return outputList;
    }
}
