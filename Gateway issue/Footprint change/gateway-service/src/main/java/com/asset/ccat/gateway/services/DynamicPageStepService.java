package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.admin.dynamic_page.StepModel;
import com.asset.ccat.gateway.models.requests.admin.dynamic_page.AddStepRequest;
import com.asset.ccat.gateway.models.requests.admin.dynamic_page.DeleteStepRequest;
import com.asset.ccat.gateway.models.requests.admin.dynamic_page.UpdateStepRequest;
import com.asset.ccat.gateway.models.requests.customer_care.customer_dynamic_page.ViewDynamicPageRequest;
import com.asset.ccat.gateway.models.responses.admin.dynamic_page.AddStepResponse;
import com.asset.ccat.gateway.models.responses.admin.dynamic_page.UpdateStepResponse;
import com.asset.ccat.gateway.models.responses.customer_care.ViewDynamicPageResponse;
import com.asset.ccat.gateway.proxy.DynamicPageProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DynamicPageStepService {

    @Autowired
    private DynamicPageProxy dynamicPageProxy;

    public AddStepResponse addStep(AddStepRequest addStepRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageStepService - addStep()");
        CCATLogger.DEBUG_LOGGER.info("Start serving add dynamic page step request");
        StepModel step = dynamicPageProxy.addStep(addStepRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished serving add dynamic page step request");
        CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPageStepService - addStep()");
        return new AddStepResponse(step);
    }

    public UpdateStepResponse updateStep(UpdateStepRequest updateStepRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageStepService - updateStep()");
        CCATLogger.DEBUG_LOGGER.info("Start serving update dynamic page step request");
        StepModel step = dynamicPageProxy.updateStep(updateStepRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished serving update dynamic page step request");
        CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPageStepService - updateStep()");
        return new UpdateStepResponse(step);
    }

    public void deleteStep(DeleteStepRequest deleteStepRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageStepService - deleteStep()");
        CCATLogger.DEBUG_LOGGER.info("Start serving delete dynamic page step request");
        dynamicPageProxy.deleteStep(deleteStepRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished serving delete dynamic page step request");
        CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPageStepService - updateStep()");
    }

}
