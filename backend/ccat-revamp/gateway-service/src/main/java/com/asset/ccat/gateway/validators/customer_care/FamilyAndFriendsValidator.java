package com.asset.ccat.gateway.validators.customer_care;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.SubscriberRequest;
import com.asset.ccat.gateway.models.requests.customer_care.family_and_friends.AddFamilyAndFriendsRequest;
import com.asset.ccat.gateway.models.requests.customer_care.family_and_friends.DeleteFamilyAndFriendsRequest;
import com.asset.ccat.gateway.models.requests.customer_care.family_and_friends.UpdateFamilyAndFriendsRequest;
import com.asset.ccat.gateway.util.GatewayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class FamilyAndFriendsValidator {

    @Autowired
    private GatewayUtil gatewayUtil;

    public void validateGetFafListRequest(SubscriberRequest subscriberRequest) throws GatewayException {
        if (!gatewayUtil.isMsisdnValid(subscriberRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, "msisdn");
        }
    }

    public void validateAddFafListRequest(AddFamilyAndFriendsRequest addFamilyAndFriendsRequest) throws GatewayException {
        if (!gatewayUtil.isMsisdnValid(addFamilyAndFriendsRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, "msisdn");
        } else if (Objects.isNull(addFamilyAndFriendsRequest.getFamilyAndFriendsNumber())
                || addFamilyAndFriendsRequest.getFamilyAndFriendsNumber().isEmpty()
                || addFamilyAndFriendsRequest.getFamilyAndFriendsNumber().isBlank()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "familyAndFriendsNumber");
        } else if (Objects.isNull(addFamilyAndFriendsRequest.getFamilyAndFriendsPlanId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "familyAndFriendsPlan");
        }
        if(addFamilyAndFriendsRequest.getMsisdn().equals(addFamilyAndFriendsRequest.getFamilyAndFriendsNumber())){
            throw new GatewayValidationException(ErrorCodes.WARNING.SUBSCRIBER_CANNOT_HAVE_FAF);
        }
    }

    public void validateUpdateFafListRequest(UpdateFamilyAndFriendsRequest updateFamilyAndFriendsRequest) throws GatewayException {
        if (!gatewayUtil.isMsisdnValid(updateFamilyAndFriendsRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, "msisdn");
        } else if (Objects.isNull(updateFamilyAndFriendsRequest.getFamilyAndFriendsNumber())
                || updateFamilyAndFriendsRequest.getFamilyAndFriendsNumber().isEmpty()
                || updateFamilyAndFriendsRequest.getFamilyAndFriendsNumber().isBlank()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "familyAndFriendsNumber");
        } else if (Objects.isNull(updateFamilyAndFriendsRequest.getFamilyAndFriendsPlanId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "familyAndFriendsPlan");
        }
    }

    public void validateDeleteFafListRequest(DeleteFamilyAndFriendsRequest deleteFamilyAndFriendsRequest) throws GatewayException {
        if (!gatewayUtil.isMsisdnValid(deleteFamilyAndFriendsRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, "msisdn");
        } else if (Objects.isNull(deleteFamilyAndFriendsRequest.getFamilyAndFriendsNumber())
                || deleteFamilyAndFriendsRequest.getFamilyAndFriendsNumber().isEmpty()
                || deleteFamilyAndFriendsRequest.getFamilyAndFriendsNumber().isBlank()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "familyAndFriendsNumber");
        } else if (Objects.isNull(deleteFamilyAndFriendsRequest.getFamilyAndFriendsPlanId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "familyAndFriendsPlan");
        }
    }

}
