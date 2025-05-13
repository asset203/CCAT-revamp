package com.asset.ccat.dynamic_page.controllers;

import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.DynamicPageStepOutputModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.StepModel;
import com.asset.ccat.dynamic_page.models.requests.dynamic_page.AddStepRequest;
import com.asset.ccat.dynamic_page.models.requests.dynamic_page.DeleteStepRequest;
import com.asset.ccat.dynamic_page.models.requests.dynamic_page.ExecuteDynamicPageStepRequest;
import com.asset.ccat.dynamic_page.models.requests.dynamic_page.UpdateStepRequest;
import com.asset.ccat.dynamic_page.models.responses.BaseResponse;
import com.asset.ccat.dynamic_page.services.StepService;
import com.asset.ccat.dynamic_page.services.execution.StepExecutionService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = Defines.ContextPaths.DYNAMIC_PAGE_STEP)
public class StepController {

    @Autowired
    private StepService stepService;

    @Autowired
    private StepExecutionService stepExecutionService;

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public ResponseEntity<BaseResponse<StepModel>> addStep(
            @RequestBody AddStepRequest addStepRequest) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started StepController - addStep()");
        ThreadContext.put("sessionId", addStepRequest.getSessionId());
        ThreadContext.put("requestId", addStepRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received add step request");
        StepModel step = stepService.addStep(addStepRequest);
        CCATLogger.DEBUG_LOGGER.info("Add Step Request finished successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ending StepController - addStep()");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", 0,
                step), HttpStatus.OK);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public ResponseEntity<BaseResponse<StepModel>> updateStep(
            @RequestBody UpdateStepRequest updateStepRequest) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started StepController - updateStep() Request is : "+updateStepRequest);
        ThreadContext.put("sessionId", updateStepRequest.getSessionId());
        ThreadContext.put("requestId", updateStepRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received update step request");
        StepModel step = stepService.updateStep(updateStepRequest);

        CCATLogger.DEBUG_LOGGER.info("Update Step Request finished successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ending StepController - updateStep()");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", 0,
                step), HttpStatus.OK);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public ResponseEntity<BaseResponse> deleteStep(
            @RequestBody DeleteStepRequest deleteStepRequest) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started StepController - deleteStep()");
        ThreadContext.put("sessionId", deleteStepRequest.getSessionId());
        ThreadContext.put("requestId", deleteStepRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received delete step request");
        stepService.deleteStep(deleteStepRequest, false);
        CCATLogger.DEBUG_LOGGER.info("Delete Step Request finished successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ending StepController - deleteStep()");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", 0,
                null), HttpStatus.OK);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.EXECUTE)
    public BaseResponse<List<DynamicPageStepOutputModel>> executeStep(@RequestBody ExecuteDynamicPageStepRequest request) throws DynamicPageException {
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received execute Dynamic Page step request");
        List<DynamicPageStepOutputModel> response = stepExecutionService.executeStep(request);
        CCATLogger.DEBUG_LOGGER.info("Execute Dynamic Page step request finished successfully");
        CCATLogger.DEBUG_LOGGER.debug("Finished StepController - executeStep()");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                response);
    }

}
