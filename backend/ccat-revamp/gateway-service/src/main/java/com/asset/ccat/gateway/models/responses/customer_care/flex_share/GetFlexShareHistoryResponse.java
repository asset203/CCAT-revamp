package com.asset.ccat.gateway.models.responses.customer_care.flex_share;

import com.asset.ccat.gateway.models.shared.FlexShareHistoryModel;

import java.util.List;

public class GetFlexShareHistoryResponse {

    private List<FlexShareHistoryModel> flexHistory;
    private Boolean isMaxSizeReached;

    public GetFlexShareHistoryResponse() {
    }

    public GetFlexShareHistoryResponse(List<FlexShareHistoryModel> flexHistory, Boolean isMaxSizeReached) {
        this.flexHistory = flexHistory;
        this.isMaxSizeReached = isMaxSizeReached;
    }

    public List<FlexShareHistoryModel> getFlexHistory() {
        return flexHistory;
    }

    public void setFlexHistory(List<FlexShareHistoryModel> flexHistory) {
        this.flexHistory = flexHistory;
    }

    public Boolean getIsMaxSizeReached() {
        return isMaxSizeReached;
    }

    public void setIsMaxSizeReached(Boolean isMaxSizeReached) {
        this.isMaxSizeReached = isMaxSizeReached;
    }

    @Override
    public String toString() {
        return "GetFlexShareHistoryResponse{" +
                "flexHistory=" + flexHistory +
                ", isMaxSizeReached=" + isMaxSizeReached +
                '}';
    }
}
