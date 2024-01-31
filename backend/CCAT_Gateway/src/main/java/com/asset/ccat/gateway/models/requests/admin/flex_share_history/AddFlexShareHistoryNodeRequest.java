package com.asset.ccat.gateway.models.requests.admin.flex_share_history;

import com.asset.ccat.gateway.models.admin.FlexShareHistoryNodeModel;
import com.asset.ccat.gateway.models.requests.BaseRequest;


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

    @Override
    public String toString() {
        return "AddFlexShareHistoryNodeRequest{" +
                "flexShareHistoryNode=" + flexShareHistoryNode +
                '}';
    }
}
