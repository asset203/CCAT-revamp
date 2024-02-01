package com.asset.ccat.gateway.models.responses.admin.dss_nodes;

import com.asset.ccat.gateway.models.admin.DSSNodeModel;


import java.util.List;

/**
 * @author Assem.Hassan
 */
public class GetAllDSSNodesResponse {

    private List<DSSNodeModel> dssNodesList;

    public GetAllDSSNodesResponse() {
    }

    public GetAllDSSNodesResponse(List<DSSNodeModel> dssNodesList) {
        this.dssNodesList = dssNodesList;
    }

    public List<DSSNodeModel> getDssNodesList() {
        return dssNodesList;
    }

    public void setDssNodesList(List<DSSNodeModel> dssNodesList) {
        this.dssNodesList = dssNodesList;
    }
}
