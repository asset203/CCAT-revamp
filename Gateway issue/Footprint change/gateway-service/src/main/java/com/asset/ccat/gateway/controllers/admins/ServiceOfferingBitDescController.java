package com.asset.ccat.gateway.controllers.admins;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.service_offering_description.GetAllServiceOfferingDescriptionRequest;
import com.asset.ccat.gateway.models.requests.admin.service_offering_description.UpdateServiceOfferingBitDescRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.service_offering_description.GetAllServiceOfferingDescriptionResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.admin.ServiceOfferingDescService;
import com.asset.ccat.gateway.validators.admins.ServiceOfferingDescValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = Defines.ContextPaths.SERVICE_OFFERING_DESCRIPTION)
public class ServiceOfferingBitDescController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private ServiceOfferingDescValidator serviceOfferingDescValidator;
    @Autowired
    private ServiceOfferingDescService serviceOfferingDescService;


    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllServiceOfferingDescriptionResponse> getAllServiceOfferingDescs
            (@RequestBody GetAllServiceOfferingDescriptionRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Service Offering Description Request [" + request + "]");
        GetAllServiceOfferingDescriptionResponse response = this.serviceOfferingDescService.getAllServiceOffering(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Service Offering Description Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                response);
    }


    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public ResponseEntity<BaseResponse> updateServiceOfferingBitDesc(
            @RequestBody UpdateServiceOfferingBitDescRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Service Offering Description Request [" + request + "]");
        serviceOfferingDescValidator.validateUpdateServiceOfferingDesc(request);
        serviceOfferingDescService.updateServiceOfferingDesc(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Service Offering Description Request Successfully!!");


        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null), HttpStatus.OK);
    }
}