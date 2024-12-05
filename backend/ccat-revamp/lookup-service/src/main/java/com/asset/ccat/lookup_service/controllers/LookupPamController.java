/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.controllers;

import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.models.BaseResponse;
import com.asset.ccat.lookup_service.models.LkPamModel;
import com.asset.ccat.lookup_service.services.LkPamClassService;
import com.asset.ccat.lookup_service.services.LkPamPeriodService;
import com.asset.ccat.lookup_service.services.LkPamPriorityService;
import com.asset.ccat.lookup_service.services.LkPamScheduleService;
import com.asset.ccat.lookup_service.services.LkPamService;
import java.util.List;
import java.util.UUID;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Mahmoud Shehab
 */
@RestController
@RequestMapping(value = Defines.ContextPaths.CACHED_LOOKUPS)
public class LookupPamController {

    @Autowired
    LkPamClassService pamClassService;
    @Autowired
    LkPamPeriodService pamPeriodService;
    @Autowired
    LkPamPriorityService pamPriorityService;
    @Autowired
    LkPamScheduleService pamScheduleService;
    @Autowired
    LkPamService pamService;

    @GetMapping(value = Defines.ContextPaths.PAM_CLASS)
    public BaseResponse<List<LkPamModel>> getAllPamClasses(HttpServletRequest req) throws  LookupException {
        String requestId = UUID.randomUUID().toString();
        ThreadContext.put("requestId", requestId);
        List<LkPamModel> response = pamClassService.retrieveCachedPamClasses();
        ThreadContext.remove("requestId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, response);
    }

    @GetMapping(value = Defines.ContextPaths.PAM_PERIOD)
    public BaseResponse<List<LkPamModel>> getAllPamPeriods(HttpServletRequest req) throws  LookupException {
        String requestId = UUID.randomUUID().toString();
        ThreadContext.put("requestId", requestId);
        List<LkPamModel> response = pamPeriodService.retrieveCachedPamPeriods();
        ThreadContext.remove("requestId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, response);
    }

    @GetMapping(value = Defines.ContextPaths.PAM_PRIORITY)
    public BaseResponse<List<LkPamModel>> getAllPamPriorities(HttpServletRequest req) throws  LookupException {
        String requestId = UUID.randomUUID().toString();
        ThreadContext.put("requestId", requestId);
        List<LkPamModel> response = pamPriorityService.retrieveCachedPamPrioritys();
        ThreadContext.remove("requestId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, response);
    }

    @GetMapping(value = Defines.ContextPaths.PAM_SCHEDULE)
    public BaseResponse<List<LkPamModel>> getAllPamSchedules(HttpServletRequest req) throws  LookupException {
        String requestId = UUID.randomUUID().toString();
        ThreadContext.put("requestId", requestId);
        List<LkPamModel> response = pamScheduleService.retrieveCachedPamSchedules();
        ThreadContext.remove("requestId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, response);
    }

    @GetMapping(value = Defines.ContextPaths.PAM_SERVICE)
    public BaseResponse<List<LkPamModel>> getAllPamServices(HttpServletRequest req) throws  LookupException {
        String requestId = UUID.randomUUID().toString();
        ThreadContext.put("requestId", requestId);
        List<LkPamModel> response = pamService.retrieveCachedPamServices();
        ThreadContext.remove("requestId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, response);
    }

}
