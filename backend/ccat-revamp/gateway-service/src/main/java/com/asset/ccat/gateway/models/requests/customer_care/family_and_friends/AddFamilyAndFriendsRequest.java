package com.asset.ccat.gateway.models.requests.customer_care.family_and_friends;

import com.asset.ccat.gateway.models.requests.SubscriberRequest;

public class AddFamilyAndFriendsRequest extends SubscriberRequest {

    private String familyAndFriendsNumber;
    private Integer familyAndFriendsPlanId;

    public String getFamilyAndFriendsNumber() {
        return familyAndFriendsNumber;
    }

    public void setFamilyAndFriendsNumber(String familyAndFriendsNumber) {
        this.familyAndFriendsNumber = familyAndFriendsNumber;
    }

    public Integer getFamilyAndFriendsPlanId() {
        return familyAndFriendsPlanId;
    }

    public void setFamilyAndFriendsPlanId(Integer familyAndFriendsPlanId) {
        this.familyAndFriendsPlanId = familyAndFriendsPlanId;
    }

    @Override
    public String toString() {
        return "AddFamilyAndFriendsRequest{" +
                "familyAndFriendsNumber='" + familyAndFriendsNumber + '\'' +
                ", familyAndFriendsPlanId=" + familyAndFriendsPlanId +
                ", msisdn='" + msisdn + '\'' +
                '}';
    }
}
