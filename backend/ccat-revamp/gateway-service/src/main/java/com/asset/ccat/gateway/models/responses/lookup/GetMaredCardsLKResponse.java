package com.asset.ccat.gateway.models.responses.lookup;



import com.asset.ccat.gateway.models.shared.LookupModel;

import java.util.List;

public class GetMaredCardsLKResponse {
    private List<LookupModel> maredCardsList;

    public GetMaredCardsLKResponse() {
    }

    public GetMaredCardsLKResponse(List<LookupModel> maredCardsList) {
        this.maredCardsList = maredCardsList;
    }

    public List<LookupModel> getMaredCardsList() {
        return maredCardsList;
    }

    public void setMaredCardsList(List<LookupModel> maredCardsList) {
        this.maredCardsList = maredCardsList;
    }
}
