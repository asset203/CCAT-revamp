package com.asset.ccat.air_service.controllers;

import com.asset.ccat.air_service.defines.Defines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.FamilyAndFriendsModel;
import com.asset.ccat.air_service.models.requests.SubscriberRequest;
import com.asset.ccat.air_service.models.requests.customer_care.family_and_friends.AddFamilyAndFriendsRequest;
import com.asset.ccat.air_service.models.requests.customer_care.family_and_friends.DeleteFamilyAndFriendsRequest;
import com.asset.ccat.air_service.models.requests.customer_care.family_and_friends.UpdateFamilyAndFriendsRequest;
import com.asset.ccat.air_service.models.responses.BaseResponse;
import com.asset.ccat.air_service.services.FamilyAndFriendsService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Defines.ContextPaths.FAMILY_AND_FRIENDS)
public class FamilyAndFriendsController {

    @Autowired
    private FamilyAndFriendsService familyAndFriendsService;

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<List<FamilyAndFriendsModel>> getFAFList(@RequestBody SubscriberRequest subscriberRequest) throws AIRException, AIRServiceException {
        ThreadContext.put("sessionId", subscriberRequest.getSessionId());
        ThreadContext.put("requestId", subscriberRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get FAFList Request [" + subscriberRequest + "]");
        List<FamilyAndFriendsModel> response = familyAndFriendsService.getSubscriberFAFList(subscriberRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get FAFList Successfully!!");

        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse addFAFList(@RequestBody AddFamilyAndFriendsRequest addFamilyAndFriendsRequest) throws AIRException, AIRServiceException {
        ThreadContext.put("sessionId", addFamilyAndFriendsRequest.getSessionId());
        ThreadContext.put("requestId", addFamilyAndFriendsRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Add FAFList Request [" + addFamilyAndFriendsRequest + "]");
        familyAndFriendsService.addSubscriberFAFList(addFamilyAndFriendsRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Add FAFList Successfully!!");

        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateFAFList(@RequestBody UpdateFamilyAndFriendsRequest updateFamilyAndFriendsRequest) throws AIRException, AIRServiceException {
        ThreadContext.put("sessionId", updateFamilyAndFriendsRequest.getSessionId());
        ThreadContext.put("requestId", updateFamilyAndFriendsRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update FAFList Request [" + updateFamilyAndFriendsRequest + "]");
        familyAndFriendsService.updateSubscriberFAFList(updateFamilyAndFriendsRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update FAFList Successfully!!");

        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deleteFAFList(@RequestBody DeleteFamilyAndFriendsRequest deleteFamilyAndFriendsRequest) throws AIRException, AIRServiceException {
        ThreadContext.put("sessionId", deleteFamilyAndFriendsRequest.getSessionId());
        ThreadContext.put("requestId", deleteFamilyAndFriendsRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Delete FAFList Request [" + deleteFamilyAndFriendsRequest + "]");
        familyAndFriendsService.deleteSubscriberFAFList(deleteFamilyAndFriendsRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Delete FAFList request Successfully");

        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }
}
