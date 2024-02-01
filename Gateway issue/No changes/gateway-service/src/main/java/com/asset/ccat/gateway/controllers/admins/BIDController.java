package com.asset.ccat.gateway.controllers.admins;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.batch_install_disconnect.BatchDisconnectRequest;
import com.asset.ccat.gateway.models.requests.admin.batch_install_disconnect.BatchInstallRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.batch_install_disconnect.BatchDisconnectResponse;
import com.asset.ccat.gateway.models.responses.admin.batch_install_disconnect.BatchInstallResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.admin.BIDService;
import com.asset.ccat.gateway.validators.customer_care.BIDValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author marwa.elshawarby
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = Defines.ContextPaths.BATCH_INSTALLATION_DISCONNECTION)
public class BIDController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private BIDService bidService;
    @Autowired
    private BIDValidator bIDValidator;


    @LogFootprint
    @RequestMapping(value = Defines.WEB_ACTIONS.INSTALL, method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<BatchInstallResponse> batchInstall(@ModelAttribute BatchInstallRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Batch Install Request [" + request + "]");
        bIDValidator.validateBatchInstallRequest(request);
        BatchInstallResponse response = bidService.batchInstall(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Batch Install Request Successfully!!");

        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                0,
                request.getRequestId(),
                response);
    }


    @LogFootprint
    @RequestMapping(value = Defines.WEB_ACTIONS.DISCONNECT, method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<BatchDisconnectResponse> batchDisconnect(@ModelAttribute BatchDisconnectRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Batch Disconnect Request [" + request + "]");
        bIDValidator.validateBatchDisconnectRequest(request);
        BatchDisconnectResponse response = bidService.batchDisconnect(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Batch Disconnect Request Successfully!!");

        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                0,
                request.getRequestId(),
                response);
    }
}
