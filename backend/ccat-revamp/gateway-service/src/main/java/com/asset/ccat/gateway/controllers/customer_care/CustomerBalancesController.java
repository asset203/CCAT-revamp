package com.asset.ccat.gateway.controllers.customer_care;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.annotation.SubscriberOwnership;
import com.asset.ccat.gateway.cache.LookupsCache;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.customer_care.AccumulatorModel;
import com.asset.ccat.gateway.models.customer_care.DedicatedAccount;
import com.asset.ccat.gateway.models.requests.*;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.CustomerBalancesService;
import com.asset.ccat.gateway.services.FootprintService;
import com.asset.ccat.gateway.services.SubscriberAdminService;
import com.asset.ccat.gateway.util.GatewayUtil;
import com.asset.ccat.gateway.validators.customer_care.CustomerBalanceValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author Mahmoud Shehab
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(Defines.ContextPaths.CUSTOMER_BALANCES)
public class CustomerBalancesController {

    @Autowired
    GatewayUtil gatewayUtil;
    @Autowired
    SubscriberAdminService subscriberService;
    @Autowired
    CustomerBalanceValidator balanceAndDateValidator;
    @Autowired
    CustomerBalancesService customerBalancesService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private FootprintService footprintService;
    @Autowired
    private LookupsCache lookupsCache;

    @SubscriberOwnership
    @RequestMapping(value = Defines.ContextPaths.DEDICATED_ACCOUNTS + Defines.WEB_ACTIONS.GET, method = RequestMethod.POST)
    public BaseResponse<List<DedicatedAccount>> getDedicatedAccounts(HttpServletRequest req,
                                                                     @RequestBody GetDedicatedAccountsRequest request) throws AuthenticationException, Exception {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Dedicated Accounts Request [" + request + "]");
        List<DedicatedAccount> subscriberModel = customerBalancesService.getDedicatedAccounts(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Dedicated Accounts Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                subscriberModel);
    }

    @SubscriberOwnership
    @PostMapping(value = Defines.ContextPaths.ACCUMULATORS + Defines.WEB_ACTIONS.GET)
    public BaseResponse<List<AccumulatorModel>> getAccumulators(HttpServletRequest req,
                                                                @RequestBody SubscriberRequest request) throws AuthenticationException, Exception {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Accumulators Request [{}]", request);
        List<AccumulatorModel> subscriberModel = customerBalancesService.getAccumulators(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Accumulators Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                subscriberModel);
    }


    @SubscriberOwnership
    @LogFootprint
    @RequestMapping(value = Defines.ContextPaths.BALANCE_AND_DATE + Defines.WEB_ACTIONS.UPDATE, method = RequestMethod.POST)
    public BaseResponse updateBalanceAndDates(HttpServletRequest req,
                                              @RequestBody UpdateBalanceAndDateRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        String userId = tokendata.get(Defines.SecurityKeywords.USER_ID).toString();

        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        request.setUserId(Integer.parseInt(userId));
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Balance And Date Request [" + request + "]");
        balanceAndDateValidator.validateUpdateBalanceAndDate(request);
        customerBalancesService.updateBalanceAndDate(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Balance And Date Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }


    @SubscriberOwnership
    @LogFootprint
    @PostMapping(value = Defines.ContextPaths.DEDICATED_ACCOUNTS + Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse<String> updateDedicatedAccounts(HttpServletRequest req,
                                                @RequestBody UpdateDedicatedBalanceRequest request) throws GatewayException {
        HashMap<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokenData.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokenData.get(Defines.SecurityKeywords.USERNAME).toString();
        String userId = tokenData.get(Defines.SecurityKeywords.USER_ID).toString();

        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        request.setUserId(Integer.parseInt(Objects.isNull(userId) ? "0" : userId));
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Dedicated Balance Request [{}]", request);
        balanceAndDateValidator.validateUpdateDedicatedAccounts(request);
        customerBalancesService.updateDedicatedAccounts(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Dedicated Balance Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }

    @SubscriberOwnership
    @LogFootprint
    @PostMapping(value = Defines.ContextPaths.ACCUMULATORS + Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse<List<AccumulatorModel>> updateAccumulators(HttpServletRequest req,
                                                                   @RequestBody UpdateAccumulatorsRequest request) throws Exception {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        String userId = tokendata.get(Defines.SecurityKeywords.USER_ID).toString();

        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        request.setUserId(Integer.parseInt(Objects.isNull(userId) ? "0" : userId));
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Accumulators Request [{}]", request);
        balanceAndDateValidator.validateAccumulators(request);
        customerBalancesService.updateAccumulators(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Accumulators Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);

    }
}
