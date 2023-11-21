package com.asset.ccat.lookup_service.models.responses.dss_nodes;

import com.asset.ccat.lookup_service.models.ods_models.DSSNodeModel;

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
