package com.asset.ccat.air_service.controllers;

import com.asset.ccat.air_service.defines.Defines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.requests.pam_info.AddPamInformationRequest;
import com.asset.ccat.air_service.models.requests.pam_info.DeletePamInformationRequest;
import com.asset.ccat.air_service.models.requests.pam_info.EvaluatePamInformationRequest;
import com.asset.ccat.air_service.models.responses.BaseResponse;
import com.asset.ccat.air_service.services.PamInformationService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author wael.mohamed
 */
@RestController
@RequestMapping(value = Defines.ContextPaths.PAM_INFORMATION)
public class PamInformationController {

    @Autowired
    Environment environment;
    @Autowired
    private PamInformationService pamInformationService;

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse addPamInfo(@RequestBody AddPamInformationRequest pamRequest) throws AIRServiceException, AIRException, UnknownHostException {
        CCATLogger.DEBUG_LOGGER.info("Received Add PamInfo Request [" + pamRequest + "]");
        ThreadContext.put("sessionId", pamRequest.getSessionId());
        ThreadContext.put("requestId", pamRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("IP => " + InetAddress.getLocalHost().getHostAddress() + environment.getProperty("server.port"));
        pamInformationService.addPamInformation(pamRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Add PamInfo Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public ResponseEntity<BaseResponse> evaluatePamInformation(HttpServletRequest req,
                                                               @RequestBody EvaluatePamInformationRequest evaluatePamRequest) throws UnknownHostException, AIRServiceException, AIRException {
        CCATLogger.DEBUG_LOGGER.info("Received EvaluatePamInfo Request [" + evaluatePamRequest + "]");
        ThreadContext.put("sessionId", evaluatePamRequest.getSessionId());
        ThreadContext.put("requestId", evaluatePamRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("IP => " + InetAddress.getLocalHost().getHostAddress() + environment.getProperty("server.port"));
        pamInformationService.evaluatePamInfo(evaluatePamRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving EvaluatePamInfo Request Successfully!!");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR,
                null), HttpStatus.OK);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public ResponseEntity<BaseResponse> deletePamInformation(HttpServletRequest req,
                                                             @RequestBody DeletePamInformationRequest deletePamRequest) throws UnknownHostException, AIRServiceException, AIRException {
        CCATLogger.DEBUG_LOGGER.info("Received DeletePamInfo Request [" + deletePamRequest + "]");
        ThreadContext.put("sessionId", deletePamRequest.getSessionId());
        ThreadContext.put("requestId", deletePamRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("IP => " + InetAddress.getLocalHost().getHostAddress() + environment.getProperty("server.port"));
        pamInformationService.deletePamInfo(deletePamRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Delete PamInfo Request Successfully!!");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR,
                null), HttpStatus.OK);

    }
}
