/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.responses.customer_care.history;

import com.asset.ccat.gateway.redis.model.SubscriberActivityModel;

import java.util.List;

/**
 * @author wael.mohamed
 */
public class GetAllSubscriberActivityResponse {

    private List<SubscriberActivityModel> subscriberActivities;
    private Long totalNumberOfActivities;

    public GetAllSubscriberActivityResponse() {
    }

    public GetAllSubscriberActivityResponse(List<SubscriberActivityModel> subscriberActivities) {
        this.subscriberActivities = subscriberActivities;
    }

    public GetAllSubscriberActivityResponse(List<SubscriberActivityModel> subscriberActivities, Long totalNumberOfActivities) {
        this.subscriberActivities = subscriberActivities;
        this.totalNumberOfActivities = totalNumberOfActivities;
    }

    public List<SubscriberActivityModel> getSubscriberActivities() {
        return subscriberActivities;
    }

    public void setSubscriberActivities(List<SubscriberActivityModel> subscriberActivities) {
        this.subscriberActivities = subscriberActivities;
    }

    public Long getTotalNumberOfActivities() {
        return totalNumberOfActivities;
    }

    public void setTotalNumberOfActivities(Long totalNumberOfActivities) {
        this.totalNumberOfActivities = totalNumberOfActivities;
    }
}
