package com.asset.ccat.air_service.controllers;

import com.asset.ccat.air_service.defines.Defines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.AccountGroupModel;
import com.asset.ccat.air_service.models.requests.GetAccountGroupRequest;
import com.asset.ccat.air_service.models.requests.UpdateAccountGroupRequest;
import com.asset.ccat.air_service.models.responses.BaseResponse;
import com.asset.ccat.air_service.services.AccountGroupService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Defines.ContextPaths.ACCOUNT_GROUP)
public class AccountGroupController {

    @Autowired
    private AccountGroupService accountGroupService;

    @PostMapping(Defines.WEB_ACTIONS.GET)
    public BaseResponse<AccountGroupModel> getCurrentAccountGroup(@RequestBody GetAccountGroupRequest request) throws AIRServiceException, AIRException {
        CCATLogger.DEBUG_LOGGER.info("Received Get Account Group Request [" + request + "]");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        AccountGroupModel currentAccountGroup = accountGroupService.getCurrentAccountGroup(request);
        BaseResponse<AccountGroupModel> response = new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR,
                currentAccountGroup);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Account Group request Successfully!!");

        return response;
    }

    @PostMapping(Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateAccountGroup(@RequestBody UpdateAccountGroupRequest request) throws AIRServiceException, AIRException {
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Account Group Request [" + request + "]");
        accountGroupService.updateCurrentAccountGroup(request);
        BaseResponse<AccountGroupModel> response = new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR,
                null);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Account Group request Successfully!!");

        return response;
    }
}
