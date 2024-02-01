package com.asset.ccat.gateway.models.requests.admin.ods_nodes;

import com.asset.ccat.gateway.models.requests.BaseRequest;
import com.asset.ccat.gateway.models.admin.ODSNodeModel;

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

    @Override
    public String toString() {
        return "UpdateODSNodeRequest{" +
                "odsNode=" + odsNode +
                '}';
    }
}
