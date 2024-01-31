/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.models.customer_care.GetPrepaidProfileResponse;
import com.asset.ccat.gateway.models.customer_care.SubscriberAccountModel;
import com.asset.ccat.gateway.models.requests.SubscriberRequest;
import com.asset.ccat.gateway.models.responses.customer_care.GetMainProductResponse;
import com.asset.ccat.gateway.proxy.SubscriberAdminProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author Mahmoud Shehab
 */
@Service
public class SubscriberAdminService {

    @Autowired
    SubscriberAdminProxy subscriberAdminProxy;

    public SubscriberAccountModel getAccountDetails(SubscriberRequest subscriberRequest) throws GatewayException {
        return subscriberAdminProxy.getAccountDetails(subscriberRequest);
    }

    public SubscriberAccountModel test(SubscriberRequest subscriberRequest) throws GatewayException {
        return subscriberAdminProxy.test(subscriberRequest);
    }

    public GetPrepaidProfileResponse.Products.Product getProducts(SubscriberRequest subscriberRequest) throws GatewayException {
        GetPrepaidProfileResponse.Products.Product product = null;
        GetPrepaidProfileResponse getPrepaidProfileResponse = subscriberAdminProxy.getProducts(subscriberRequest);
        if (getPrepaidProfileResponse.getProducts() != null
                && getPrepaidProfileResponse.getProducts().getProduct() != null) {
            product = getPrepaidProfileResponse.getProducts().getProduct().get(0);
        }
        return product;
    }
    
     public List<GetMainProductResponse> getMainProducts(SubscriberRequest subscriberRequest) throws GatewayException {
        return subscriberAdminProxy.getMainProducts(subscriberRequest);
    }
}
