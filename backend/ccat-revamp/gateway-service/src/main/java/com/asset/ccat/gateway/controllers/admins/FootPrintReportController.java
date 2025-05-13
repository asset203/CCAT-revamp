package com.asset.ccat.gateway.controllers.admins;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.foot_print.GetFootPrintReportRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.foot_print.GetFootPrintReportResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.admin.FootPrintReportService;
import com.asset.ccat.gateway.validators.admins.FootPrintReportValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author Assem.Hassan
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = Defines.ContextPaths.ADMIN_FOOTPRINT_REPORT)
public class FootPrintReportController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private FootPrintReportService footPrintReportService;
    @Autowired
    private FootPrintReportValidator footPrintReportValidator;

    @PostMapping(value = Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetFootPrintReportResponse> getFootPrintReport(HttpServletRequest req,
                                                                       @RequestBody GetFootPrintReportRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get FootPrint Report Request [" + request + "]");
        footPrintReportValidator.validateGetFootPrintReport(request);
        GetFootPrintReportResponse getFootPrintReportResponse = footPrintReportService.getFootPrintReport(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get FootPrint Report Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                getFootPrintReportResponse);
    }


    @PostMapping(value = Defines.WEB_ACTIONS.EXPORT)
    @LogFootprint
    public ResponseEntity<Resource> exportFootPrintReport(HttpServletRequest req,
                                                          @RequestBody GetFootPrintReportRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Export FootPrint Report Request [" + request + "]");
        InputStreamResource file = new InputStreamResource(footPrintReportService.exportFootPrintReport(request));
        String filename = "FootPrintReport.xlsx";
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Export FootPrint Report Request Successfully!!");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }
}
