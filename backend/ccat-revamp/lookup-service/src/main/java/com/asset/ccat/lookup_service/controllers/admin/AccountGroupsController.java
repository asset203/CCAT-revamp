package com.asset.ccat.lookup_service.controllers.admin;


import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.models.BaseResponse;
import com.asset.ccat.lookup_service.models.requests.account_groups.AddAccountGroupRequest;
import com.asset.ccat.lookup_service.models.requests.account_groups.DeleteAccountGroupRequest;
import com.asset.ccat.lookup_service.models.requests.account_groups.GetAllAccountGroupsRequest;
import com.asset.ccat.lookup_service.models.requests.account_groups.UpdateAccountGroupRequest;
import com.asset.ccat.lookup_service.models.responses.account_groups.GetAllAccountGroupsResponse;
import com.asset.ccat.lookup_service.services.AccountGroupsService;
import com.asset.ccat.lookup_service.validators.AccountGroupsValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Defines.ContextPaths.ACCOUNT_GROUPS)
public class AccountGroupsController {

    @Autowired
    private AccountGroupsService accountGroupsService;

    @Autowired
    private AccountGroupsValidator accountGroupsValidator;

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllAccountGroupsResponse> getAllAccountGroups(GetAllAccountGroupsRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        GetAllAccountGroupsResponse response = accountGroupsService.getAllAccountGroups();
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR,
                response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateAccountGroup(@RequestBody UpdateAccountGroupRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());

        accountGroupsValidator.isAccountGroupUpdateValid(request.getUpdatedAccountGroup());
        accountGroupsService.updateAccountGroup(request.getUpdatedAccountGroup());
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse addAccountGroup(@RequestBody AddAccountGroupRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        accountGroupsValidator.isAccountGroupAddValid(request.getAddedAccountGroup());
        accountGroupsService.addAccountGroup(request.getAddedAccountGroup());
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deleteAccountGroup(@RequestBody DeleteAccountGroupRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        accountGroupsService.deleteAccountGroup(request.getAccountGroupId());
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }
}
