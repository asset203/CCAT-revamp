/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.controllers.admin;

import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.TransactionLink;
import com.asset.ccat.lookup_service.models.requests.pam_admin.DeletePamRequest;
import com.asset.ccat.lookup_service.models.requests.transaction.AddTransactionCodeRequest;
import com.asset.ccat.lookup_service.models.requests.transaction.AddTransactionTypeRequest;
import com.asset.ccat.lookup_service.models.requests.transaction.GetAllTransactionCodeRequest;
import com.asset.ccat.lookup_service.models.requests.transaction.GetAllTransactionLinkRequest;
import com.asset.ccat.lookup_service.models.requests.transaction.GetAllTransactionTypeRequest;
import com.asset.ccat.lookup_service.models.requests.transaction.UpdateTransactionCodeRequest;
import com.asset.ccat.lookup_service.models.requests.transaction.UpdateTransactionLinkRequest;
import com.asset.ccat.lookup_service.models.requests.transaction.UpdateTransactionTypeRequest;
import com.asset.ccat.lookup_service.models.responses.BaseResponse;
import com.asset.ccat.lookup_service.models.responses.transaction.GetAllTransactionCodeResponse;
import com.asset.ccat.lookup_service.models.responses.transaction.GetAllTransactionLinkesResponse;
import com.asset.ccat.lookup_service.models.responses.transaction.GetAllTransactionTypeResponse;
import com.asset.ccat.lookup_service.services.AdmTransactionService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.ThreadContext;
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
@RequestMapping(value = Defines.ContextPaths.TRANSACTION_ADMIN)
public class AdmTransactionController {

    @Autowired
    private AdmTransactionService transactionService;

    @PostMapping(value = Defines.ContextPaths.TYPE + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllTransactionTypeResponse> getAllTransactionTypes(HttpServletRequest req,
            @RequestBody GetAllTransactionTypeRequest request) throws LookupException {
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.debug("Get All Admin Transaction Type request started.");
        GetAllTransactionTypeResponse response = transactionService.getAllTransactionTypes();
        CCATLogger.DEBUG_LOGGER.debug("Get All Admin Transaction Type request ended.");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                response);
    }

    @PostMapping(value = Defines.ContextPaths.CODE + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllTransactionCodeResponse> getAllTransactionCodes(HttpServletRequest req,
            @RequestBody GetAllTransactionCodeRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Started Geting All Transaction Code");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        GetAllTransactionCodeResponse response = transactionService.getAllTransactionCodes();
        CCATLogger.DEBUG_LOGGER.info(" All Transaction Codes are Retrived Succssfully");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                response);
    }

    @PostMapping(value = Defines.ContextPaths.LINK + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllTransactionCodeResponse> getAllTransactionCodesByTypeId(HttpServletRequest req,
            @RequestBody GetAllTransactionLinkRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Started Geting All Transaction Code");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        GetAllTransactionCodeResponse pamAdminstirationResponse = transactionService.getAllTransactionCodes(request.getTypeId());
        CCATLogger.DEBUG_LOGGER.info(" All Transaction Codes are Retrived Succssfully");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                pamAdminstirationResponse);
    }
    
    @PostMapping(value = Defines.ContextPaths.LINK_DESCRIPTION + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllTransactionLinkesResponse> getAllLinkes(HttpServletRequest req,
            @RequestBody GetAllTransactionLinkRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Started Geting All Transaction linkes");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        List<TransactionLink> result = transactionService.getAllTransactionLinks();
        GetAllTransactionLinkesResponse response = new GetAllTransactionLinkesResponse();
        response.setTransactionLinkes(result);
        CCATLogger.DEBUG_LOGGER.info(" All Transaction linkes are Retrived Succssfully");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                response);
    }

    @PostMapping(value = Defines.ContextPaths.TYPE + Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateTransactionType(HttpServletRequest req,
            @RequestBody UpdateTransactionTypeRequest updateRequest) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Recieve Update All Transaction Type request");
        ThreadContext.put("sessionId", updateRequest.getSessionId());
        ThreadContext.put("requestId", updateRequest.getRequestId());
        transactionService.updateTransactionType(updateRequest);
        CCATLogger.DEBUG_LOGGER.info(" The Transaction Type is Updated Succssfully ");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                null);
    }

    @PostMapping(value = Defines.ContextPaths.CODE + Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateTransactionCode(HttpServletRequest req,
            @RequestBody UpdateTransactionCodeRequest updateRequest) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Recieve Update Transaction Code request");
        ThreadContext.put("sessionId", updateRequest.getSessionId());
        ThreadContext.put("requestId", updateRequest.getRequestId());
        transactionService.updateTransactionCode(updateRequest);
        CCATLogger.DEBUG_LOGGER.info(" The Transaction Code is Updated Succssfully ");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                null);
    }

    @PostMapping(value = Defines.ContextPaths.CODE + Defines.WEB_ACTIONS.ADD)
    public BaseResponse addTransactionCode(HttpServletRequest req,
            @RequestBody AddTransactionCodeRequest addRequest) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Recieve Created Transaction Code request");
        ThreadContext.put("sessionId", addRequest.getSessionId());
        ThreadContext.put("requestId", addRequest.getRequestId());
        transactionService.addTransactionCode(addRequest);
        CCATLogger.DEBUG_LOGGER.info("The Pam is Created Succssfully");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                null);

    }

    @PostMapping(value = Defines.ContextPaths.TYPE + Defines.WEB_ACTIONS.ADD)
    public BaseResponse addTransactionType(HttpServletRequest req,
            @RequestBody AddTransactionTypeRequest addRequest) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Recieve Created Transaction Type request");
        ThreadContext.put("sessionId", addRequest.getSessionId());
        ThreadContext.put("requestId", addRequest.getRequestId());
        transactionService.addTransactionType(addRequest);
        CCATLogger.DEBUG_LOGGER.info("The Transaction Type is Created Succssfully");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                null);

    }

    @PostMapping(value = Defines.ContextPaths.CODE + Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deleteTransactionCode(HttpServletRequest req,
            @RequestBody DeletePamRequest deleteRequest) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Recieve Deleted Transaction Code request");
        ThreadContext.put("sessionId", deleteRequest.getSessionId());
        ThreadContext.put("requestId", deleteRequest.getRequestId());
        transactionService.deleteTransactionCode(deleteRequest);
        CCATLogger.DEBUG_LOGGER.info(" The Transaction Code  is Deleted Succssfully");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                null);
    }

    @PostMapping(value = Defines.ContextPaths.TYPE + Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deleteTransactionType(HttpServletRequest req,
            @RequestBody DeletePamRequest deleteRequest) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Recieve Deleted Transaction Type request");
        ThreadContext.put("sessionId", deleteRequest.getSessionId());
        ThreadContext.put("requestId", deleteRequest.getRequestId());
        transactionService.deleteTransactionType(deleteRequest);
        CCATLogger.DEBUG_LOGGER.info(" The Transaction Type  is Deleted Succssfully");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                null);
    }

    @PostMapping(value = Defines.ContextPaths.LINK + Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateTransactionLink(HttpServletRequest req,
            @RequestBody UpdateTransactionLinkRequest updateRequest) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Recieve Update Transaction Link request");
        ThreadContext.put("sessionId", updateRequest.getSessionId());
        ThreadContext.put("requestId", updateRequest.getRequestId());
        transactionService.updateTransactionLink(updateRequest);
        CCATLogger.DEBUG_LOGGER.info(" The Transaction Link is Updated Succssfully ");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                null);
    }

    @PostMapping(value = Defines.ContextPaths.LINK_DESCRIPTION + Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateTransactionLinkDescription(HttpServletRequest req,
            @RequestBody UpdateTransactionLinkRequest updateRequest) throws LookupException {
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                null);
    }
}
