/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.responses.lookup;

import com.asset.ccat.gateway.models.customer_care.LkOfferModel;
import java.util.List;

/**
 *
 * @author wael.mohamed
 */
public class GetOffersLKResponse {

    List<LkOfferModel> offers;

    public GetOffersLKResponse() {
    }

    public GetOffersLKResponse(List<LkOfferModel> offers) {
        this.offers = offers;
    }

    public List<LkOfferModel> getOffers() {
        return offers;
    }

    public void setOffers(List<LkOfferModel> offers) {
        this.offers = offers;
    }
}
