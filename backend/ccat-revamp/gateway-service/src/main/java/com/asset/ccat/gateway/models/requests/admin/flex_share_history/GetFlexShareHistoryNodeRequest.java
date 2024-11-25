package com.asset.ccat.gateway.models.requests.admin.flex_share_history;

import com.asset.ccat.gateway.models.requests.BaseRequest;
/**
 * @author Assem.Hassan
 */
public class GetFlexShareHistoryNodeRequest extends BaseRequest {

    private Integer flexShareHistoryNodeId;

    public GetFlexShareHistoryNodeRequest() {
    }

    public GetFlexShareHistoryNodeRequest(Integer flexShareHistoryNodeId) {
        this.flexShareHistoryNodeId = flexShareHistoryNodeId;
    }

    public Integer getFlexShareHistoryNodeId() {
        return flexShareHistoryNodeId;
    }

    public void setFlexShareHistoryNodeId(Integer flexShareHistoryNodeId) {
        this.flexShareHistoryNodeId = flexShareHistoryNodeId;
    }

    @Override
    public String toString() {
        return "GetFlexShareHistoryNodeRequest{" +
                "flexShareHistoryNodeId=" + flexShareHistoryNodeId +
                '}';
    }
}
