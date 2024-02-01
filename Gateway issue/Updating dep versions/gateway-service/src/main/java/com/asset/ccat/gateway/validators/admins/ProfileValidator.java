package com.asset.ccat.gateway.validators.admins;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.admin.profile.AddProfileRequest;
import com.asset.ccat.gateway.models.requests.admin.profile.DeleteProfileRequest;
import com.asset.ccat.gateway.models.requests.admin.profile.GetProfileRequest;
import com.asset.ccat.gateway.models.requests.admin.profile.UpdateProfileRequest;
import com.asset.ccat.gateway.models.requests.admin.profile_features.GetProfileUsersRequest;
import com.asset.ccat.gateway.models.users.LkFeatureModel;

import java.util.Objects;

import org.springframework.stereotype.Component;

/**
 * @author nour.ihab
 */
@Component
public class ProfileValidator {

    public void validateGetProfile(GetProfileRequest getProfileRequest) throws GatewayException {
        if (Objects.isNull(getProfileRequest.getProfileId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "profileId");
        }
    }

    public void getAllUserProfile(GetProfileUsersRequest getProfileUsersRequest) throws GatewayException {
        if (Objects.isNull(getProfileUsersRequest.getProfileId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "profileId");
        }
    }

    public void validateAddProfile(AddProfileRequest addProfileRequest) throws GatewayException {
        if (Objects.isNull(addProfileRequest.getProfile().getProfileName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "profileName");
        }
        for (LkFeatureModel lkCCFeatures : addProfileRequest.getProfile().getCcFeatures()) {
            if (Objects.isNull(lkCCFeatures.getId())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "FeatureId");
            } else if (Objects.isNull(lkCCFeatures.getName())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "FeatureName for [" + lkCCFeatures.getId() + "]");
            }
        }
        for (LkFeatureModel lkSysFeatures : addProfileRequest.getProfile().getSysFeatures()) {
            if (Objects.isNull(lkSysFeatures.getId())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "FeatureId");
            } else if (Objects.isNull(lkSysFeatures.getName())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "FeatureName for [" + lkSysFeatures.getId() + "]");
            }
        }

    }

    public void validateUpdateProfile(UpdateProfileRequest updateProfileRequest) throws GatewayException {
        if (Objects.isNull(updateProfileRequest.getProfile().getProfileName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "profileName");
        } else if (Objects.isNull(updateProfileRequest.getProfile().getProfileId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "profileId");
        }
        if (updateProfileRequest.getProfile().getCcFeatures() != null) {
            for (LkFeatureModel lkCCFeatures : updateProfileRequest.getProfile().getCcFeatures()) {
                if (Objects.isNull(lkCCFeatures.getId())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "FeatureId");
                } else if (Objects.isNull(lkCCFeatures.getName())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "FeatureName for [" + lkCCFeatures.getId() + "]");
                }
            }
        }
        if (updateProfileRequest.getProfile().getSysFeatures() != null) {
            for (LkFeatureModel lkSysFeatures : updateProfileRequest.getProfile().getSysFeatures()) {
                if (Objects.isNull(lkSysFeatures.getId())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "FeatureId");
                } else if (Objects.isNull(lkSysFeatures.getName())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "FeatureName for [" + lkSysFeatures.getId() + "]");
                }
            }
        }

        if (updateProfileRequest.getProfile().getDssReportsFeatures() != null) {
            for (LkFeatureModel lkDssFeatures : updateProfileRequest.getProfile().getDssReportsFeatures()) {
                if (Objects.isNull(lkDssFeatures.getId())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "FeatureId");
                } else if (Objects.isNull(lkDssFeatures.getName())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "FeatureName for [" + lkDssFeatures.getId() + "]");
                }
            }
        }
    }

    public void validateDeleteProfile(DeleteProfileRequest deleteProfileRequest) throws GatewayException {
        if (Objects.isNull(deleteProfileRequest.getProfileId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "profileId");
        }
    }

}
