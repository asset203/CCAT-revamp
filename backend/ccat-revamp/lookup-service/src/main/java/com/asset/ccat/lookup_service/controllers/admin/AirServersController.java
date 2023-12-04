package com.asset.ccat.lookup_service.controllers.admin;

import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.AIRServer;
import com.asset.ccat.lookup_service.models.BaseResponse;
import com.asset.ccat.lookup_service.models.requests.air_servers.*;
import com.asset.ccat.lookup_service.models.requests.ods_nodes.*;
import com.asset.ccat.lookup_service.models.responses.air_servers.GetAirServerResponse;
import com.asset.ccat.lookup_service.models.responses.air_servers.GetAllAirServersResponse;
import com.asset.ccat.lookup_service.services.AirServersService;
import com.asset.ccat.lookup_service.util.Utils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Assem.Hassan
 */
@RestController
@RequestMapping(value = Defines.ContextPaths.AIR_SERVERS)
public class AirServersController {

    @Autowired
    private AirServersService airServersService;


    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllAirServersResponse> getAllAirServers(@RequestBody GetAllAirServersRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Started Getting All AirServers");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        GetAllAirServersResponse response = airServersService.retrieveAllAirServers();
        CCATLogger.DEBUG_LOGGER.info("All AirServers are Retrieved Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetAirServerResponse> getAirServersById(
            @RequestBody GetAirServerRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Started Getting AirServer by ID");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        GetAirServerResponse response = airServersService.retrieveAirServerById(request.getAirServerId());
        CCATLogger.DEBUG_LOGGER.info("AirServer Is Retrieved Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse<AIRServer> updateAirServers(@RequestBody UpdateAirServerRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Started Updating AirServer by ID");
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        airServersService.updateAirServer(request.getAirServer());
        CCATLogger.DEBUG_LOGGER.info("AirServer Is Updated Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse<AIRServer> addAirServers(@RequestBody AddAirServerRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Started Adding AirServer");
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        airServersService.addAirServer(request.getAirServer());
        CCATLogger.DEBUG_LOGGER.info("AirServer Is Added Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse<AIRServer> deleteAirServersById(@RequestBody DeleteAirServerRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Started Deleting AirServer by ID");
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        airServersService.deleteAirServerById(request.getAirServerId());
        CCATLogger.DEBUG_LOGGER.info("AirServer Is Deleted Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }
}
