/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.responses.customer_care.history;

import java.util.Map;

/**
 *
 * @author wael.mohamed
 */
public class GetSubscriberActivityDetailsResponse {

    Map<String, String> details;

    public GetSubscriberActivityDetailsResponse() {
    }

    public GetSubscriberActivityDetailsResponse(Map<String, String> details) {
        this.details = details;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }

}
