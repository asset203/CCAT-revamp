package com.asset.ccat.gateway.services.admin;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.models.requests.admin.flex_share_history.*;
import com.asset.ccat.gateway.models.responses.admin.flex_share_history.GetAllFlexShareHistoryNodesResponse;
import com.asset.ccat.gateway.models.responses.admin.flex_share_history.GetFlexShareHistoryNodeResponse;
import com.asset.ccat.gateway.proxy.admin.FlexShareHistoryNodesProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Assem.Hassan
 */
@Service
public class FlexShareHistoryNodesService {

    @Autowired
    private FlexShareHistoryNodesProxy flexShareHistoryNodesProxy;

    public GetAllFlexShareHistoryNodesResponse getAllFlexShareHistoryNodes(GetAllFlexShareHistoryNodesRequest request) throws GatewayException {
        return flexShareHistoryNodesProxy.getAllFlexShareHistoryNodes(request);
    }

    public GetFlexShareHistoryNodeResponse getFlexShareHistoryNode(GetFlexShareHistoryNodeRequest request) throws GatewayException {
        return flexShareHistoryNodesProxy.getFlexShareHistoryNodes(request);
    }

    public void addFlexShareHistoryNode(AddFlexShareHistoryNodeRequest request) throws GatewayException {
        flexShareHistoryNodesProxy.addFlexShareHistoryNode(request);
    }

    public void updateFlexShareHistoryNode(UpdateFlexShareHistoryNodeRequest request) throws GatewayException {
        flexShareHistoryNodesProxy.updateFlexShareHistoryNode(request);
    }

    public void deleteFlexShareHistoryNode(DeleteFlexShareHistoryNodeRequest request) throws GatewayException {
        flexShareHistoryNodesProxy.deleteFlexShareHistoryNode(request);
    }
}
