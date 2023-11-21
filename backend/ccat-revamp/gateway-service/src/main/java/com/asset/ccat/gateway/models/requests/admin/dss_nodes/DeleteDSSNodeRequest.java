package com.asset.ccat.gateway.models.requests.admin.dss_nodes;


import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author Assem.Hassan
 */
public class DeleteDSSNodeRequest extends BaseRequest {

    private Integer dssNodeId;

    public DeleteDSSNodeRequest() {
    }

    public DeleteDSSNodeRequest(Integer dssNodeId) {
        this.dssNodeId = dssNodeId;
    }

    public Integer getDssNodeId() {
        return dssNodeId;
    }

    public void setDssNodeId(Integer dssNodeId) {
        this.dssNodeId = dssNodeId;
    }

    @Override
    public String toString() {
        return "DeleteDSSNodeRequest{" +
                "dssNodeId=" + dssNodeId +
                '}';
    }
}
