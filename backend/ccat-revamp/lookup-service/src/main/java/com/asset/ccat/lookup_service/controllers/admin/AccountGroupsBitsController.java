package com.asset.ccat.lookup_service.controllers.admin;


import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.BaseResponse;
import com.asset.ccat.lookup_service.models.requests.account_groups_bits_desc.GetAllAccountGroupsBitsDescRequest;
import com.asset.ccat.lookup_service.models.requests.account_groups_bits_desc.UpdateAccountGroupsBitsDescRequest;
import com.asset.ccat.lookup_service.models.responses.account_groups_bits_desc.GetAllAccountGroupsBitsDescResponse;
import com.asset.ccat.lookup_service.services.AccountGroupsBitsService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Defines.ContextPaths.ACCOUNT_GROUPS_BITS_DESCRIPTION)
public class AccountGroupsBitsController {

    @Autowired
    private AccountGroupsBitsService accountGroupsBitsService;

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllAccountGroupsBitsDescResponse> getAllAccountGroupsBits(GetAllAccountGroupsBitsDescRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());

        GetAllAccountGroupsBitsDescResponse response = accountGroupsBitsService.getAllAccountGroupsBits();
        CCATLogger.DEBUG_LOGGER.info("Result is "+ response.getAccountGroupsList());
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR,
                response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateAccountGroupBit(@RequestBody UpdateAccountGroupsBitsDescRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        accountGroupsBitsService.updateAccountGroupBit(request.getAccountGroupBit());
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }
}
