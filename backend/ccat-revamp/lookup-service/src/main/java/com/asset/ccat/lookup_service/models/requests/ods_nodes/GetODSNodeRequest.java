package com.asset.ccat.lookup_service.models.requests.ods_nodes;

import com.asset.ccat.lookup_service.models.requests.BaseRequest;

/**
 * @author Assem.Hassan
 */
public class GetODSNodeRequest extends BaseRequest {

    private Integer odsNodeId;

    public GetODSNodeRequest() {
    }

    public GetODSNodeRequest(Integer odsNodeId) {
        this.odsNodeId = odsNodeId;
    }

    public Integer getOdsNodeId() {
        return odsNodeId;
    }

    public void setOdsNodeId(Integer odsNodeId) {
        this.odsNodeId = odsNodeId;
    }
}
