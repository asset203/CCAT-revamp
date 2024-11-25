package com.asset.ccat.lookup_service.controllers.admin;


import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.BaseResponse;
import com.asset.ccat.lookup_service.models.ReasonActivityModel;
import com.asset.ccat.lookup_service.models.requests.call_activity_admin.*;
import com.asset.ccat.lookup_service.services.ReasonActivityService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping(value = Defines.ContextPaths.CALL_ACTIVITY_ADMIN)
public class ReasonActivityController {

    @Autowired
    ReasonActivityService reasonActivityService;

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<List<ReasonActivityModel>> getAllActivityReasons(@RequestBody GetAllActivitiesWithTypeRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Started ReasonActivityController - getAllActivityReasons()");
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Received get all call reason activities request :" + request);
        List<ReasonActivityModel> response = reasonActivityService.getAllReasonsWithType(request.getActivityType().name(), request.getParentId());
        CCATLogger.DEBUG_LOGGER.info("Finished get all call reason activities request successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ended ReasonActivityController - getAllActivityReasons()");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR,
                response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse addReasonActivity(@RequestBody AddActivityReasonRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Started ReasonActivityController - addReasonActivity()");
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Received add call reason activity request :" + request);
        reasonActivityService.addReasonActivity(request.getReasonActivity());
        CCATLogger.DEBUG_LOGGER.info("Finished add call reason activity request successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ended ReasonActivityController - addReasonActivity()");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR,
                null);
    }


    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateReasonActivity(@RequestBody UpdateReasonActivityRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Started ReasonActivityController - updateReasonActivity()");
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Received update call reason activity request :" + request);
        reasonActivityService.updateReasonActivity(request.getReasonActivity());
        CCATLogger.DEBUG_LOGGER.info("Finished update call reason activity request successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ended ReasonActivityController - updateReasonActivity()");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR,
                null);
    }


    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deleteReasonActivity(@RequestBody DeleteReasonActivityRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Started ReasonActivityController - deleteReasonActivity()");
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Received delete call reason activity request :" + request);
        reasonActivityService.deleteReasonActivity(request.getActivityId());
        CCATLogger.DEBUG_LOGGER.info("Finished delete call reason activity request successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ended ReasonActivityController - deleteReasonActivity()");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR,
                null);
    }


    @PostMapping(value = Defines.WEB_ACTIONS.DOWNLOAD)
    public ResponseEntity<Resource> downloadActivities(
            @RequestBody DownloadActivitiesRequest request,
            HttpServletResponse response) throws LookupException, IOException {
        CCATLogger.DEBUG_LOGGER.debug("Started ReasonActivityController - downloadActivities()");
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Received download call reason activities request");

        byte[] fileContent = reasonActivityService.exportActivities();
        ByteArrayResource resource = new ByteArrayResource(fileContent);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=activities.xlsx");

        CCATLogger.DEBUG_LOGGER.info("Finished download call reason activities request successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ended ReasonActivityController - downloadActivities()");
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(fileContent.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPLOAD)
    public BaseResponse uploadActivities(
            @ModelAttribute UploadActivitiesFileRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Started ReasonActivityController - uploadActivities()");
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Received upload call reason activities request");
        reasonActivityService.uploadActivities(request);
        CCATLogger.DEBUG_LOGGER.info("Finished upload call reason activities request successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ended ReasonActivityController - uploadActivities()");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR,
                null);


    }


}
