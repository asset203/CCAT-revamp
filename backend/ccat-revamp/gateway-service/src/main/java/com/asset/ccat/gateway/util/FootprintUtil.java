package com.asset.ccat.gateway.util;

import com.asset.ccat.gateway.cache.LookupsCache;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.models.requests.BaseRequest;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.rabbitmq.models.FootprintModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Service
public class FootprintUtil {

    private final JwtTokenUtil jwtTokenUtil;

    private final LookupsCache lookupsCache;

    @Autowired
    public FootprintUtil(JwtTokenUtil jwtTokenUtil, LookupsCache lookupsCache) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.lookupsCache = lookupsCache;
    }

    /**
     * Gathers footprint data from four main sources:
     * 1. Requests received (machine name, msisdn, sendSms).
     * 2. Cached data (retrieved from lookupsCache for page, action, and type).
     * 3. Token data (session, username, profile information).
     * 4. System's response (error code and error message).
     */
    public void populateFootprint(BaseRequest baseRequest, String requestId, String pageName, String actionName,
                                  String actionType, String errorCode, String errorMessage, String status) throws GatewayException {
        FootprintModel footprintModel = Optional.ofNullable(baseRequest.getFootprintModel())
                .orElse(new FootprintModel());

        extractAndSetFootprintData(baseRequest, requestId, pageName, actionName, actionType,
                errorCode, errorMessage, status, footprintModel);

        baseRequest.setFootprintModel(footprintModel);
    }
    public void populateFootprint(BaseRequest baseRequest, String requestId, String controllerName, String errorCode, String errorMessage,
                                  String methodName, String status) throws GatewayException {
        // Retrieve cached data from lookupsCache
        String pageName = lookupsCache.getFootPrintPages().get(controllerName).getPageName();
        String actionName = lookupsCache.getFootPrintPages()
                .get(controllerName)
                .getFootprintPageInfoMap()
                .get(methodName).getActionName();
        String actionType = lookupsCache.getFootPrintPages()
                .get(controllerName)
                .getFootprintPageInfoMap()
                .get(methodName).getActionType();

        // Set defaults if values are null or empty
        pageName = Objects.isNull(pageName) || pageName.isEmpty() ? controllerName.replace("Controller", "") : pageName;
        actionName = Objects.isNull(actionName) || actionName.isEmpty() ? methodName : actionName;
        actionType = Objects.isNull(actionType) || actionType.isEmpty() ? methodName : actionType;

        populateFootprint(baseRequest, requestId, pageName, actionName, actionType, errorCode, errorMessage,
                status);
    }


    private void extractAndSetFootprintData(BaseRequest baseRequest, String requestId, String pageName, String actionName,
                                            String actionType, String errorCode, String errorMessage,
                                            String status, FootprintModel footprintModel) throws GatewayException {
        extractAndSetRequestData(baseRequest, requestId, footprintModel);
        extractAndSetTokenData(baseRequest.getToken(), footprintModel);
        extractAndSetCachedData(pageName, actionName, actionType, footprintModel);
        extractAndSetResponseDate(errorCode, errorMessage, status, footprintModel);
    }

    private void extractAndSetRequestData(BaseRequest baseRequest, String requestId, FootprintModel footprintModel) {
        // Extract machineName, msisdn, and sendSms from the footprint model in the base request
        FootprintModel requestFootprint = baseRequest.getFootprintModel();
        if (requestFootprint != null) {
            footprintModel.setMachineName(requestFootprint.getMachineName());
            footprintModel.setMsisdn(requestFootprint.getMsisdn());
            footprintModel.setSendSms(requestFootprint.getSendSms());
        }
        footprintModel.setRequestId(requestId);
    }

    private void extractAndSetCachedData(String pageName, String actionName, String actionType, FootprintModel footprintModel){
        footprintModel.setPageName(pageName);
        footprintModel.setActionName(actionName);
        footprintModel.setActionType(actionType);
    }

    private void extractAndSetTokenData(String token, FootprintModel footprintModel) throws GatewayException {
        HashMap<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(token);
        footprintModel.setSessionId(tokenData.get(Defines.SecurityKeywords.SESSION_ID).toString());
        footprintModel.setUserName(tokenData.get(Defines.SecurityKeywords.USERNAME).toString());
        footprintModel.setProfileName(tokenData.get(Defines.SecurityKeywords.PROFILE_NAME).toString());
    }

    private void extractAndSetResponseDate(String errorCode, String errorMessage, String status, FootprintModel footprintModel){
        footprintModel.setErrorCode(errorCode);
        footprintModel.setErrorMessage(errorMessage);
        footprintModel.setStatus(status);
    }

}
