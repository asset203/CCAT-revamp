package com.asset.ccat.gateway.models.responses.admin.dss_nodes;

import com.asset.ccat.gateway.models.admin.DSSNodeModel;

/**
 * @author Assem.Hassan
 */
public class GetDSSNodeResponse {

    private DSSNodeModel dssNodeModel;

    public GetDSSNodeResponse() {
    }

    public GetDSSNodeResponse(DSSNodeModel dssNodeModel) {
        this.dssNodeModel = dssNodeModel;
    }

    public DSSNodeModel getDssNodesModel() {
        return dssNodeModel;
    }

    public void setDssNodesModel(DSSNodeModel dssNodeModel) {
        this.dssNodeModel = dssNodeModel;
    }
}
