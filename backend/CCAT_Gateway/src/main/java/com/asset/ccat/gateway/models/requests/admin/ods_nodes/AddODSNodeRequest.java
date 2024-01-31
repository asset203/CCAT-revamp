package com.asset.ccat.gateway.models.requests.admin.ods_nodes;


import com.asset.ccat.gateway.models.requests.BaseRequest;
import com.asset.ccat.gateway.models.admin.ODSNodeModel;

/**
 * @author Assem.Hassan
 */
public class AddODSNodeRequest extends BaseRequest {

    private ODSNodeModel odsNode;

    public AddODSNodeRequest() {
    }

    public AddODSNodeRequest(ODSNodeModel odsNode) {
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
        return "AddODSNodeRequest{" +
                "odsNode=" + odsNode +
                '}';
    }
}
