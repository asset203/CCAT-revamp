package com.asset.ccat.lookup_service.controllers.admin;


import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.models.BaseResponse;
import com.asset.ccat.lookup_service.models.CommunityAdminModel;
import com.asset.ccat.lookup_service.models.requests.community_admin.AddCommunityAdminRequest;
import com.asset.ccat.lookup_service.models.requests.community_admin.DeleteCommunityAdminRequest;
import com.asset.ccat.lookup_service.models.requests.community_admin.GetAllCommunitiesRequest;
import com.asset.ccat.lookup_service.models.requests.community_admin.UpdateCommunityAdminRequest;
import com.asset.ccat.lookup_service.services.CommunityAdminService;
import com.asset.ccat.lookup_service.validators.CommunityAdminValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = Defines.ContextPaths.COMMUNITIES)
public class CommunityAdminController {

    @Autowired
    CommunityAdminService communityAdminService;

    @Autowired
    CommunityAdminValidator communityAdminValidator;

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<List<CommunityAdminModel>> getAllCommunities(GetAllCommunitiesRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        List<CommunityAdminModel> response = communityAdminService.getAllCommunities();
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR,
                response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateCommunityAdmin(@RequestBody UpdateCommunityAdminRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        communityAdminValidator.isCommunityAdminUpdateValid(request.getUpdatedCommunity());
        communityAdminService.updateCommunityAdmin(request.getUpdatedCommunity());
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse addCommunityAdmin(@RequestBody AddCommunityAdminRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        communityAdminValidator.isCommunityAdminAddValid(request.getAddedCommunity());
        communityAdminService.addCommunityAdmin(request.getAddedCommunity());
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deleteCommunityAdmin(@RequestBody DeleteCommunityAdminRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        communityAdminService.deleteCommunityAdmin(request.getCommunityId());
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }

}
