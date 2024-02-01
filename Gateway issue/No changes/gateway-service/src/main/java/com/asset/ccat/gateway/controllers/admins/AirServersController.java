package com.asset.ccat.gateway.controllers.admins;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.air_servers.*;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.air_servers.GetAirServerResponse;
import com.asset.ccat.gateway.models.responses.admin.air_servers.GetAllAirServersResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.admin.AirServersService;
import com.asset.ccat.gateway.validators.admins.AirServersValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author Assem.Hassan
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = Defines.ContextPaths.AIR_SERVERS)
public class AirServersController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AirServersValidator airServersValidator;
    @Autowired
    private AirServersService airServersService;


    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllAirServersResponse> getAllAirServers(@RequestBody GetAllAirServersRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Air Servers Request [" + request + "]");
        GetAllAirServersResponse allAirServers = airServersService.getAllAirServers(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Air Servers Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                allAirServers);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetAirServerResponse> getAirServer(@RequestBody GetAirServerRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Air Server Request [" + request + "]");
        GetAirServerResponse airServer = airServersService.getAirServer(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Air Server Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                airServer);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse addAirServer(@RequestBody AddAirServerRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Add Air Server Request [" + request + "]");
        airServersValidator.validateAirServer(request);
        airServersService.addAirServer(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Add Air Server Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateAirServer(@RequestBody UpdateAirServerRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Air Server Request [" + request + "]");
        airServersValidator.validateUpdateAirServer(request);
        airServersService.updateAirServer(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Air Server Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deleteAirServer(@RequestBody DeleteAirServerRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Delete Air Server Request [" + request + "]");
        airServersValidator.validateDeleteAirServer(request);
        airServersService.deleteAirServer(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Delete Air Server Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }
}
