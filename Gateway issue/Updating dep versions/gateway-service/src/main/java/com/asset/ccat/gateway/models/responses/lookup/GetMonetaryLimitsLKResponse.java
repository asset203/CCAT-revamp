package com.asset.ccat.gateway.models.responses.lookup;

import com.asset.ccat.gateway.models.users.LkMonetaryLimitModel;
import java.util.List;

/**
 *
 * @author marwa.elshawarby
 */
public class GetMonetaryLimitsLKResponse {

    private List<LkMonetaryLimitModel> monetaryLimits;

    public GetMonetaryLimitsLKResponse() {
    }

    public GetMonetaryLimitsLKResponse(List<LkMonetaryLimitModel> monetaryLimits) {
        this.monetaryLimits = monetaryLimits;
    }

    public List<LkMonetaryLimitModel> getMonetaryLimits() {
        return monetaryLimits;
    }

    public void setMonetaryLimits(List<LkMonetaryLimitModel> monetaryLimits) {
        this.monetaryLimits = monetaryLimits;
    }
}
