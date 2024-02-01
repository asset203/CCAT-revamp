package com.asset.ccat.gateway.services.admin;

import com.asset.ccat.gateway.exceptions.GatewayException;

import com.asset.ccat.gateway.models.requests.admin.dss_nodes.*;
import com.asset.ccat.gateway.models.responses.admin.dss_nodes.GetAllDSSNodesResponse;
import com.asset.ccat.gateway.models.responses.admin.dss_nodes.GetDSSNodeResponse;
import com.asset.ccat.gateway.proxy.admin.DSSNodesProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Assem.Hassan
 */
@Service
public class DSSNodesService {

    @Autowired
    private DSSNodesProxy DSSNodesProxy;

    public GetAllDSSNodesResponse getAllDSSNodes(GetAllDSSNodesRequest request) throws GatewayException {
        return DSSNodesProxy.getAllDssNodes(request);
    }

    public GetDSSNodeResponse getDSSNode(GetDSSNodeRequest request) throws GatewayException {
        return DSSNodesProxy.getDssNode(request);
    }

    public void addDSSNode(AddDSSNodeRequest request) throws GatewayException {
        DSSNodesProxy.addDSSNode(request);
    }

    public void updateDSSNode(UpdateDSSNodeRequest request) throws GatewayException {
        DSSNodesProxy.updateDSSNode(request);
    }

    public void deleteDSSNode(DeleteDSSNodeRequest request) throws GatewayException {
        DSSNodesProxy.deleteDSSNode(request);
    }
}
