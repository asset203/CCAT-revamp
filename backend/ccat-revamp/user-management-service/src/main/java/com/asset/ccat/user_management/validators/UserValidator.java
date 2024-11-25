package com.asset.ccat.user_management.validators;

import com.asset.ccat.user_management.defines.Defines;
import com.asset.ccat.user_management.defines.ErrorCodes;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.logger.CCATLogger;
import com.asset.ccat.user_management.services.ProfileService;
import com.asset.ccat.user_management.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author marwa.elshawarby
 */
@Component
public class UserValidator {

    @Autowired
    private ProfileService profileService;


    public void isProfileExists(Integer profileId) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.info("Start validating if profile[" + profileId + "] exists");
        if (profileId == null || !profileService.isProfileExists(profileId)) {
            CCATLogger.DEBUG_LOGGER.debug("Validating user Failed , Profile with ID [" + profileId + "] does not exist");
            throw new UserManagementException(ErrorCodes.ERROR.PROFILE_NOT_FOUND, Defines.SEVERITY.VALIDATION);
        }
        CCATLogger.DEBUG_LOGGER.info("Finished validating if profile exists successfully");
    }
}
