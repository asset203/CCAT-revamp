package com.asset.ccat.dynamic_page.controllers;

import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.DynamicPageModel;
import com.asset.ccat.dynamic_page.models.requests.dynamic_page.*;
import com.asset.ccat.dynamic_page.models.responses.BaseResponse;
import com.asset.ccat.dynamic_page.models.responses.dynamic_page.AddDynamicPageResponse;
import com.asset.ccat.dynamic_page.models.responses.dynamic_page.ViewDynamicPageResponse;
import com.asset.ccat.dynamic_page.security.JwtTokenUtil;
import com.asset.ccat.dynamic_page.services.DynamicPagesService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = Defines.ContextPaths.DYNAMIC_PAGE)
public class DynamicPageController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private DynamicPagesService dynamicPagesService;

    @PostMapping(value = Defines.WEB_ACTIONS.VIEW)
    public BaseResponse<ViewDynamicPageResponse> viewDynamicPage(@RequestBody ViewDynamicPageRequest viewDynamicPageRequest) throws DynamicPageException {

        CCATLogger.DEBUG_LOGGER.debug("Started CustomerDynamicPageController - viewDynamicPage()");
        ThreadContext.put("sessionId", viewDynamicPageRequest.getSessionId());
        ThreadContext.put("requestId", viewDynamicPageRequest.getRequestId());

        CCATLogger.DEBUG_LOGGER.info("Received view Dynamic Page request");
        ViewDynamicPageResponse resp = dynamicPagesService.viewDynamicPage(viewDynamicPageRequest);
        CCATLogger.DEBUG_LOGGER.info("View Dynamic Page request finished successfully");

        CCATLogger.DEBUG_LOGGER.debug("Finished CustomerDynamicPageController - viewDynamicPage()");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                resp);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public ResponseEntity<BaseResponse<AddDynamicPageResponse>> addDynamicPage(
            @RequestBody AddDynamicPageRequest addDynamicPageRequest) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageController - addDynamicPage()");
        ThreadContext.put("sessionId", addDynamicPageRequest.getSessionId());
        ThreadContext.put("requestId", addDynamicPageRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Add Dynamic Page Request");
        AddDynamicPageResponse response = dynamicPagesService.addAdminDynamicPage(addDynamicPageRequest);
        CCATLogger.DEBUG_LOGGER.info("Add Dynamic Page request finished successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPageController - addDynamicPage()");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", 0
                , response), HttpStatus.OK);
    }


    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public ResponseEntity<BaseResponse> updateDynamicPage(
            @RequestBody UpdateDynamicPageRequest updateDynamicPageRequest) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageController - updateDynamicPage()");
        ThreadContext.put("sessionId", updateDynamicPageRequest.getSessionId());
        ThreadContext.put("requestId", updateDynamicPageRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received UpdateDynamicPage Request");
        dynamicPagesService.updateAdminDynamicPage(updateDynamicPageRequest);
        CCATLogger.DEBUG_LOGGER.info("UpdateDynamicPage request finished successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPageController - updateDynamicPage()");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", 0,
                null), HttpStatus.OK);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.GET)
    public BaseResponse<DynamicPageModel> getDynamicPage(
            @RequestBody GetDynamicPageRequest getDynamicPageRequest) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageController - getDynamicPage()");
        ThreadContext.put("sessionId", getDynamicPageRequest.getSessionId());
        ThreadContext.put("requestId", getDynamicPageRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received GetDynamicPage Request");
        DynamicPageModel response = dynamicPagesService.retrieveDynamicPage(getDynamicPageRequest);
        CCATLogger.DEBUG_LOGGER.info("Get dynamic page request finished successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPageController - getDynamicPage()");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", 0,
                response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<List<DynamicPageModel>> getAllPages(
            @RequestBody GetAllDynamicPagesRequest getAllDynamicPagesRequest) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageController - getAllPages()");
        ThreadContext.put("sessionId", getAllDynamicPagesRequest.getSessionId());
        ThreadContext.put("requestId", getAllDynamicPagesRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received get all dynamic pages Request");
        List<DynamicPageModel> response = dynamicPagesService.retrieveAllAdminDynamicPages();
        CCATLogger.DEBUG_LOGGER.info("Get all dynamic pages request finished Successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPageController - getAllPages()");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public ResponseEntity<BaseResponse> deleteDynamicPage(
            @RequestBody DeleteDynamicPageRequest deleteDynamicPageRequest) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageController - deleteDynamicPage()");
        ThreadContext.put("sessionId", deleteDynamicPageRequest.getSessionId());
        ThreadContext.put("requestId", deleteDynamicPageRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received DeleteDynamicPage Request");
        dynamicPagesService.deleteDynamicPage(deleteDynamicPageRequest);
        CCATLogger.DEBUG_LOGGER.info("DeleteDynamicPage request finished successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPageController - deleteDynamicPage()");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", 0,
                null), HttpStatus.OK);
    }


}
