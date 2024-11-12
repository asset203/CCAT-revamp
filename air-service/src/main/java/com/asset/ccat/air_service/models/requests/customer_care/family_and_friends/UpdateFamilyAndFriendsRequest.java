package com.asset.ccat.air_service.models.requests.customer_care.family_and_friends;

import com.asset.ccat.air_service.models.requests.SubscriberRequest;

public class UpdateFamilyAndFriendsRequest extends SubscriberRequest {
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
        return "UpdateFamilyAndFriendsRequest{" +
                "familyAndFriendsNumber='" + familyAndFriendsNumber + '\'' +
                ", familyAndFriendsPlanId=" + familyAndFriendsPlanId +
                '}';
    }
}
