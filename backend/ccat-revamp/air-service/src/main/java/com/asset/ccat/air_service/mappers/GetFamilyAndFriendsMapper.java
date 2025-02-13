package com.asset.ccat.air_service.mappers;

import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.FafPlanModel;
import com.asset.ccat.air_service.models.FamilyAndFriendsModel;
import com.asset.ccat.air_service.services.LookupsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class GetFamilyAndFriendsMapper {
    private final LookupsService lookupsService;

    public GetFamilyAndFriendsMapper(LookupsService lookupsService) {
        this.lookupsService = lookupsService;
    }

    public List<FamilyAndFriendsModel> map(HashMap map, String owner) throws AIRServiceException {
        List<FamilyAndFriendsModel> fafList = (List<FamilyAndFriendsModel>) map.get(AIRDefines.fafInformationList);
        List<FamilyAndFriendsModel> mappedFafList = new ArrayList<>();
        if (fafList != null && !fafList.isEmpty()) {
            for (FamilyAndFriendsModel fafModel : fafList) {
                FafPlanModel fafLookup = getFafLookup(fafModel.getInd());
                fafModel.setPlan(fafLookup.getName());
                fafModel.setId(fafLookup.getPlanId() != null ? fafLookup.getPlanId().toString() : null);
                if (fafModel.getOwner().equalsIgnoreCase(AIRDefines.FAMILY_AND_FRIENDS.FAF_OWNER_SUB) && owner.equals(AIRDefines.FAMILY_AND_FRIENDS.FAF_OWNER_SUB_ID)) {
                    mappedFafList.add(fafModel);
                } else if (fafModel.getOwner().equalsIgnoreCase(AIRDefines.FAMILY_AND_FRIENDS.FAF_OWNER_ACC) && owner.equals(AIRDefines.FAMILY_AND_FRIENDS.FAF_OWNER_ACC_ID)) {
                    mappedFafList.add(fafModel);
                }
            }
        }
        return mappedFafList;
    }

    private FafPlanModel getFafLookup(String fafInd) throws AIRServiceException {
        HashMap<Integer, FafPlanModel> fafLookupsMap = lookupsService.getFafPlans();
        try {
            Integer fafId = Integer.parseInt(fafInd);
            return fafLookupsMap.getOrDefault(fafId, new FafPlanModel());
        } catch (Exception ex){
            CCATLogger.DEBUG_LOGGER.error("Exception while getting faf lookup: {}", ex.getMessage());
            CCATLogger.ERROR_LOGGER.error("Exception while getting faf lookup: ", ex);
        }
        return new FafPlanModel();
    }
}
