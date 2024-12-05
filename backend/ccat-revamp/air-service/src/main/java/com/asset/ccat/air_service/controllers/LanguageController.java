package com.asset.ccat.air_service.controllers;

import com.asset.ccat.air_service.defines.Defines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.requests.UpdateLanguageRequest;
import com.asset.ccat.air_service.models.responses.BaseResponse;
import com.asset.ccat.air_service.models.shared.ServiceInfo;
import com.asset.ccat.air_service.services.UpdateLanguageService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.sasl.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * @author wael.mohamed
 */
@RestController
@RequestMapping(Defines.ContextPaths.UPDATE_LANGUAGE)
public class LanguageController {

    @Autowired
    private Environment environment;

    @Autowired
    private UpdateLanguageService updateLanguageService;

    @PostMapping(Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateLanguage(HttpServletRequest req, @RequestBody UpdateLanguageRequest request) throws AuthenticationException, AIRServiceException, AIRException, Exception {
        CCATLogger.DEBUG_LOGGER.info("Received Update Language Request [" + request + "]");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        updateLanguageService.updateLanguage(request);
        CCATLogger.DEBUG_LOGGER.info("IP => " + InetAddress.getLocalHost().getHostAddress() + environment.getProperty("server.port"));
        CCATLogger.DEBUG_LOGGER.debug("updateLanguage Ended successfully.");
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Language Request Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR,
                new ServiceInfo(InetAddress.getLocalHost().getHostAddress(), environment.getProperty("server.port")),
                null);
    }
}
