package com.asset.ccat.gateway.controllers.admins;

import com.asset.ccat.gateway.constants.PamType;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.admin.PamTypesModel;
import com.asset.ccat.gateway.models.requests.admin.pam_admin.AddPamRequest;
import com.asset.ccat.gateway.models.requests.admin.pam_admin.DeletePamRequest;
import com.asset.ccat.gateway.models.requests.admin.pam_admin.GetAllPamsRequest;
import com.asset.ccat.gateway.models.requests.admin.pam_admin.UpdatePamRequest;
import com.asset.ccat.gateway.models.requests.lookup.GetAllPamsTypeLKRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.pam_admin.GetAllPamAdminstirationResponse;
import com.asset.ccat.gateway.models.responses.lookup.GetAllPamsTypeLKResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.admin.PamService;
import com.asset.ccat.gateway.validators.admins.PamAdministrationValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author nour.ihab
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = Defines.ContextPaths.PAM_ADMIN)
public class PamAdministrationController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PamAdministrationValidator pamAdministrationValidator;
    @Autowired
    private PamService pamService;

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllPamAdminstirationResponse> getAllPams(HttpServletRequest req,
                                                                    @RequestBody GetAllPamsRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Pams Request [" + request + "]");
        GetAllPamAdminstirationResponse response = pamService.getAllPam(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Pams Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public ResponseEntity<BaseResponse> updatePam(HttpServletRequest req,
                                                  @RequestBody UpdatePamRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Pam Request [" + request + "]");
        pamAdministrationValidator.validateUpdatePam(request);
        pamService.updatePam(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Pam Request Successfully!!");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null), HttpStatus.OK);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public ResponseEntity<BaseResponse> addPam(HttpServletRequest req,
                                               @RequestBody AddPamRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Add Pam Request [" + request + "]");
        pamAdministrationValidator.validateAddPam(request);
        pamService.addPam(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Add Pam Request Successfully!!");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null), HttpStatus.OK);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public ResponseEntity<BaseResponse> deletePam(HttpServletRequest req,
                                                  @RequestBody DeletePamRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Delete Pam Request [" + request + "]");
        pamAdministrationValidator.validateDeletePam(request);
        pamService.deletePam(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Delete Pam Request Successfully!!");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null), HttpStatus.OK);
    }

    @PostMapping(value = Defines.ContextPaths.PAM_TYPE_LK + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllPamsTypeLKResponse> getAllPamsType(HttpServletRequest req,
                                                                 @RequestBody GetAllPamsTypeLKRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Pams Type LK Request [" + request + "]");
        GetAllPamsTypeLKResponse getAllPamsTypeLKResponse = new GetAllPamsTypeLKResponse();
        List<PamTypesModel> pamTypesModelList = new ArrayList<>();
        for (PamType value : PamType.values()) {
            PamTypesModel pam = new PamTypesModel();
            pam.setType(value.name());
            pam.setTypeId(value.getId());
            pamTypesModelList.add(pam);
        }
        getAllPamsTypeLKResponse.setPamTypes(pamTypesModelList);
        CCATLogger.DEBUG_LOGGER.info("Finished Get All Pams Type LK Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                getAllPamsTypeLKResponse);
    }
}
