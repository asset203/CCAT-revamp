package com.asset.ccat.gateway.models.responses.admin.flex_share_history;

import com.asset.ccat.gateway.models.admin.FlexShareHistoryNodeModel;

/**
 * @author Assem.Hassan
 */
public class GetFlexShareHistoryNodeResponse {

    private FlexShareHistoryNodeModel flexShareHistoryNodeModel;

    public GetFlexShareHistoryNodeResponse() {
    }

    public GetFlexShareHistoryNodeResponse(FlexShareHistoryNodeModel flexShareHistoryNodeModel) {
        this.flexShareHistoryNodeModel = flexShareHistoryNodeModel;
    }

    public FlexShareHistoryNodeModel getFlexShareHistoryNodeModel() {
        return flexShareHistoryNodeModel;
    }

    public void setFlexShareHistoryNodeModel(FlexShareHistoryNodeModel flexShareHistoryNodeModel) {
        this.flexShareHistoryNodeModel = flexShareHistoryNodeModel;
    }
}
