package com.asset.ccat.lookup_service.controllers.admin;

import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.BaseResponse;
import com.asset.ccat.lookup_service.models.ods_models.FlexShareHistoryNodeModel;
import com.asset.ccat.lookup_service.models.requests.flex_share_history.*;
import com.asset.ccat.lookup_service.models.responses.flex_share_history.GetAllFlexShareHistoryNodesResponse;
import com.asset.ccat.lookup_service.models.responses.flex_share_history.GetFlexShareHistoryNodeResponse;
import com.asset.ccat.lookup_service.services.FlexShareHistoryNodesService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Assem Hassan
 */
@RestController
@RequestMapping(value = Defines.ContextPaths.FLEX_SHARE_HISTORY_NODES)
public class FlexShareHistoryNodesController {

    @Autowired
    private FlexShareHistoryNodesService flexShareHistoryNodesService;


    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllFlexShareHistoryNodesResponse> getAllFlexShareHistoryNodes(@RequestBody GetAllFlexShareHistoryNodesRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Started Getting All FlexShareHistoryNodes");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        GetAllFlexShareHistoryNodesResponse response = flexShareHistoryNodesService.getAllFlexShareHistoryNodes();
        CCATLogger.DEBUG_LOGGER.info("All FlexShareHistoryNodes are Retrieved Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetFlexShareHistoryNodeResponse> getFlexShareHistoryNodeById(
            @RequestBody GetFlexShareHistoryNodeRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Started Getting FlexShareHistoryNode by ID");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        GetFlexShareHistoryNodeResponse response = flexShareHistoryNodesService.getFlexShareHistoryNodeById(request.getFlexShareHistoryNodeId());
        CCATLogger.DEBUG_LOGGER.info("FlexShareHistoryNode is Retrieved Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse<FlexShareHistoryNodeModel> updateFlexShareHistoryNode(@RequestBody UpdateFlexShareHistoryNodeRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Started Updating FlexShareHistoryNode by ID");
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        flexShareHistoryNodesService.updateFlexShareHistoryNode(request.getFlexShareHistoryNode());
        CCATLogger.DEBUG_LOGGER.info("FlexShareHistoryNode is Updated Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse<FlexShareHistoryNodeModel> addFlexShareHistoryNode(@RequestBody AddFlexShareHistoryNodeRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Started Adding FlexShareHistoryNode");
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        flexShareHistoryNodesService.addFlexShareHistoryNode(request.getFlexShareHistoryNode());
        CCATLogger.DEBUG_LOGGER.info("FlexShareHistoryNode is Added Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse<FlexShareHistoryNodeModel> deleteFlexShareHistoryNodeById(@RequestBody DeleteFlexShareHistoryNodeRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Started Deleting FlexShareHistoryNode by ID");
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        flexShareHistoryNodesService.deleteFlexShareHistoryNode(request.getFlexShareHistoryNodeId());
        CCATLogger.DEBUG_LOGGER.info("FlexShareHistoryNode is Deleted Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }
}
