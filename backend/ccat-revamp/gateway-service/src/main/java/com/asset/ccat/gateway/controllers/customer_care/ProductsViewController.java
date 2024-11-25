package com.asset.ccat.gateway.controllers.customer_care;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.annotation.SubscriberOwnership;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.customer_care.GetPrepaidProfileResponse;
import com.asset.ccat.gateway.models.requests.SubscriberRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.ProductViewService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Mahmoud Shehab
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = Defines.ContextPaths.PRODUCT)
public class ProductsViewController {

    @Autowired
    ProductViewService subscriberService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @LogFootprint
    @SubscriberOwnership
    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetPrepaidProfileResponse> getProductsView(HttpServletRequest req,
                                                                   @RequestBody SubscriberRequest request) throws AuthenticationException, Exception {
        CCATLogger.DEBUG_LOGGER.info("Received Products View Request");
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();

        request.setRequestId(UUID.randomUUID().toString());
        request.setUsername(username);
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Products View Request [" + request + "]");
        GetPrepaidProfileResponse product = subscriberService.getProducts(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Products View Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                product);
    }
}
