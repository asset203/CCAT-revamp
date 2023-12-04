/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.history_service.controllers;

import com.asset.ccat.history_service.defines.Defines;
import com.asset.ccat.history_service.defines.ErrorCodes;
import com.asset.ccat.history_service.exceptions.HistoryException;
import com.asset.ccat.history_service.models.requests.AddAdjustmentTransactionRequest;
import com.asset.ccat.history_service.models.requests.AdjustmentTransactionCountRequest;
import com.asset.ccat.history_service.models.responses.BaseResponse;
import com.asset.ccat.history_service.services.SubscriberAdjustmentService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author wael.mohamed
 */
@RestController
@RequestMapping(value = Defines.ContextPaths.SUBSCRIBER_ADJUSTMENT)
public class SubscriberAdjustmentController {

    @Autowired
    private SubscriberAdjustmentService adjustmentService;

    @PostMapping(value = Defines.WEB_ACTIONS.CHECK)
    public BaseResponse checkTransactionLimit(HttpServletRequest httpRequest,
            @RequestBody AdjustmentTransactionCountRequest request) throws HistoryException {
        adjustmentService.checkTransactionLimit(request);
        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse addAdjustment(HttpServletRequest httpRequest,
            @RequestBody AddAdjustmentTransactionRequest request) throws HistoryException {
        adjustmentService.insertIntoHistory(request);
        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }
}
