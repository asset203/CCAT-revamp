/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.controllers;

import com.asset.ccat.air_service.defines.Defines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.requests.customer_care.voucher.VoucherLessRequest;
import com.asset.ccat.air_service.models.responses.BaseResponse;
import com.asset.ccat.air_service.services.VoucherLessRefillService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author wael.mohamed
 */
@RestController
@RequestMapping(value = Defines.ContextPaths.VOUCHERLESS_REFILL)
public class VoucherLessRefillController {

    @Autowired
    private Environment environment;

    @Autowired
    private VoucherLessRefillService voucherLessService;

    @PostMapping(value = Defines.WEB_ACTIONS.SUBMIT)
    public BaseResponse submitVoucherLess(@RequestBody VoucherLessRequest voucherRequest) throws AIRServiceException, AIRException, UnknownHostException {
        CCATLogger.DEBUG_LOGGER.info("Received Submit VoucherLess Request [" + voucherRequest + "]");
        ThreadContext.put("sessionId", voucherRequest.getSessionId());
        ThreadContext.put("requestId", voucherRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("IP => " + InetAddress.getLocalHost().getHostAddress() + environment.getProperty("server.port"));
        voucherLessService.submitVoucherLessRefill(voucherRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Submit VoucherLess Request Successfully");

        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS, "success", Defines.SEVERITY.CLEAR, null);
    }
}
