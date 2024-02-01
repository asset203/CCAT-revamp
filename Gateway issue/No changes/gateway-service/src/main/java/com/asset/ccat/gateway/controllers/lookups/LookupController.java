package com.asset.ccat.gateway.controllers.lookups;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.admin.AccountGroupBitDescModel;
import com.asset.ccat.gateway.models.admin.ReasonActivityModel;
import com.asset.ccat.gateway.models.requests.admin.account_groups.GetAllAccountGroupsRequest;
import com.asset.ccat.gateway.models.requests.lookup.*;
import com.asset.ccat.gateway.models.requests.shared.BaseRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.account_groups_bits_desc.GetAllAccountGroupsBitsDescResponse;
import com.asset.ccat.gateway.models.responses.lookup.*;
import com.asset.ccat.gateway.models.shared.FafIndicatorModel;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.LookupsService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author marwa.elshawarby
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = Defines.ContextPaths.LOOKUP, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class LookupController {

    @Autowired
    private LookupsService lookupService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @PostMapping(value = Defines.ContextPaths.OFFERS + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetOffersLKResponse> getOffers(HttpServletRequest req,
                                                       @RequestBody BaseRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Offers Request [" + request + "]");
        GetOffersLKResponse response = lookupService.getOffersLookup();
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Offers Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                response);
    }

    @PostMapping(value = Defines.ContextPaths.FEATURES + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetFeaturesLKResponse> getFeaturesLookup(HttpServletRequest req,
                                                                 @RequestBody GetFeaturesLKRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Recieved getFeaturesLookup request [ " + request + " ]");
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Features Lookup Request [" + request + "]");
        GetFeaturesLKResponse response = lookupService.getFeaturesLookup();
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Features Lookup Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                response);
    }

    @PostMapping(value = Defines.ContextPaths.MONETARY_LIMITS + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetMonetaryLimitsLKResponse> getMonetaryLimitsLookup(HttpServletRequest req,
                                                                             @RequestBody GetMonetaryLimitsLKRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get MonetaryLimits Lookup Request [" + request + "]");
        GetMonetaryLimitsLKResponse response = lookupService.getMonetaryLimitsLookup();
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get MonetaryLimits Lookup Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                response);
    }

    @PostMapping(value = Defines.LOOKUP_SERVICE.HLR_PROFILES + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllHLRProfileResponse> getAllHLRProfiles(HttpServletRequest req,
                                                                    @RequestBody BaseRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get AllHLRProfiles Request [" + request + "]");
        GetAllHLRProfileResponse hLRProfileResponse = lookupService.getHLRProfilesLookup();
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All HLR Profiles Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                hLRProfileResponse);
    }

    @PostMapping(value = Defines.LOOKUP_SERVICE.PAMS + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllLookupPamResponse> getAllLookupsPams(HttpServletRequest req,
                                                                   @RequestBody GetAllPamsTypeLKRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Lookups Pams Request [" + request + "]");
        GetAllLookupPamResponse getAllPamsTypeLKResponse = lookupService.getAllPams();
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Lookups Pams Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                getAllPamsTypeLKResponse);
    }

    @PostMapping(value = Defines.ContextPaths.ACCOUNT_GROUPS_LOOKUP + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllAccountGroupsResponse> getAccountGroups(HttpServletRequest req,
                                                                      @RequestBody GetAllAccountGroupsRequest request) throws AuthenticationException, GatewayException {

        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Account Groups Request [" + request + "]");
        GetAllAccountGroupsResponse response = lookupService.getAllAccountsGroups();
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Account Groups Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                response);
    }


    @PostMapping(value = Defines.ContextPaths.ACCOUNT_GROUP_BITS_LOOKUP + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllAccountGroupsBitsDescResponse> getAccountGroupBits(HttpServletRequest req,
                                                                                 @RequestBody BaseRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Account Group bits desc Request [" + request + "]");
//        GetAllAccountGroupsResponse response = lookupService.getAllAccountsGroups();
        List<AccountGroupBitDescModel> bits = new ArrayList<>();
        bits.add(new AccountGroupBitDescModel(1, "bit 01"));
        bits.add(new AccountGroupBitDescModel(2, "bit 02"));
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Account Group bits desc Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                new GetAllAccountGroupsBitsDescResponse(bits));
    }


    @PostMapping(value = Defines.LOOKUP_SERVICE.COMMUNITIES + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetCommunitiesResponse> getAllCommunities(HttpServletRequest req,
                                                                  @RequestBody BaseRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Communities Request [" + request + "]");
//        ArrayList list = new ArrayList();
//        for (int i = 0; i < 6; i++) {
//
//            LookupModel model = new LookupModel();
//            model.setId(i);
//            model.setName("community " + i);
//
//            list.add(model);
//        }
        // GetAllCommunitiesResponse response = new GetAllCommunitiesResponse();
//        response.setCommunities(list);
//        CCATLogger.DEBUG_LOGGER.debug(" All communities are Retrived Succssfully");
//        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
//                "success",
//                0,
//                request.getRequestId(),
//                response);
        GetCommunitiesResponse response = lookupService.getAllCommunities();
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Communities Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                response);
    }

    @PostMapping(value = Defines.LOOKUP_SERVICE.SERVICE_OFFERING + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllServiceOfferingResponse> getAllServiceOffering(HttpServletRequest req,
                                                                             @RequestBody BaseRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Service Offering Request [" + request + "]");
        GetAllServiceOfferingResponse response = lookupService.getAllServiceOfferings();
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Service Offering Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                response);
    }

    @PostMapping(value = Defines.LOOKUP_SERVICE.FAF_PLANS + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllFafPlansResponse> getAllFafPlans(HttpServletRequest req,
                                                               @RequestBody BaseRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Faf Plans Request [" + request + "]");
        GetAllFafPlansResponse response = lookupService.getAllFafPlans();
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Faf Plans Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                response);
    }

    @PostMapping(value = Defines.ContextPaths.MARED_CARDS + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetMaredCardsLKResponse> getMaredCardsLookup(HttpServletRequest req,
                                                                     @RequestBody GetMaredCardsLKRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Mared Cards Lookup [" + request + "]");
        GetMaredCardsLKResponse response = lookupService.getMaredCards();
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Mared Cards Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                response);
    }

    @PostMapping(value = Defines.ContextPaths.FAF_INDICATORS + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllFafIndicatorsResponse> getAllFafIndicators(@RequestBody GetAllFafIndicatorsRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Faf Indicators [" + request + "]");
        List<FafIndicatorModel> fafIndicators = this.lookupService.getAllFafIndicators();
        GetAllFafIndicatorsResponse payload = new GetAllFafIndicatorsResponse(fafIndicators);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Faf Indicators Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                payload);
    }

    @PostMapping(value = Defines.ContextPaths.CALL_ACTIVITIES + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllCallActivitiesResponse> getAllCallActivities(@RequestBody GetAllCallActivitiesRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Call Activities Request [" + request + "]");
        List<ReasonActivityModel> activities = this.lookupService.getCallActivities(request);
        GetAllCallActivitiesResponse payload = new GetAllCallActivitiesResponse(activities);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Call Activities Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                payload);
    }

    @PostMapping(value = Defines.ContextPaths.SMS_ACTIONS + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetSmsActionsResponse> getSmsActions(@RequestBody GetSmsActionsRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Sms Actions Request [" + request + "]");
        GetSmsActionsResponse payload = this.lookupService.getSmsActions(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Sms Actions Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                payload);
    }

    @PostMapping(value = Defines.ContextPaths.SMS_ACTION_PARAM_MAP + Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetSmsActionParamMapResponse> getSmsActionParamMap(@RequestBody GetSmsActionParamMapRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Sms Action Param Map Request [" + request + "]");
        GetSmsActionParamMapResponse payload = this.lookupService.getSmsActionParamMap(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Sms Action Param Map Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                payload);
    }

    @PostMapping(value = Defines.ContextPaths.ACTIVITIES_HEADERS + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetODSActivityHeaderResponse> getODSActivityHeader(@RequestBody GetAllODSActivityHeaderRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Sms Action Param Map Request [" + request + "]");
        GetODSActivityHeaderResponse payload = this.lookupService.getODSActivityHeader(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Sms Action Param Map Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
            "success",
            Defines.SEVERITY.CLEAR,
            request.getRequestId(),
            payload);
    }
}
