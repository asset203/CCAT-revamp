package com.asset.ccat.air_service.mappers;

import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.models.CommunitiesModel;
import com.asset.ccat.air_service.models.FamilyAndFriendsModel;
import com.asset.ccat.air_service.models.responses.customer_care.GetCommunitiesResponse;
import com.asset.ccat.air_service.models.shared.LookupModel;
import com.asset.ccat.air_service.services.LookupsService;
import com.asset.ccat.air_service.utils.AIRUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Component
public class GetCommunitiesMapper {

    @Autowired
    private LookupsService lookupsService;

    public GetCommunitiesResponse map(String msisdn, HashMap map) throws AIRServiceException {
        List<LookupModel> selectedCommunitiesIds = (List<LookupModel>) map.get(AIRDefines.communityInformationCurrent);
        List<CommunitiesModel> selectedCommunites = new ArrayList<>();
        List<CommunitiesModel> unselectedCommunities;
        HashMap<Integer, CommunitiesModel> allCommunities = lookupsService.getCommunities();

        if(Objects.nonNull(selectedCommunitiesIds)){
            selectedCommunitiesIds.forEach(communityModel -> {
                Integer id = Integer.valueOf(communityModel.getKey());
                String desc = "";
                if (allCommunities.get(id) == null) {
                    desc = communityModel.getKey();
                } else {
                    desc = allCommunities.get(id).getCommunityDescription();
                    allCommunities.remove(id); // remove selectedCommunity from list of all communities to filter unselected communities
                }
                selectedCommunites.add(new CommunitiesModel(id, desc));
            });
        }


        unselectedCommunities = new ArrayList<>(allCommunities.values());

        return new GetCommunitiesResponse(selectedCommunites, unselectedCommunities);
    }
}
