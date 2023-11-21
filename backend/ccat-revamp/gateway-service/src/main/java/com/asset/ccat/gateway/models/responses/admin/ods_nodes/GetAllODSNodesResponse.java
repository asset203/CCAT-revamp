package com.asset.ccat.gateway.models.responses.admin.ods_nodes;

import com.asset.ccat.gateway.models.admin.ODSNodeModel;

import java.util.List;

/**
 * @author Assem.Hassan
 */
public class GetAllODSNodesResponse {

    private List<ODSNodeModel> odsNodesList;

    public GetAllODSNodesResponse() {
    }

    public GetAllODSNodesResponse(List<ODSNodeModel> odsNodesList) {
        this.odsNodesList = odsNodesList;
    }

    public List<ODSNodeModel> getOdsNodesModelList() {
        return odsNodesList;
    }

    public void setOdsNodesModelList(List<ODSNodeModel> odsNodeModelList) {
        this.odsNodesList = odsNodeModelList;
    }
}
