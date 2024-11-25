package com.asset.ccat.dynamic_page.operation;

import com.asset.ccat.dynamic_page.constants.StepTypes;
import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import org.springframework.stereotype.Component;

@Component
public class StepOperationFactory {
    private final SPStepOperation spStepOperation;
    private final HTTPStepOperation httpStepOperation;

    public StepOperationFactory(SPStepOperation spStepOperation, HTTPStepOperation httpStepOperation) {
        this.spStepOperation = spStepOperation;
        this.httpStepOperation = httpStepOperation;
    }

    public StepOperation getOperation(Integer stepType) throws DynamicPageException {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Started StepOperationFactory - getOperation");
            CCATLogger.DEBUG_LOGGER.info("Getting operation for step of type" + stepType);
            if (stepType == null) {
                CCATLogger.DEBUG_LOGGER.debug("Failed to get operation, stepType must be supplied");
                throw new DynamicPageException(ErrorCodes.ERROR.MISSING_FIELD, Defines.SEVERITY.ERROR, "stepType");
            } else if (stepType.equals(StepTypes.PROCEDURE.type)) {
                CCATLogger.DEBUG_LOGGER.debug("Returning SP Step Operation");
                return this.spStepOperation;
            } else if (stepType.equals(StepTypes.HTTP.type)) {
                CCATLogger.DEBUG_LOGGER.debug("Returning HTTP Step Operation");
                return this.httpStepOperation;
            } else {
                CCATLogger.DEBUG_LOGGER.debug("Failed to get operation, unsupported operation type");
                throw new DynamicPageException(ErrorCodes.ERROR.UNSUPPORTED_OPERATION_TYPE);
            }
        } finally {
            CCATLogger.DEBUG_LOGGER.info("Finished getting operation for step of type" + stepType);
            CCATLogger.DEBUG_LOGGER.debug("Ended StepOperationFactory - getOperation");
        }
    }
}

