/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.models.requests.offer.DeleteOfferRequest;
import com.asset.ccat.gateway.models.requests.offer.GetOfferRequest;
import com.asset.ccat.gateway.models.requests.offer.OfferRequest;
import com.asset.ccat.gateway.models.responses.GetAllOffersResponse;
import com.asset.ccat.gateway.proxy.OfferProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author wael.mohamed
 */
@Service
public class OfferService {

    @Autowired
    private OfferProxy offerProxy;

    public GetAllOffersResponse getOffers(GetOfferRequest offerRequest) throws GatewayException {
        return offerProxy.getAllOffers(offerRequest);
    }

    public void addOffer(OfferRequest offerRequest) throws GatewayException {
        offerProxy.addOffer(offerRequest);
    }

    public void updateOffer(OfferRequest offerRequest) throws GatewayException {
        offerProxy.updateOffer(offerRequest);
    }

    public void deleteOffer(DeleteOfferRequest offerRequest) throws GatewayException {
        offerProxy.deleteOffer(offerRequest);
    }

}
