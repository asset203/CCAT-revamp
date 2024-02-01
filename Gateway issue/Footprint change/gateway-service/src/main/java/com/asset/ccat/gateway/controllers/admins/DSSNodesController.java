package com.asset.ccat.gateway.controllers.admins;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.dss_nodes.*;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.dss_nodes.GetAllDSSNodesResponse;
import com.asset.ccat.gateway.models.responses.admin.dss_nodes.GetDSSNodeResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.admin.DSSNodesService;
import com.asset.ccat.gateway.validators.admins.DSSNodesValidator;
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
@RequestMapping(value = Defines.ContextPaths.DSS_NODES)
public class DSSNodesController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private DSSNodesValidator dssNodesValidator;
    @Autowired
    private DSSNodesService DSSNodesService;


    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllDSSNodesResponse> getAllDSSNodes(@RequestBody GetAllDSSNodesRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All DSS Nodes Request [" + request + "]");
        GetAllDSSNodesResponse nodes = DSSNodesService.getAllDSSNodes(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All DSS Nodes Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                nodes);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetDSSNodeResponse> getDSSNode(@RequestBody GetDSSNodeRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get DSS Nodes Request [" + request + "]");
        GetDSSNodeResponse node = DSSNodesService.getDSSNode(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get DSS Nodes Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                node);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse addDSSNode(@RequestBody AddDSSNodeRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Add DSS Nodes Request [" + request + "]");
        dssNodesValidator.validateDSSNode(request);
        DSSNodesService.addDSSNode(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Add DSS Nodes Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateDSSNode(@RequestBody UpdateDSSNodeRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update DSS Nodes Request [" + request + "]");
        dssNodesValidator.validateUpdateDSSNode(request);
        DSSNodesService.updateDSSNode(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update DSS Nodes Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deleteDSSNode(@RequestBody DeleteDSSNodeRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Delete DSS Nodes Request [" + request + "]");
        dssNodesValidator.validateDeleteDSSNode(request);
        DSSNodesService.deleteDSSNode(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Delete DSS Nodes Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }
}
