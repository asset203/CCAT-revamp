/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.models.responses.marquee;

import com.asset.ccat.user_management.models.users.MarqueeModel;
import java.util.List;

/**
 *
 * @author wael.mohamed
 */
public class GetAllMarqueeResponse {

    List<MarqueeModel> marquees;

    public GetAllMarqueeResponse() {
    }

    public GetAllMarqueeResponse(List<MarqueeModel> marquees) {
        this.marquees = marquees;
    }

    public List<MarqueeModel> getMarquees() {
        return marquees;
    }

    public void setMarquees(List<MarqueeModel> marquees) {
        this.marquees = marquees;
    }

}
