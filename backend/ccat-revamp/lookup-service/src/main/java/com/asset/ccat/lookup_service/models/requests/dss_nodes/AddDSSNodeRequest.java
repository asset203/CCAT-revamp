package com.asset.ccat.lookup_service.models.requests.dss_nodes;

import com.asset.ccat.lookup_service.models.ods_models.DSSNodeModel;
import com.asset.ccat.lookup_service.models.requests.BaseRequest;

/**
 * @author Assem.Hassan
 */
public class AddDSSNodeRequest extends BaseRequest {

    private DSSNodeModel dssNode;

    public AddDSSNodeRequest() {
    }

    public AddDSSNodeRequest(DSSNodeModel dssNode) {
        this.dssNode = dssNode;
    }

    public DSSNodeModel getDssNode() {
        return dssNode;
    }

    public void setDssNode(DSSNodeModel dssNode) {
        this.dssNode = dssNode;
    }
}
