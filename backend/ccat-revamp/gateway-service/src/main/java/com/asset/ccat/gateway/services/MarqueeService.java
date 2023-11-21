/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.marquee.CreateMarqueeRequest;
import com.asset.ccat.gateway.models.requests.admin.marquee.DeleteAllMarqueesRequest;
import com.asset.ccat.gateway.models.requests.admin.marquee.DeleteMarqueeRequest;
import com.asset.ccat.gateway.models.requests.admin.marquee.GetAllEs2alnyMarqueeRequest;
import com.asset.ccat.gateway.models.requests.admin.marquee.UpdateAllMarqueesRequest;
import com.asset.ccat.gateway.models.requests.admin.marquee.UpdateMarqueesRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.marquee.GetAllEs2alnyMarqueeResponse;
import com.asset.ccat.gateway.proxy.MarqueeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author wael.mohamed
 */
@Service
public class MarqueeService {

    @Autowired
    private MarqueeProxy marqueeProxy;

    public GetAllEs2alnyMarqueeResponse getAllMarquees(GetAllEs2alnyMarqueeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllEs2alnyMarquees - call serivce");
        return marqueeProxy.getAllMarquees(request);
    }

    public BaseResponse addMarquee(CreateMarqueeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting addMarquee - call  serivce");
        var getAllEs2alnyMarqueeRequest = new GetAllEs2alnyMarqueeRequest();
        getAllEs2alnyMarqueeRequest.setRequestId(request.getRequestId());
        getAllEs2alnyMarqueeRequest.setSessionId(request.getSessionId());
        getAllEs2alnyMarqueeRequest.setUsername(request.getUsername());
        getAllEs2alnyMarqueeRequest.setToken(request.getToken());
        GetAllEs2alnyMarqueeResponse response = marqueeProxy.getAllMarquees(getAllEs2alnyMarqueeRequest);
        boolean contains;
        int i;
        for (i = 0; i < response.getMarquees().size(); i++) {
            contains = response.getMarquees().get(i).getTitle().equals(request.getTitle());
            if (contains) {
                throw new GatewayValidationException(ErrorCodes.WARNING.DUPLICATED_DATA, "title");
            }
        }
        return marqueeProxy.addMarquee(request);
    }

    public BaseResponse updateAllMarquees(UpdateAllMarqueesRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting updateMarquee - call  serivce");
        return marqueeProxy.updateAllMarquees(request);
    }

    public BaseResponse updateMarquee(UpdateMarqueesRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting updateMarquee - call  serivce");
        return marqueeProxy.updateMarquee(request);
    }

    public BaseResponse deleteMarqueeById(DeleteMarqueeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting deleteMarquee - call  serivce");
        return marqueeProxy.deleteMarqueeById(request);
    }

    public BaseResponse deleteAllMarquee(DeleteAllMarqueesRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting deleteAllMarquee - call  serivce");
        return marqueeProxy.deleteAllMarquee(request);
    }

}
