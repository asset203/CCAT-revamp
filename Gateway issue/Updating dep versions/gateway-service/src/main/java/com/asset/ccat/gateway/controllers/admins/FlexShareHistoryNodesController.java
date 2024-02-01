package com.asset.ccat.gateway.controllers.admins;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.flex_share_history.*;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.flex_share_history.GetAllFlexShareHistoryNodesResponse;
import com.asset.ccat.gateway.models.responses.admin.flex_share_history.GetFlexShareHistoryNodeResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.admin.FlexShareHistoryNodesService;
import com.asset.ccat.gateway.validators.admins.FlexShareHistoryNodesValidator;
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
@RequestMapping(value = Defines.ContextPaths.FLEX_SHARE_HISTORY_NODES)
public class FlexShareHistoryNodesController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private FlexShareHistoryNodesValidator flexShareHistoryNodesValidator;
    @Autowired
    private FlexShareHistoryNodesService flexShareHistoryNodesService;


    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllFlexShareHistoryNodesResponse> getAllFlexShareHistoryNodes(@RequestBody GetAllFlexShareHistoryNodesRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Flex Share History Nodes Request [" + request + "]");
        GetAllFlexShareHistoryNodesResponse nodes = flexShareHistoryNodesService.getAllFlexShareHistoryNodes(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Flex Share History Nodes Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                nodes);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetFlexShareHistoryNodeResponse> getFlexShareHistoryNode(@RequestBody GetFlexShareHistoryNodeRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Flex Share History Nodes Request [" + request + "]");
        GetFlexShareHistoryNodeResponse node = flexShareHistoryNodesService.getFlexShareHistoryNode(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Flex Share History Nodes Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                node);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse addFlexShareHistoryNode(@RequestBody AddFlexShareHistoryNodeRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Add Flex Share History Nodes Request [" + request + "]");
        flexShareHistoryNodesValidator.validateFlexShareHistoryNode(request);
        flexShareHistoryNodesService.addFlexShareHistoryNode(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Add Flex Share History Nodes Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateFlexShareHistoryNodes(@RequestBody UpdateFlexShareHistoryNodeRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Flex Share History Nodes Request [" + request + "]");
        flexShareHistoryNodesValidator.validateUpdateFlexShareHistoryNode(request);
        flexShareHistoryNodesService.updateFlexShareHistoryNode(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Flex Share History Nodes Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deleteFlexShareHistoryNodes(@RequestBody DeleteFlexShareHistoryNodeRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Delete Flex Share History Nodes Request [" + request + "]");
        flexShareHistoryNodesValidator.validateDeleteFlexShareHistoryNode(request);
        flexShareHistoryNodesService.deleteFlexShareHistoryNode(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Delete Flex Share History Nodes Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }
}
