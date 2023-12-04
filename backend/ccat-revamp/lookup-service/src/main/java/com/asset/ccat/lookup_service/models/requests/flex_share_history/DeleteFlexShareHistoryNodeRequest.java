package com.asset.ccat.lookup_service.models.requests.flex_share_history;

import com.asset.ccat.lookup_service.models.requests.BaseRequest;

/**
 * @author Assem.Hassan
 */
public class DeleteFlexShareHistoryNodeRequest extends BaseRequest {

    private Integer flexShareHistoryNodeId;

    public DeleteFlexShareHistoryNodeRequest() {
    }

    public DeleteFlexShareHistoryNodeRequest(Integer flexShareHistoryNodeId) {
        this.flexShareHistoryNodeId = flexShareHistoryNodeId;
    }

    public Integer getFlexShareHistoryNodeId() {
        return flexShareHistoryNodeId;
    }

    public void setFlexShareHistoryNodeId(Integer flexShareHistoryNodeId) {
        this.flexShareHistoryNodeId = flexShareHistoryNodeId;
    }
}
