/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.models.customer_care.SubscriberAccountModel;
import com.asset.ccat.gateway.models.nba.AcceptGiftRequest;
import com.asset.ccat.gateway.models.nba.GiftModel;
import com.asset.ccat.gateway.models.nba.RejectGiftRequest;
import com.asset.ccat.gateway.models.nba.SendGiftRequest;
import com.asset.ccat.gateway.models.requests.SubscriberRequest;
import com.asset.ccat.gateway.models.requests.nba.GetAllGiftsRequest;
import com.asset.ccat.gateway.proxy.NBAProxy;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mahmoud Shehab
 */
@Service
public class NBAService {

    @Autowired
    NBAProxy nbaProxy;

    public List<GiftModel> getAllGifts(GetAllGiftsRequest getAllGiftsRequest) throws GatewayException {
        return nbaProxy.getAllGifts(getAllGiftsRequest);
    }

    public void acceptGift(AcceptGiftRequest acceptGiftRequest) throws GatewayException {
        nbaProxy.acceptGift(acceptGiftRequest);
    }

    public void rejectGift(RejectGiftRequest rejectGiftRequest) throws GatewayException {
        nbaProxy.rejectGift(rejectGiftRequest);
    }

    public void sendSmsGift(SendGiftRequest sendGiftRequest) throws GatewayException {
        nbaProxy.sendSmsGift(sendGiftRequest);
    }
}
