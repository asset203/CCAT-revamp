/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.controllers.admin;

import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.models.BaseResponse;
import com.asset.ccat.lookup_service.models.LkPamModel;
import com.asset.ccat.lookup_service.models.requests.pam_admin.AddPamRequest;
import com.asset.ccat.lookup_service.models.requests.pam_admin.DeletePamRequest;
import com.asset.ccat.lookup_service.models.requests.pam_admin.GetAllPamsRequest;
import com.asset.ccat.lookup_service.models.requests.pam_admin.UpdatePamRequest;
import com.asset.ccat.lookup_service.services.LkPamService;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping(value = Defines.ContextPaths.LOOKUPS + Defines.ContextPaths.PAM_SERVICE)
public class PamServiceController {

    @Autowired
    LkPamService pamService;

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<List<LkPamModel>> getAllPamServices(HttpServletRequest req, @RequestBody GetAllPamsRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        List<LkPamModel> response = pamService.retrievePamServices();
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse addPamService(HttpServletRequest req, @RequestBody AddPamRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        pamService.addPamService(request.getPam().getId(), request.getPam().getDescription());
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updatePamService(HttpServletRequest req, @RequestBody UpdatePamRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        pamService.updatePamService(request.getId(), request.getDescription());
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deletePamService(HttpServletRequest req, @RequestBody DeletePamRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        pamService.deletePamService(request.getId());
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }
}
