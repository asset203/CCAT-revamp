package com.asset.ccat.ci_service.controllers;

import com.asset.ccat.ci_service.defines.Defines;
import com.asset.ccat.ci_service.defines.ErrorCodes;
import com.asset.ccat.ci_service.exceptions.CIServiceException;
import com.asset.ccat.ci_service.logger.CCATLogger;
import com.asset.ccat.ci_service.models.requests.SubscriberRequest;
import com.asset.ccat.ci_service.models.responses.BaseResponse;
import com.asset.ccat.ci_service.models.responses.GetMainProductResponse;
import com.asset.ccat.ci_service.models.responses.GetPrepaidProfileResponse;
import com.asset.ccat.ci_service.models.shared.ServiceInfo;
import com.asset.ccat.ci_service.services.GetPrepaidProfileService;
import com.asset.ccat.ci_service.services.MainProductService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 *
 * @author Mahmoud Shehab
 */
@RestController
@RequestMapping(value = Defines.ContextPaths.PREPAID_PROFILE)
public class GetPrepaidProfileController {

    @Autowired
    Environment environment;

    @Autowired
    GetPrepaidProfileService getPrepaidProfileService;

    @Autowired
    MainProductService mainProductService;

    @RequestMapping(value = Defines.WEB_ACTIONS.GET, method = RequestMethod.POST)
    public BaseResponse<GetPrepaidProfileResponse> getAllProducts(HttpServletRequest req, @RequestBody SubscriberRequest request) throws AuthenticationException, Exception {
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        GetPrepaidProfileResponse response = getPrepaidProfileService.getPrepaidProfile(request);
        CCATLogger.DEBUG_LOGGER.info("IP => " + InetAddress.getLocalHost().getHostAddress() + environment.getProperty("server.port"));
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0, new ServiceInfo(InetAddress.getLocalHost().getHostAddress(), environment.getProperty("server.port")),
                response);
    }

    @RequestMapping(value = Defines.ContextPaths.MAIN_PRODUCTS + Defines.WEB_ACTIONS.GET, method = RequestMethod.POST)
    public BaseResponse<List<GetMainProductResponse>> getMainProducts(HttpServletRequest req, @RequestBody SubscriberRequest request) throws AuthenticationException, CIServiceException, UnknownHostException {
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());

        CCATLogger.DEBUG_LOGGER.info("Received get main products request for [" + request.getMsisdn() + "]");
        List<GetMainProductResponse> response = mainProductService.getMainProducts(request.getMsisdn());
        CCATLogger.DEBUG_LOGGER.info("Get main products request finished successfully");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0, new ServiceInfo(InetAddress.getLocalHost().getHostAddress(), environment.getProperty("server.port")),
                response);
    }
}
