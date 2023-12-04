package com.asset.ccat.air_service.controllers;

import com.asset.ccat.air_service.defines.Defines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.requests.customer_care.DeleteUsageThresholdsRequest;
import com.asset.ccat.air_service.models.requests.customer_care.GetAllUsageCountersRequest;
import com.asset.ccat.air_service.models.requests.customer_care.UpdateUsageCountersRequest;
import com.asset.ccat.air_service.models.responses.BaseResponse;
import com.asset.ccat.air_service.models.responses.customer_care.GetAllUsageCountersResponse;
import com.asset.ccat.air_service.models.shared.ServiceInfo;
import com.asset.ccat.air_service.services.UsageCountersService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Assem.Hassan
 */
@RestController
@RequestMapping(path = Defines.ContextPaths.USAGE_COUNTERS)
public class UsageCountersController {

    @Autowired
    Environment environment;

    @Autowired
    private UsageCountersService usageCountersService;

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    @CrossOrigin(origins = "*")
    public BaseResponse<GetAllUsageCountersResponse> getUsageThresholdsAndCounters(HttpServletRequest req,
                                                                                   @RequestBody GetAllUsageCountersRequest getAllUsageCountersRequest) throws AIRServiceException, AIRException, UnknownHostException {
        CCATLogger.DEBUG_LOGGER.info("Received Get All Usage Counters Request [" + getAllUsageCountersRequest + "]");
        ThreadContext.put("sessionId", getAllUsageCountersRequest.getSessionId());
        ThreadContext.put("requestId", getAllUsageCountersRequest.getRequestId());
        GetAllUsageCountersResponse response = usageCountersService.getUsageCounters(getAllUsageCountersRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Usage Counters Request Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                new ServiceInfo(InetAddress.getLocalHost().getHostAddress(), environment.getProperty("server.port")),
                response);
    }


    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<BaseResponse> UpdateUsageThresholdsAndCounters(HttpServletRequest req,
                                                                         @RequestBody UpdateUsageCountersRequest updateUsageCountersRequest) throws AIRServiceException, UnknownHostException {
        CCATLogger.DEBUG_LOGGER.info("Received Update UsageThresholds And Counters Request [" + updateUsageCountersRequest + "]");
        ThreadContext.put("sessionId", updateUsageCountersRequest.getSessionId());
        ThreadContext.put("requestId", updateUsageCountersRequest.getRequestId());
        usageCountersService.addAndUpdateUsageCounters(updateUsageCountersRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished Update UsageThresholds And Counters Request Successfully");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                new ServiceInfo(InetAddress.getLocalHost().getHostAddress(), environment.getProperty("server.port")),
                null), HttpStatus.OK);
    }


    @PostMapping(value = Defines.ContextPaths.USAGE_THRESHOLDS + Defines.WEB_ACTIONS.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse deleteUsageThresholds(@RequestBody DeleteUsageThresholdsRequest deleteUsageThresholdsRequest) throws AIRException, AIRServiceException, UnknownHostException {
        CCATLogger.DEBUG_LOGGER.info("Received Delete UsageThresholds Request [" + deleteUsageThresholdsRequest + "]");
        ThreadContext.put("sessionId", deleteUsageThresholdsRequest.getSessionId());
        ThreadContext.put("requestId", deleteUsageThresholdsRequest.getRequestId());
        usageCountersService.deleteUsageThresholds(deleteUsageThresholdsRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Delete Usage Thresholds Request Successfully");

        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS, "success", 0,
                new ServiceInfo(InetAddress.getLocalHost().getHostAddress(), environment.getProperty("server.port")),
                null);
    }
}
