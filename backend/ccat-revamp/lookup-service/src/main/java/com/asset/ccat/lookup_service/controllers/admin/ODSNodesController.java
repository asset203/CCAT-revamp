package com.asset.ccat.lookup_service.controllers.admin;

import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.BaseResponse;
import com.asset.ccat.lookup_service.models.ods_models.ODSNodeModel;
import com.asset.ccat.lookup_service.models.requests.ods_nodes.*;
import com.asset.ccat.lookup_service.models.responses.ods_nodes.GetAllODSNodesResponse;
import com.asset.ccat.lookup_service.models.responses.ods_nodes.GetODSNodeResponse;
import com.asset.ccat.lookup_service.services.ODSNodesService;
import com.asset.ccat.lookup_service.util.Utils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Assem Hassan
 */
@RestController
@RequestMapping(value = Defines.ContextPaths.ODS_NODES)
public class ODSNodesController {

    @Autowired
    private ODSNodesService odsNodesService;


    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllODSNodesResponse> getAllODSNodes(@RequestBody GetAllODSNodesRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Started Getting All ODSNodes");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        GetAllODSNodesResponse response = odsNodesService.getAllODSNodes();
        CCATLogger.DEBUG_LOGGER.info("All ODSNodes are Retrieved Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetODSNodeResponse> getODSNodeById(
            @RequestBody GetODSNodeRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Started Getting ODSNode by ID");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        GetODSNodeResponse response = odsNodesService.getODSNodeById(request.getOdsNodeId());
        CCATLogger.DEBUG_LOGGER.info("ODSNode is Retrieved Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse<ODSNodeModel> updateODSNode(@RequestBody UpdateODSNodeRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Started Updating ODSNode by ID");
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        odsNodesService.updateODSNode(request.getOdsNode());
        CCATLogger.DEBUG_LOGGER.info("ODSNode is Updated Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse<ODSNodeModel> addODSNode(@RequestBody AddODSNodeRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Started Adding ODSNode");
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        odsNodesService.addODSNode(request.getOdsNode());
        CCATLogger.DEBUG_LOGGER.info("ODSNode is Added Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse<ODSNodeModel> deleteODSNodeById(@RequestBody DeleteODSNodeRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Started Deleting ODSNode by ID");
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        odsNodesService.deleteODSNode(request.getOdsNodeId());
        CCATLogger.DEBUG_LOGGER.info("ODSNode is Deleted Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }
}
