package com.asset.ccat.gateway.models.responses.customer_care.super_flex;

import com.asset.ccat.gateway.models.customer_care.SuperFlexLookupModel;

import java.util.List;

public class GetOptInAddonsResponse {

    private List<String> currentAddonsList;
    private List<SuperFlexLookupModel> addonsList;

    public GetOptInAddonsResponse() {
    }

    public GetOptInAddonsResponse(List<String> currentAddonsList, List<SuperFlexLookupModel> addonsList) {
        this.currentAddonsList = currentAddonsList;
        this.addonsList = addonsList;
    }

    public List<String> getCurrentAddonsList() {
        return currentAddonsList;
    }

    public void setCurrentAddonsList(List<String> currentAddonsList) {
        this.currentAddonsList = currentAddonsList;
    }

    public List<SuperFlexLookupModel> getAddonsList() {
        return addonsList;
    }

    public void setAddonsList(List<SuperFlexLookupModel> addonsList) {
        this.addonsList = addonsList;
    }

    @Override
    public String toString() {
        return "GetOptInAddonsResponse{" +
                "currentAddonsList=" + currentAddonsList +
                ", addonsList=" + addonsList +
                '}';
    }
}
