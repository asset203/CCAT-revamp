package com.asset.ccat.lookup_service.controllers.admin;

import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.BaseResponse;
import com.asset.ccat.lookup_service.models.ods_models.DSSNodeModel;
import com.asset.ccat.lookup_service.models.requests.dss_nodes.*;
import com.asset.ccat.lookup_service.models.responses.dss_nodes.GetAllDSSNodesResponse;
import com.asset.ccat.lookup_service.models.responses.dss_nodes.GetDSSNodeResponse;
import com.asset.ccat.lookup_service.services.DSSNodesService;
import com.asset.ccat.lookup_service.util.Utils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author assem.hassan
 */
@RestController

@RequestMapping(value = Defines.ContextPaths.DSS_NODES)
public class DSSNodesController {

    @Autowired
    private DSSNodesService dssNodesService;
    @Autowired
    private Utils utils;

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllDSSNodesResponse> getAllDSSNodes(@RequestBody GetAllDSSNodesRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Started Getting All DSSNodes");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        GetAllDSSNodesResponse response = dssNodesService.getAllDSSNodes();
        CCATLogger.DEBUG_LOGGER.info("All DSSNodes are Retrieved Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetDSSNodeResponse> getDSSNodeById(
            @RequestBody GetDSSNodeRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Started Getting DSSNode by ID");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        utils.isFieldInteger(request.getDssNodeId());
        GetDSSNodeResponse response = dssNodesService.getDSSNodeById(request.getDssNodeId());
        CCATLogger.DEBUG_LOGGER.info("DSSNode are Retrieved Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse<DSSNodeModel> updateDSSNode(@RequestBody UpdateDSSNodeRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Started Updating DSSNode by ID");
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        utils.isFieldInteger(request.getDssNode().getId());
        dssNodesService.updateDSSNode(request.getDssNode());
        CCATLogger.DEBUG_LOGGER.info("DSSNode are Updated Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse<DSSNodeModel> addDSSNode(@RequestBody AddDSSNodeRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Started Adding DSSNode");
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        dssNodesService.addDSSNode(request.getDssNode());
        CCATLogger.DEBUG_LOGGER.info("DSSNode are Added Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse<DSSNodeModel> deleteDSSNodeById(@RequestBody DeleteDSSNodeRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Started Deleting DSSNode by ID");
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        utils.isFieldInteger(request.getDssNodeId());
        dssNodesService.deleteDSSNode(request.getDssNodeId());
        CCATLogger.DEBUG_LOGGER.info("DSSNode are Deleted Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }
}
