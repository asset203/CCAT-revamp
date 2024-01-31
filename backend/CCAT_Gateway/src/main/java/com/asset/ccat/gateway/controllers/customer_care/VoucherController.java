package com.asset.ccat.gateway.controllers.customer_care;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.customer_care.voucher.*;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.customer_care.CheckVoucherNumberResponse;
import com.asset.ccat.gateway.models.responses.customer_care.GetPaymentGatewayVoucherResponse;
import com.asset.ccat.gateway.models.responses.customer_care.GetVoucherDetailsResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.VoucherService;
import com.asset.ccat.gateway.validators.customer_care.VoucherValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping(Defines.ContextPaths.VOUCHER)
public class VoucherController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private VoucherValidator voucherValidator;
    @Autowired
    private VoucherService voucherService;


    @LogFootprint
    @PostMapping(value = Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetVoucherDetailsResponse> getVoucherDetails(@RequestBody GetVoucherDetailsRequest request) throws GatewayException {
        GetVoucherDetailsResponse voucherDetailsResponse = null;
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setRequestId(UUID.randomUUID().toString());
        request.setUsername(username);
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Voucher Details Request [" + request + "]");
        voucherValidator.validateGetVoucherDetailsRequest(request);
        voucherDetailsResponse = voucherService.getVoucherDetails(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Voucher Details Request Successfully!!");


        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                voucherDetailsResponse);
    }


    @LogFootprint
    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateVoucherState(@RequestBody UpdateVoucherStateRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setRequestId(UUID.randomUUID().toString());
        request.setUsername(username);
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Voucher State Request [" + request + "]");
        voucherValidator.validateUpdateVoucherState(request);
        voucherService.updateVoucherState(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Voucher State Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                request.getRequestId(),
                null);
    }


    @LogFootprint
    @PostMapping(value = Defines.ContextPaths.VOUCHER_BASED_REFILL + Defines.WEB_ACTIONS.SUBMIT)
    public BaseResponse submitVoucherBasedRefill(@RequestBody VoucherBasedRefillRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setRequestId(UUID.randomUUID().toString());
        request.setUsername(username);
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Voucher Based Refill Request [" + request + "]");
        voucherValidator.validateVoucherBasedRefillRequest(request);
        voucherService.submitVoucherBasedRefill(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Voucher Based Refill Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                request.getRequestId(),
                null);
    }


    @LogFootprint
    @PostMapping(value = Defines.WEB_ACTIONS.CHECK)
    public BaseResponse<CheckVoucherNumberResponse> checkVoucherNumber(@RequestBody CheckVoucherNumberRequest request) throws GatewayException {
        CheckVoucherNumberResponse response = null;
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setRequestId(UUID.randomUUID().toString());
        request.setUsername(username);
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Check Voucher Number Request [" + request + "]");
        voucherValidator.validateCheckVoucherNumberRequest(request);
        response = voucherService.checkVoucherNumber(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Check Voucher Number Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                request.getRequestId(),
                response);
    }

    @PostMapping(value = Defines.ContextPaths.PAYMENT_GATEWAY_VOUCHER + Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetPaymentGatewayVoucherResponse> getPaymentGatewayVoucher(@RequestBody GetPaymentGatewayVoucherRequest request) throws GatewayException {
        HashMap<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokenData.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokenData.get(Defines.SecurityKeywords.USERNAME).toString();
        Integer userId = Integer.parseInt(tokenData.get(Defines.SecurityKeywords.USER_ID).toString());
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setSessionId(sessionId);
        request.setRequestId(UUID.randomUUID().toString());
        request.setUserId(userId);
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Payment Gateway Voucher Request [" + request + "]");
        voucherValidator.validateGetPaymentGatewayVoucher(request);
        GetPaymentGatewayVoucherResponse getPaymentGatewayVoucherResponse = voucherService.getPaymentGatewayVoucher(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Payment Gateway Voucher Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                1,
                request.getRequestId(),
                getPaymentGatewayVoucherResponse);
    }
}

