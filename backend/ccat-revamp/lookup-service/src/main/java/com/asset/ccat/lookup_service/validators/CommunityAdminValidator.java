package com.asset.ccat.lookup_service.validators;

import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.CommunityAdminModel;
import com.asset.ccat.lookup_service.services.CommunityAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommunityAdminValidator {

    @Autowired
    CommunityAdminService communityAdminService;

    public void isCommunityAdminUpdateValid(CommunityAdminModel communityAdmin) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Start validating if community admin [" + communityAdmin + "] exists");
        if (!communityAdminService.isCommunityIdExists(communityAdmin.getCommunityId())) {
            CCATLogger.DEBUG_LOGGER.debug("Validating community admin failed , Community with ID [" + communityAdmin.getCommunityId() + "] not found");
            throw new LookupException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.VALIDATION,"Community Admin Id:"+ communityAdmin.getCommunityId()+"");
        }
        if (communityAdminService.isCommunityDescExists(communityAdmin)) {
            CCATLogger.DEBUG_LOGGER.debug("Validating community admin failed , Community with Name [" + communityAdmin.getCommunityDescription() + "] exist");
            throw new LookupException(ErrorCodes.ERROR.DUPLICATED_DATA, Defines.SEVERITY.VALIDATION,"community admin Description:"+ communityAdmin.getCommunityDescription());
        }
        CCATLogger.DEBUG_LOGGER.info("Finished validating  Community Admin  successfully");
    }

    public void isCommunityAdminAddValid(CommunityAdminModel communityAdmin) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Start validating if Community Admin [" + communityAdmin + "] exists");
        if (communityAdminService.isCommunityIdExists(communityAdmin.getCommunityId())) {
            CCATLogger.DEBUG_LOGGER.debug("Validating community Admin failed , communityAdmin with ID [" + communityAdmin.getCommunityId() + "] exist");
            throw new LookupException(ErrorCodes.ERROR.DUPLICATED_DATA, Defines.SEVERITY.VALIDATION,"Account Group Id:" + communityAdmin.getCommunityId()+"");
        }
        if (communityAdminService.isCommunityDescExists(communityAdmin)) {
            CCATLogger.DEBUG_LOGGER.debug("Validating community Admin failed , communityAdmin with Name [" + communityAdmin.getCommunityDescription() + "] exist");
            throw new LookupException(ErrorCodes.ERROR.DUPLICATED_DATA, Defines.SEVERITY.VALIDATION,"community Admin Description:"+ communityAdmin.getCommunityDescription());
        }
        CCATLogger.DEBUG_LOGGER.info("Finished validating  Community Admin successfully");
    }
}
