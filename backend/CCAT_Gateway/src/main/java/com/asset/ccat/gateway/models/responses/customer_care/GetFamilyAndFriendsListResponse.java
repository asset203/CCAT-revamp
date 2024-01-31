package com.asset.ccat.gateway.models.responses.customer_care;

import com.asset.ccat.gateway.models.customer_care.FamilyAndFriendsModel;

import java.util.List;

public class GetFamilyAndFriendsListResponse {

    private List<FamilyAndFriendsModel> familyAndFriendsList;

    public GetFamilyAndFriendsListResponse() {
    }

    public GetFamilyAndFriendsListResponse(List<FamilyAndFriendsModel> familyAndFriendsList) {
        this.familyAndFriendsList = familyAndFriendsList;
    }

    public List<FamilyAndFriendsModel> getFamilyAndFriendsList() {
        return familyAndFriendsList;
    }

    public void setFamilyAndFriendsList(List<FamilyAndFriendsModel> familyAndFriendsList) {
        this.familyAndFriendsList = familyAndFriendsList;
    }
}
