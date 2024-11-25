package com.asset.ccat.lookup_service.models.requests.ods_nodes;

import com.asset.ccat.lookup_service.models.ods_models.ODSNodeModel;
import com.asset.ccat.lookup_service.models.requests.BaseRequest;

/**
 * @author Assem.Hassan
 */
public class UpdateODSNodeRequest extends BaseRequest {

    private ODSNodeModel odsNode;

    public UpdateODSNodeRequest() {
    }

    public UpdateODSNodeRequest(ODSNodeModel odsNode) {
        this.odsNode = odsNode;
    }

    public ODSNodeModel getOdsNode() {
        return odsNode;
    }

    public void setOdsNode(ODSNodeModel odsNode) {
        this.odsNode = odsNode;
    }
}
