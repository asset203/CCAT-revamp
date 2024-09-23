package com.asset.ccat.gateway.util;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.models.requests.BaseRequest;
import com.asset.ccat.gateway.models.shared.FootprintAttributesModel;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.rabbitmq.models.FootprintModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class FootprintUtil {

    private final GatewayUtil gatewayUtil;

    @Autowired
    public FootprintUtil(JwtTokenUtil jwtTokenUtil, GatewayUtil gatewayUtil) {
        this.gatewayUtil = gatewayUtil;
    }

    public void populateFootprint(@NonNull BaseRequest baseRequest, @NonNull FootprintAttributesModel details, HashMap<String, Object> tokenData) throws GatewayException {
        FootprintModel footprintModel = Optional.ofNullable(baseRequest.getFootprintModel())
                .orElse(new FootprintModel());

        extractAndSetFootprintData(baseRequest, tokenData, footprintModel, details);

        baseRequest.setFootprintModel(footprintModel);
    }

    private void extractAndSetFootprintData(BaseRequest baseRequest, HashMap<String, Object> tokenData, FootprintModel footprintModel, FootprintAttributesModel details)   {

        footprintModel.setRequestId(details.getRequestId());
        footprintModel.setSessionId((String) tokenData.get(Defines.SecurityKeywords.SESSION_ID));
        footprintModel.setUserName((String) tokenData.get(Defines.SecurityKeywords.USERNAME));
        footprintModel.setProfileName((String) tokenData.get(Defines.SecurityKeywords.PROFILE_NAME));
        footprintModel.setActionType(details.getActionType());
        footprintModel.setActionName(details.getActionName());
        footprintModel.setStatus(details.getFootprintStatus());
        footprintModel.setErrorCode(details.getErrorCode());
        footprintModel.setErrorMessage(details.getErrorMessage());
        footprintModel.setMachineName(gatewayUtil.getHostNameIfExist());

        baseRequest.setFootprintModel(footprintModel);
    }

}
