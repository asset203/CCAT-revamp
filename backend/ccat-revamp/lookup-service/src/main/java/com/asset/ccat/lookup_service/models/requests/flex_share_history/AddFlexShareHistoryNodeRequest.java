package com.asset.ccat.lookup_service.models.requests.flex_share_history;

import com.asset.ccat.lookup_service.models.ods_models.FlexShareHistoryNodeModel;
import com.asset.ccat.lookup_service.models.ods_models.ODSNodeModel;
import com.asset.ccat.lookup_service.models.requests.BaseRequest;

/**
 * @author Assem.Hassan
 */
public class AddFlexShareHistoryNodeRequest extends BaseRequest {

    private FlexShareHistoryNodeModel flexShareHistoryNode;

    public AddFlexShareHistoryNodeRequest() {
    }

    public AddFlexShareHistoryNodeRequest(FlexShareHistoryNodeModel flexShareHistoryNode) {
        this.flexShareHistoryNode = flexShareHistoryNode;
    }

    public FlexShareHistoryNodeModel getFlexShareHistoryNode() {
        return flexShareHistoryNode;
    }

    public void setFlexShareHistoryNode(FlexShareHistoryNodeModel flexShareHistoryNode) {
        this.flexShareHistoryNode = flexShareHistoryNode;
    }
}
