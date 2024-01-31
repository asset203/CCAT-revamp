package com.asset.ccat.gateway.models.requests.shared;

import com.asset.ccat.rabbitmq.models.FootprintModel;

/**
 *
 * @author marwa.elshawarby
 */
public class FootprintLogRequest extends com.asset.ccat.gateway.models.requests.BaseRequest{
    
    private FootprintModel footprint;

    public FootprintModel getFootPrint() {
        return footprint;
    }

    public void setFootPrint(FootprintModel footPrint) {
        this.footprint = footPrint;
    } 
}
