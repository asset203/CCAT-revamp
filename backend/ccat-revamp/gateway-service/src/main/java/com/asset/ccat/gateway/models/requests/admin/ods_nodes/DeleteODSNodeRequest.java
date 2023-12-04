package com.asset.ccat.gateway.models.requests.admin.ods_nodes;


import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author Assem.Hassan
 */
public class DeleteODSNodeRequest extends BaseRequest {

    private Integer odsNodeId;

    public DeleteODSNodeRequest() {
    }

    public DeleteODSNodeRequest(Integer odsNodeId) {
        this.odsNodeId = odsNodeId;
    }

    public Integer getOdsNodeId() {
        return odsNodeId;
    }

    public void setOdsNodeId(Integer odsNodeId) {
        this.odsNodeId = odsNodeId;
    }

    @Override
    public String toString() {
        return "DeleteODSNodeRequest{" +
                "odsNodeId=" + odsNodeId +
                '}';
    }
}
