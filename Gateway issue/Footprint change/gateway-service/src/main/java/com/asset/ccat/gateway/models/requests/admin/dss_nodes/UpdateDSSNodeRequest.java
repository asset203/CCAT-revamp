package com.asset.ccat.gateway.models.requests.admin.dss_nodes;

import com.asset.ccat.gateway.models.admin.DSSNodeModel;
import com.asset.ccat.gateway.models.requests.BaseRequest;


/**
 * @author Assem.Hassan
 */
public class UpdateDSSNodeRequest extends BaseRequest {

    private DSSNodeModel dssNode;

    public UpdateDSSNodeRequest() {
    }

    public UpdateDSSNodeRequest(DSSNodeModel dssNode) {
        this.dssNode = dssNode;
    }

    public DSSNodeModel getDssNode() {
        return dssNode;
    }

    public void setDssNode(DSSNodeModel dssNode) {
        this.dssNode = dssNode;
    }

    @Override
    public String toString() {
        return "UpdateDSSNodeRequest{" +
                "dssNode=" + dssNode +
                '}';
    }
}
