/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.models.customer_care.GetPrepaidProfileResponse;
import com.asset.ccat.gateway.models.requests.SubscriberRequest;
import com.asset.ccat.gateway.proxy.SubscriberAdminProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mahmoud Shehab
 */
@Service
public class ProductViewService {

    @Autowired
    SubscriberAdminProxy subscriberAdminProxy;

    public GetPrepaidProfileResponse getProducts(SubscriberRequest subscriberRequest) throws GatewayException {
        GetPrepaidProfileResponse getPrepaidProfileResponse = subscriberAdminProxy.getProducts(subscriberRequest);
        return getPrepaidProfileResponse;
    }
}
