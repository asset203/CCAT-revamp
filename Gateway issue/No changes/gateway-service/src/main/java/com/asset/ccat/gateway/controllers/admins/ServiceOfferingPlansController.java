package com.asset.ccat.gateway.controllers.admins;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.service_offering_plans.*;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.service_offering_plans.GetAllServiceOfferingPlanBitsResponse;
import com.asset.ccat.gateway.models.responses.admin.service_offering_plans.GetAllServiceOfferingPlansResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.admin.ServiceOfferingPlansService;
import com.asset.ccat.gateway.validators.admins.ServiceOfferingPlanValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = Defines.ContextPaths.SERVICE_OFFERING_PLANS)
public class ServiceOfferingPlansController {

    @Autowired
    ServiceOfferingPlansService serviceOfferingPlansService;
    @Autowired
    ServiceOfferingPlanValidator serviceOfferingPlanValidator;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllServiceOfferingPlansResponse> getAllServiceOfferingPlans(@RequestBody GetAllServiceOfferingPlansRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Service Offering Plans Request [" + request + "]");
        GetAllServiceOfferingPlansResponse response = serviceOfferingPlansService.getAllServiceOfferingPlans(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Service Offering Plans Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetAllServiceOfferingPlanBitsResponse> getAllServiceOfferingPlanBits(@RequestBody GetServiceOfferingPlanBitsRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Service Offering Plan Bits Request [" + request + "]");
        serviceOfferingPlanValidator.validateGetServiceOfferingPlan(request);
        GetAllServiceOfferingPlanBitsResponse response = serviceOfferingPlansService.getServiceOfferingPlanBits(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Service Offering Plan Bits Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                response);
    }


    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse addServiceOfferingPlan(@RequestBody AddServiceOfferingPlanRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Add Service Offering Plan Request [" + request + "]");
        serviceOfferingPlanValidator.validateAddServiceOfferingPlan(request);
        serviceOfferingPlansService.addServiceOfferingPlan(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Add Service Offering Plan Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }


    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateServiceOfferingPlanBits(@RequestBody UpdateServiceOfferingPlanRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Service Offering Plan Request [" + request + "]");
        serviceOfferingPlanValidator.validateUpdateServiceOfferingPlan(request);
        serviceOfferingPlansService.updateServiceOfferingPlan(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Service Offering Plan Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }


    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deleteServiceOfferingPlan(@RequestBody DeleteServiceOfferingPlanRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Delete Service Offering Plan Request [" + request + "]");
        serviceOfferingPlanValidator.validateDeleteServiceOfferingPlan(request);
        serviceOfferingPlansService.deleteServiceOfferingPlan(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Delete Service Offering Plan Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }


//    @PostMapping(value = Defines.ContextPaths.SO_SC_DESC + Defines.WEB_ACTIONS.GET)
//    public BaseResponse<GetAllServiceClassPlanDescriptionResponse> getAllServiceClassPlanDescription(@RequestBody GetServiceClassServiceOfferingPlanDescs request) throws GatewayException {
//
//        CCATLogger.DEBUG_LOGGER.debug("Started ServiceOfferingPlansController - getAllServiceClassPlanDesc()");
//        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
//        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
//        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
//        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[ " + sessionId + " ]username=[ " + username + " ]");
//        request.setUsername(username);
//        request.setRequestId(UUID.randomUUID().toString());
//        request.setSessionId(sessionId);
//        CCATLogger.DEBUG_LOGGER.info("Received get all service offering service classes plan desc Request");
//        ThreadContext.put("sessionId", sessionId);
//        ThreadContext.put("requestId", request.getRequestId());
//        serviceOfferingPlanValidator.validateGetServiceOfferingServiceClassDesc(request);
////        GetAllServiceClassPlanDescriptionResponse response = serviceOfferingPlansService.getAllServiceClassPlanDesc(request);
//        GetAllServiceClassPlanDescriptionResponse response = null;
//
//
//        CCATLogger.DEBUG_LOGGER.info("Get all service offering service classes plan desc request finished Successfully");
//        CCATLogger.DEBUG_LOGGER.debug("Ending ServiceOfferingPlansController - getAllServiceClassPlanDesc()");
//        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
//                "success",
//                0,
//                request.getRequestId(),
//                response);
//    }


//    @PostMapping(value = Defines.ContextPaths.SO_SC_DESC + Defines.WEB_ACTIONS.UPDATE)
//    public BaseResponse updateServiceOfferingServiceClassDesc(@RequestBody UpdateServiceClassPlanDescriptionRequest request) throws GatewayException {
//
//        CCATLogger.DEBUG_LOGGER.debug("Started ServiceOfferingPlansController - updateServiceOfferingServiceClassDesc()");
//        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
//        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
//        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
//        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[ " + sessionId + " ]username=[ " + username + " ]");
//        request.setUsername(username);
//        request.setRequestId(UUID.randomUUID().toString());
//        request.setSessionId(sessionId);
//        CCATLogger.DEBUG_LOGGER.info("Received update service offering service class desc Request");
//        ThreadContext.put("sessionId", sessionId);
//        ThreadContext.put("requestId", request.getRequestId());
//
//        serviceOfferingPlanValidator.validateUpdateServiceClassPlanDesc(request);
//        serviceOfferingPlansService.updateServiceClassPlanDesc(request);
//
//        CCATLogger.DEBUG_LOGGER.info("Update service offering service class desc  request finished Successfully");
//        CCATLogger.DEBUG_LOGGER.debug("Ending ServiceOfferingPlansController - updateServiceOfferingServiceClassDesc()");
//        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
//                "success",
//                0,
//                request.getRequestId(),
//                new BaseResponse<>());
//    }
//
//    @PostMapping(value = Defines.ContextPaths.SO_SC_DESC + Defines.WEB_ACTIONS.ADD)
//    public BaseResponse addServiceOfferingServiceClassDesc(@RequestBody AddServiceClassPlanDescriptionRequest request) throws GatewayException {
//
//        CCATLogger.DEBUG_LOGGER.debug("Started ServiceOfferingPlansController - addServiceOfferingServiceClassDesc()");
//        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
//        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
//        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
//        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[ " + sessionId + " ]username=[ " + username + " ]");
//        request.setUsername(username);
//        request.setRequestId(UUID.randomUUID().toString());
//        request.setSessionId(sessionId);
//        CCATLogger.DEBUG_LOGGER.info("Received add service offering service class desc Request");
//        ThreadContext.put("sessionId", sessionId);
//        ThreadContext.put("requestId", request.getRequestId());
//        serviceOfferingPlanValidator.validateAddServiceClassPlanDesc(request);
//        serviceOfferingPlansService.addServiceClassPlanDesc(request);
//
//        CCATLogger.DEBUG_LOGGER.info("Add service offering service class desc  request finished Successfully");
//        CCATLogger.DEBUG_LOGGER.debug("Ending ServiceOfferingPlansController - addServiceOfferingServiceClassDesc()");
//        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
//                "success",
//                0,
//                request.getRequestId(),
//                new BaseResponse<>());
//    }
//
//
//    @PostMapping(value = Defines.ContextPaths.SO_SC_DESC + Defines.WEB_ACTIONS.DELETE)
//    public BaseResponse deleteServiceOfferingServiceClassDesc(@RequestBody DeleteServiceClassPlanDescriptionRequest request) throws GatewayException {
//
//        CCATLogger.DEBUG_LOGGER.debug("Started ServiceOfferingPlansController - deleteServiceOfferingServiceClassDesc()");
//        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
//        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
//        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
//        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[ " + sessionId + " ]username=[ " + username + " ]");
//        request.setUsername(username);
//        request.setRequestId(UUID.randomUUID().toString());
//        request.setSessionId(sessionId);
//        CCATLogger.DEBUG_LOGGER.info("Received delete service offering service class desc Request");
//        ThreadContext.put("sessionId", sessionId);
//        ThreadContext.put("requestId", request.getRequestId());
//        serviceOfferingPlanValidator.validateDeleteServiceClassPlanDesc(request);
//        serviceOfferingPlansService.deleteServiceClassPlanDesc(request);
//
//        CCATLogger.DEBUG_LOGGER.info("Delete service offering service class desc  request finished Successfully");
//        CCATLogger.DEBUG_LOGGER.debug("Ending ServiceOfferingPlansController - deleteServiceOfferingServiceClassDesc()");
//        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
//                "success",
//                0,
//                request.getRequestId(),
//                new BaseResponse<>());
//    }

}
