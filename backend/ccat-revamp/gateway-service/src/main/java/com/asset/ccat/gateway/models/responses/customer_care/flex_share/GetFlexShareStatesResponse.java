package com.asset.ccat.gateway.models.responses.customer_care.flex_share;

import com.asset.ccat.gateway.models.shared.FlexShareBWStateModel;
import com.asset.ccat.gateway.models.shared.FlexShareProfitableStateModel;

public class GetFlexShareStatesResponse {

    private FlexShareBWStateModel blackWhiteState;
    private FlexShareProfitableStateModel profitableState;

    public GetFlexShareStatesResponse() {
    }

    public GetFlexShareStatesResponse(FlexShareBWStateModel blackWhiteState) {
        this.blackWhiteState = blackWhiteState;
    }

    public GetFlexShareStatesResponse(FlexShareProfitableStateModel profitableState) {
        this.profitableState = profitableState;
    }

    public FlexShareBWStateModel getBlackWhiteState() {
        return blackWhiteState;
    }

    public void setBlackWhiteState(FlexShareBWStateModel blackWhiteState) {
        this.blackWhiteState = blackWhiteState;
    }

    public FlexShareProfitableStateModel getProfitableState() {
        return profitableState;
    }

    public void setProfitableState(FlexShareProfitableStateModel profitableState) {
        this.profitableState = profitableState;
    }

    @Override
    public String toString() {
        return "GetFlexShareStatesResponse{" +
                "blackWhiteState=" + blackWhiteState +
                ", profitableState=" + profitableState +
                '}';
    }
}
