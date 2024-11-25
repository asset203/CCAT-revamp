package com.asset.ccat.user_management.models.responses.lookup;

import com.asset.ccat.user_management.models.shared.LkMonetaryLimitModel;
import java.util.List;

/**
 *
 * @author marwa.elshawarby
 */
public class GetAllMonetaryLimitsResponse {

    List<LkMonetaryLimitModel> monetaryLimits;

    public GetAllMonetaryLimitsResponse() {
    }

    public GetAllMonetaryLimitsResponse(List<LkMonetaryLimitModel> monetaryLimits) {
        this.monetaryLimits = monetaryLimits;
    }

    public List<LkMonetaryLimitModel> getMonetaryLimits() {
        return monetaryLimits;
    }

    public void setMonetaryLimits(List<LkMonetaryLimitModel> monetaryLimits) {
        this.monetaryLimits = monetaryLimits;
    }

}
