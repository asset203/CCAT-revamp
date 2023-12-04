package com.asset.ccat.gateway.validators.admins;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.admin.community_admin.AddCommunityAdminRequest;
import com.asset.ccat.gateway.models.requests.admin.community_admin.DeleteCommunityAdminRequest;
import com.asset.ccat.gateway.models.requests.admin.community_admin.UpdateCommunityAdminRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author mohamed.metwaly
 */
@Component
public class CommunityAdminValidator {
    public void addCommunityAdminValidator(AddCommunityAdminRequest request) throws GatewayException {
        if (Objects.isNull(request.getAddedCommunity().getCommunityId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "communityId");
        }
        else if (Objects.isNull(request.getAddedCommunity().getCommunityDescription())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "communityDescription");
        }

    }
    public void updateCommunityAdminValidator(UpdateCommunityAdminRequest request) throws GatewayException{
        if (Objects.isNull(request.getUpdatedCommunity().getCommunityId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "communityId");
        }
        else if (Objects.isNull(request.getUpdatedCommunity().getCommunityDescription())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "communityDescription");
        }
    }
    public void deleteCommunityAdminValidator(DeleteCommunityAdminRequest request) throws GatewayException{
        if (Objects.isNull(request.getCommunityId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "communityId");
        }
    }
}
