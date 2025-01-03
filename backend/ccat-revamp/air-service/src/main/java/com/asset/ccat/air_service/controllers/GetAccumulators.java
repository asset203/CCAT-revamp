package com.asset.ccat.air_service.controllers;

import com.asset.ccat.air_service.defines.Defines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.AccumulatorModel;
import com.asset.ccat.air_service.models.requests.SubscriberRequest;
import com.asset.ccat.air_service.models.responses.BaseResponse;
import com.asset.ccat.air_service.models.shared.ServiceInfo;
import com.asset.ccat.air_service.services.GetAccumulatorsService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.List;

/**
 * @author Mahmoud Shehab
 */
@RestController
public class GetAccumulators {

    @Autowired
    Environment environment;

    @Autowired
    GetAccumulatorsService getAccumulatorsService;

    @PostMapping(value = Defines.ContextPaths.ACCUMULATORS + Defines.WEB_ACTIONS.GET)
    public BaseResponse<List<AccumulatorModel>> getAccumulators(HttpServletRequest req, @RequestBody SubscriberRequest request) throws AuthenticationException, AIRServiceException, AIRException, Exception {
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Subscriber Request [{}]", request);
        List<AccumulatorModel> response = getAccumulatorsService.getAccumulators(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Subscriber request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                new ServiceInfo(InetAddress.getLocalHost().getHostAddress(), environment.getProperty("server.port")),
                response);
    }
}
