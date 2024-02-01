package com.asset.ccat.gateway.services.admin;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.models.requests.admin.ods_nodes.*;
import com.asset.ccat.gateway.models.responses.admin.ods_nodes.GetAllODSNodesResponse;
import com.asset.ccat.gateway.models.responses.admin.ods_nodes.GetODSNodeResponse;
import com.asset.ccat.gateway.proxy.admin.ODSNodesProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Assem.Hassan
 */
@Service
public class ODSNodesService {

    @Autowired
    private ODSNodesProxy odsNodesProxy;

    public GetAllODSNodesResponse getAllODSNodes(GetAllODSNodesRequest request) throws GatewayException {
        return odsNodesProxy.getAllOdsNodes(request);
    }

    public GetODSNodeResponse getODSNode(GetODSNodeRequest request) throws GatewayException {
        return odsNodesProxy.getOdsNodes(request);
    }

    public void addODSNode(AddODSNodeRequest request) throws GatewayException {
        odsNodesProxy.addODSNode(request);
    }

    public void updateODSNode(UpdateODSNodeRequest request) throws GatewayException {
        odsNodesProxy.updateODSNode(request);
    }

    public void deleteODSNode(DeleteODSNodeRequest request) throws GatewayException {
        odsNodesProxy.deleteODSNode(request);
    }
}
