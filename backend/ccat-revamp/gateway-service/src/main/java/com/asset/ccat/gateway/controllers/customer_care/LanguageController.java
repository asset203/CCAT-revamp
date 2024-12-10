package com.asset.ccat.gateway.controllers.customer_care;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.annotation.SubscriberOwnership;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.customer_care.LanguageModel;
import com.asset.ccat.gateway.models.requests.customer_care.language.GetAllLanguageRequest;
import com.asset.ccat.gateway.models.requests.customer_care.language.UpdateLanguageRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.customer_care.GetAllLanguagesResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.LanguageService;
import com.asset.ccat.gateway.validators.customer_care.LanguageValidation;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author nour.ihab
 */
@RestController
@RequestMapping(path = Defines.ContextPaths.LANGUAGE)
public class LanguageController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private LanguageService languageService;
    @Autowired
    private LanguageValidation languageValidation;


    @CrossOrigin(origins = "*")
    @LogFootprint
    @SubscriberOwnership
    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateLanguage(HttpServletRequest req,
                                       @RequestBody UpdateLanguageRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Language Request [" + request + "]");
        languageValidation.validateUpdateLanguage(request);
        languageService.updateLanguage(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Language Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllLanguagesResponse> getLanguage(HttpServletRequest req,
                                                             @RequestBody GetAllLanguageRequest request) throws AuthenticationException, GatewayException {
        GetAllLanguagesResponse languageResponse = new GetAllLanguagesResponse();
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Language Request [" + request + "]");
        List<LanguageModel> response = languageService.getAllLanguages();
        languageResponse.setLangaugeModel(response);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Language Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                languageResponse);
    }
}
