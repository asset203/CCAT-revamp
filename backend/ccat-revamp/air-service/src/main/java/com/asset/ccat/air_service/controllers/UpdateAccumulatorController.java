package com.asset.ccat.air_service.controllers;

import com.asset.ccat.air_service.defines.Defines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.requests.UpdateAccumulatorsRequest;
import com.asset.ccat.air_service.models.responses.BaseResponse;
import com.asset.ccat.air_service.models.shared.ServiceInfo;
import com.asset.ccat.air_service.services.UpdateAccumulatorService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Mahmoud Shehab
 */
@RestController
public class UpdateAccumulatorController {

    @Autowired
    Environment environment;

    @Autowired
    UpdateAccumulatorService accumulatorService;

    @PostMapping(value = Defines.ContextPaths.ACCUMULATORS + Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse<String> updateBalanceAndDates(HttpServletRequest req,
                                              @RequestBody UpdateAccumulatorsRequest request) throws AIRServiceException, AIRException, UnknownHostException {
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Accumulators Request [{}]", request);
        accumulatorService.updateAccumulators(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Update Accumulators Request Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                new ServiceInfo(InetAddress.getLocalHost().getHostAddress(), environment.getProperty("server.port")),
                null);
    }
}
