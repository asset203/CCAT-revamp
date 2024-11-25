package com.asset.ccat.lookup_service.models.responses.ods_nodes;

import com.asset.ccat.lookup_service.models.ods_models.ODSNodeModel;

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
