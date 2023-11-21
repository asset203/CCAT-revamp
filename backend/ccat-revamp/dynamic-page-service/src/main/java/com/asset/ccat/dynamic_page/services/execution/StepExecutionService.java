package com.asset.ccat.dynamic_page.services.execution;

import com.asset.ccat.dynamic_page.cache.DynamicPagesCache;
import com.asset.ccat.dynamic_page.constants.StepTypes;
import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.DynamicPageModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.DynamicPageStepOutputModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.StepConfigurationModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.StepModel;
import com.asset.ccat.dynamic_page.models.requests.dynamic_page.ExecuteDynamicPageStepRequest;
import com.asset.ccat.dynamic_page.operation.StepOperation;
import com.asset.ccat.dynamic_page.operation.StepOperationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StepExecutionService {

    @Autowired
    private DynamicPagesCache dynamicPagesCache;

    @Autowired
    private StepOperationFactory stepOperationFactory;

    public List<DynamicPageStepOutputModel> executeStep(ExecuteDynamicPageStepRequest request) throws DynamicPageException {
            CCATLogger.DEBUG_LOGGER.debug("Started StepExecutionService - executeStep()");
        CCATLogger.DEBUG_LOGGER.info("Start serving execute dynamic page step request");


        try {
            CCATLogger.DEBUG_LOGGER.debug("Retrieving step configuration from cache");
            DynamicPageModel page = dynamicPagesCache.getDynamicPages().get(request.getPrivilegeId());
            if (page == null) {
                CCATLogger.DEBUG_LOGGER.debug("Page with id [" + request.getPageId() + "] is not found");
                throw new DynamicPageException(ErrorCodes.ERROR.DYNAMIC_PAGE_NOT_FOUND);
            }

            List<StepModel> steps = page.getSteps().stream()
                    .filter((step) -> step.getId().equals(request.getStepId()))
                    .collect(Collectors.toList());

            if (steps == null || steps.isEmpty()) {
                CCATLogger.DEBUG_LOGGER.debug("Step with id [" + request.getStepId() + "] is not found");
                throw new DynamicPageException(ErrorCodes.ERROR.STEP_NOT_FOUND);
            }

            StepModel step = steps.get(0);
            StepConfigurationModel configurationModel = step.getStepConfiguration();

            CCATLogger.DEBUG_LOGGER.debug("Get do operation for step of type [" + StepTypes.getStepType(step.getStepType()) + "]");
            StepOperation operation = stepOperationFactory.getOperation(step.getStepType())
                    .clone()
                    .setStepConfiguration(configurationModel);

            CCATLogger.DEBUG_LOGGER.debug("Start [" + StepTypes.getStepType(step.getStepType()) + "] operation");
            List<DynamicPageStepOutputModel> result = operation.doOperate(request.getInputParameters());

            CCATLogger.DEBUG_LOGGER.info("Finished serving execute dynamic page step request");
            CCATLogger.DEBUG_LOGGER.debug("Ended StepExecutionService - executeStep()");
            return result;
        } catch (DynamicPageException ex) {
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("Exception occured while serving execute step request >> " + ex.getMessage());
            CCATLogger.DEBUG_LOGGER.error("Exception occured while serving execute step request >> " + ex.getMessage(), ex);
            throw new DynamicPageException(ErrorCodes.ERROR.STEP_EXECUTION_FAILED, Defines.SEVERITY.ERROR);
        }
    }


}
