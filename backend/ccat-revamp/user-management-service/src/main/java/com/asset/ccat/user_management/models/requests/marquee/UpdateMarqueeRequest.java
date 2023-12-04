/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.models.requests.marquee;

import com.asset.ccat.user_management.models.requests.BaseRequest;
import com.asset.ccat.user_management.models.users.MarqueeModel;

/**
 *
 * @author wael.mohamed
 */
public class UpdateMarqueeRequest extends BaseRequest {

    MarqueeModel marquees;

    public UpdateMarqueeRequest() {
    }

    public UpdateMarqueeRequest(MarqueeModel marquee) {
        this.marquees = marquee;
    }

    public MarqueeModel getMarquees() {
        return marquees;
    }

    public void setMarquees(MarqueeModel marquees) {
        this.marquees = marquees;
    }

}
