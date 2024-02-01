package com.asset.ccat.gateway.models.responses.admin.ods_nodes;


import com.asset.ccat.gateway.models.admin.ODSNodeModel;

/**
 * @author Assem.Hassan
 */
public class GetODSNodeResponse {

    private ODSNodeModel odsNodeModel;

    public GetODSNodeResponse() {
    }

    public GetODSNodeResponse(ODSNodeModel odsNodeModel) {
        this.odsNodeModel = odsNodeModel;
    }

    public ODSNodeModel getOdsNodesModel() {
        return odsNodeModel;
    }

    public void setOdsNodesModel(ODSNodeModel odsNodeModel) {
        this.odsNodeModel = odsNodeModel;
    }
}
