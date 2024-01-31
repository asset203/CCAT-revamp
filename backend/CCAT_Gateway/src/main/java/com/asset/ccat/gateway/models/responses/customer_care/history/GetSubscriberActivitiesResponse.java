package com.asset.ccat.gateway.models.responses.customer_care.history;

import com.asset.ccat.gateway.redis.model.SubscriberActivityModel;

import java.util.List;

public class GetSubscriberActivitiesResponse {

    private List<SubscriberActivityModel> subscriberActivityList;
    private Integer totalNumberOfActivities;

    public GetSubscriberActivitiesResponse() {
    }

    public GetSubscriberActivitiesResponse(List<SubscriberActivityModel> subscriberActivityList) {
        this.subscriberActivityList = subscriberActivityList;
    }

    public GetSubscriberActivitiesResponse(List<SubscriberActivityModel> subscriberActivityList, Integer totalNumberOfActivities) {
        this.subscriberActivityList = subscriberActivityList;
        this.totalNumberOfActivities = totalNumberOfActivities;
    }

    public List<SubscriberActivityModel> getSubscriberActivityList() {
        return subscriberActivityList;
    }

    public void setSubscriberActivityList(List<SubscriberActivityModel> subscriberActivityList) {
        this.subscriberActivityList = subscriberActivityList;
    }

    public Integer getTotalNumberOfActivities() {
        return totalNumberOfActivities;
    }

    public void setTotalNumberOfActivities(Integer totalNumberOfActivities) {
        this.totalNumberOfActivities = totalNumberOfActivities;
    }
}
