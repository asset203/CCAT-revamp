package com.asset.ccat.lookup_service.models.requests.dss_nodes;

import com.asset.ccat.lookup_service.models.requests.BaseRequest;

/**
 * @author Assem.Hassan
 */
public class GetDSSNodeRequest extends BaseRequest {

    private Integer dssNodeId;

    public GetDSSNodeRequest() {
    }

    public GetDSSNodeRequest(Integer dssNodeId) {
        this.dssNodeId = dssNodeId;
    }

    public Integer getDssNodeId() {
        return dssNodeId;
    }

    public void setDssNodeId(Integer dssNodeId) {
        this.dssNodeId = dssNodeId;
    }
}
