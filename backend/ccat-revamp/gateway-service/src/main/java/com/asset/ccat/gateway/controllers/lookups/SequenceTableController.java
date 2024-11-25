package com.asset.ccat.gateway.controllers.lookups;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.shared.BaseRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.SequenceTableService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author wael.mohamed
 */
@RestController
@CrossOrigin("*")
@RequestMapping(value = Defines.SEQUENCE.CONTEXT_PATH)
public class SequenceTableController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private SequenceTableService lookupSequenceService;

    @PostMapping(value = Defines.SEQUENCE.DED_ACCOUNT)
    public BaseResponse<Map<String, List<Integer>>> getDedAccountSeq(HttpServletRequest httpReq,
                                                                     @RequestBody BaseRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get DedAccount Sequence Request [" + request + "]");
        Map<String, List<Integer>> seq = lookupSequenceService.getDedAccountSequence();
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get DedAccount Sequence Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                seq);
    }

    @PostMapping(value = Defines.SEQUENCE.ACCUMULATOR)
    public BaseResponse<Map<String, List<Integer>>> getAccumulatorSeq(HttpServletRequest httpReq,
                                                                      @RequestBody BaseRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Accumulator Sequence Request [" + request + "]");
        Map<String, List<Integer>> seq = lookupSequenceService.getAccumulatorSequence();
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Accumulator Sequence Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                seq);
    }
}
