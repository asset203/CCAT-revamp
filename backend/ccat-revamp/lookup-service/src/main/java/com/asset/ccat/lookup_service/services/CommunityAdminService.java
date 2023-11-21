package com.asset.ccat.lookup_service.services;


import com.asset.ccat.lookup_service.database.dao.CommunityAdminDao;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.CommunityAdminModel;
import com.asset.ccat.lookup_service.models.FafPlanModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityAdminService {

    @Autowired
    CommunityAdminDao communityAdminDao;

    public List<CommunityAdminModel> getAllCommunities() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all Communities.");
        List<CommunityAdminModel> communities = communityAdminDao.retrieveCommunities();
        if (communities == null || communities.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.error("No communities were found.");
            throw new LookupException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, "communities");
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving all communities Admin with size[" + communities.size() + "].");
        return  communities;
    }


    public void updateCommunityAdmin(CommunityAdminModel community) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start updateCommunityAdmin with COMMUNITY_ID[" + community.getCommunityId() + "].");
        int isUpdated = communityAdminDao.updateCommunityAdmin(community);
        if (isUpdated <= 0) {
            CCATLogger.DEBUG_LOGGER.error("Failed to update CommunityAdmin.");
            throw new LookupException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "communityId " + community.getCommunityId());
        }
        CCATLogger.DEBUG_LOGGER.debug("Done updating communityAdmin[" + community.getCommunityId() + "].");
    }



    public void deleteCommunityAdmin(int communityId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start deleteCommunityAdmin with COMMUNITY_ID[" + communityId + "].");
        int isDeleted = communityAdminDao.deleteCommunityAdmin(communityId);
        if (isDeleted <= 0) {
            CCATLogger.DEBUG_LOGGER.error("Failed to delete CommunityAdmin.");
            throw new LookupException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "communityId " + communityId);
        }
        CCATLogger.DEBUG_LOGGER.debug("Done deleting communityAdmin[" + communityId + "].");
    }
    public void addCommunityAdmin(CommunityAdminModel community) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start adding community ]");
        int isAdded = communityAdminDao.addCommunityAdmin(community);
        if (isAdded <= 0) {
            CCATLogger.DEBUG_LOGGER.error("Failed to add communityAdmin.");
            throw new LookupException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "communityId " + community.getCommunityId());
        }
        CCATLogger.DEBUG_LOGGER.debug("Done adding communityAdmin with communityId[" + community.getCommunityId()+ "].");
    }


    public Boolean isCommunityIdExists(int communityId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Checking if account group  with ID[" + communityId+ "] exists in DB");
        return communityAdminDao.findCommunityAdminById(communityId) > 0;
    }

    public Boolean isCommunityDescExists(CommunityAdminModel community) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Checking if account group with Description[" + community.getCommunityDescription() +"] exists in DB");
        List<CommunityAdminModel> res =communityAdminDao.findCommunityAdmin(community);
        if(res.size() == 0) return false;
        return (!res.get(0).getCommunityId().equals(community.getCommunityId()));
    }

}
