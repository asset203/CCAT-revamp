package com.asset.ccat.lookup_service.models.responses.flex_share_history;

import com.asset.ccat.lookup_service.models.ods_models.DSSNodeModel;
import com.asset.ccat.lookup_service.models.ods_models.FlexShareHistoryNodeModel;

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
