package com.asset.ccat.gateway.controllers.customer_care;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.annotation.SubscriberOwnership;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.customer_care.PaymentProfileModel;
import com.asset.ccat.gateway.models.requests.customer_care.voucher_refill.GetAllPaymentsProfilesRequest;
import com.asset.ccat.gateway.models.requests.customer_care.voucher_refill.SubmitVoucherlessRefillRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.customer_care.GetAllPaymentProfilesResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.VoucherlessRefillService;
import com.asset.ccat.gateway.validators.customer_care.VoucherLessRefillValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author nour.ihab
 */
@RestController
@RequestMapping(path = Defines.ContextPaths.VOUCHERLESS_REFILL)
public class VoucherLessRefillController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private VoucherlessRefillService voucherlessRefillService;
    @Autowired
    private VoucherLessRefillValidator voucherLessRefillValidator;


    @CrossOrigin(origins = "*")
    @SubscriberOwnership
    @PostMapping(value = Defines.ContextPaths.GETALL_PAYMENT_PROFILE)
    public BaseResponse<GetAllPaymentProfilesResponse> getAllPaymentsProfiles(HttpServletRequest req,
                                                                              @RequestBody GetAllPaymentsProfilesRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Payments Profiles Request [" + request + "]");
        List<PaymentProfileModel> paymentProfileModeList = voucherlessRefillService.getAllPaymentsProfiles();
        GetAllPaymentProfilesResponse getAllPaymentProfilesResponse = new GetAllPaymentProfilesResponse();
        getAllPaymentProfilesResponse.setPaymentProfiles(paymentProfileModeList);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Payments Profiles Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                getAllPaymentProfilesResponse);
    }


    @CrossOrigin(origins = "*")
    @LogFootprint
    @SubscriberOwnership
    @PostMapping(value = Defines.WEB_ACTIONS.SUBMIT)
    public BaseResponse submitVoucherLessRefill(HttpServletRequest req,
                                                @RequestBody SubmitVoucherlessRefillRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Submit Voucherless Refill Request [" + request + "]");
        voucherLessRefillValidator.validateSubmitVoucherlessRefill(request);
        voucherlessRefillService.submitVoucherlessRefill(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Submit Voucherless Refill Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }
}
