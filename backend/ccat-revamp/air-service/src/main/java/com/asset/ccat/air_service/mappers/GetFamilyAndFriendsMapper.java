package com.asset.ccat.air_service.mappers;

import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.models.FafPlanModel;
import com.asset.ccat.air_service.models.FamilyAndFriendsModel;
import com.asset.ccat.air_service.services.LookupsService;
import com.asset.ccat.air_service.utils.AIRUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class GetFamilyAndFriendsMapper {

    @Autowired
    AIRUtils aIRUtils;

    @Autowired
    private LookupsService lookupsService;

    public List<FamilyAndFriendsModel> map(String msisdn, HashMap map, String owner) {

        List<FamilyAndFriendsModel> fafList = (List<FamilyAndFriendsModel>) map.get(AIRDefines.fafInformationList);
        List<FamilyAndFriendsModel> mappedFafList = new ArrayList<>();
//        HashMap<Integer, FafPlanModel>

        // return empty list if nth is returned from air
        // TODO map fafPlan and
        if (fafList != null && !fafList.isEmpty()) {
            for (int i = 0; i < fafList.size(); i++) {
                if (fafList.get(i).getOwner().equalsIgnoreCase(AIRDefines.FAMILY_AND_FRIENDS.FAF_OWNER_SUB) && owner.equals(AIRDefines.FAMILY_AND_FRIENDS.FAF_OWNER_SUB_ID)) {
                    mappedFafList.add(fafList.get(i));
                } else if (fafList.get(i).getOwner().equalsIgnoreCase(AIRDefines.FAMILY_AND_FRIENDS.FAF_OWNER_ACC) && owner.equals(AIRDefines.FAMILY_AND_FRIENDS.FAF_OWNER_ACC_ID)) {
                    mappedFafList.add(fafList.get(i));
                }
            }
        }
        return mappedFafList;
    }
}
