package com.asset.ccat.gateway.models.responses.admin.flex_share_history;

import com.asset.ccat.gateway.models.admin.FlexShareHistoryNodeModel;

import java.util.List;

/**
 * @author Assem.Hassan
 */
public class GetAllFlexShareHistoryNodesResponse {

    private List<FlexShareHistoryNodeModel> flexShareHistoryNodeModelNodesList;

    public GetAllFlexShareHistoryNodesResponse() {
    }

    public GetAllFlexShareHistoryNodesResponse(List<FlexShareHistoryNodeModel> flexShareHistoryNodeModelNodesList) {
        this.flexShareHistoryNodeModelNodesList = flexShareHistoryNodeModelNodesList;
    }

    public List<FlexShareHistoryNodeModel> getFlexShareHistoryNodeModelNodesList() {
        return flexShareHistoryNodeModelNodesList;
    }

    public void setFlexShareHistoryNodeModelNodesList(List<FlexShareHistoryNodeModel> flexShareHistoryNodeModelNodesList) {
        this.flexShareHistoryNodeModelNodesList = flexShareHistoryNodeModelNodesList;
    }
}
