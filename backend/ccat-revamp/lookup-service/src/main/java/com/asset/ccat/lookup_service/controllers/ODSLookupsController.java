/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.controllers;

import com.asset.ccat.lookup_service.cache.CachedLookups;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.ods_models.ODSActivityDetailsMapping;
import com.asset.ccat.lookup_service.models.ods_models.ODSActivityHeader;
import com.asset.ccat.lookup_service.models.ods_models.ODSActivityHeaderMapping;
import com.asset.ccat.lookup_service.models.ods_models.ODSActivityModel;
import com.asset.ccat.lookup_service.models.responses.BaseResponse;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import javax.security.sasl.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Mahmoud Shehab
 */
@RestController
@RequestMapping(value = Defines.ContextPaths.CACHED_LOOKUPS + Defines.ContextPaths.ODS)
public class ODSLookupsController {

    @Autowired
    private CachedLookups cachedLookups;

    @GetMapping(value = Defines.ContextPaths.ACTIVITIES + Defines.WEB_ACTIONS.GET_ALL)
    public ResponseEntity<BaseResponse<HashMap<String, ODSActivityModel>>> getOdsActivities(HttpServletRequest req) throws AuthenticationException, Exception {
        String requestId = UUID.randomUUID().toString();
        ThreadContext.put("requestId", requestId);
        HashMap<String, ODSActivityModel> map = cachedLookups.getOdsActivites();
        if (map == null || map.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.info("No ods activities configured " + map);
            throw new LookupException(ErrorCodes.ERROR.NO_DATA_FOUND);
        }
        ThreadContext.remove("requestId");
        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                map), HttpStatus.OK);
    }

    @GetMapping(value = Defines.ContextPaths.ACTIVITIES_HEADERS + Defines.WEB_ACTIONS.GET_ALL)
    public ResponseEntity<BaseResponse<HashMap<Integer, ODSActivityHeader>>> getOdsActivitiesHeaders(HttpServletRequest req) throws AuthenticationException, Exception {
        String requestId = UUID.randomUUID().toString();
        ThreadContext.put("requestId", requestId);
        HashMap<Integer, ODSActivityHeader> map = cachedLookups.getOdsActivityHeaders();
        if (map == null || map.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.info("No ods activities headers configured " + map);
            throw new LookupException(ErrorCodes.ERROR.NO_DATA_FOUND);
        }
        ThreadContext.remove("requestId");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                map), HttpStatus.OK);
    }

    @GetMapping(value = Defines.ContextPaths.ACTIVITIES_HEADERS_MAPPING + Defines.WEB_ACTIONS.GET_ALL)
    public ResponseEntity<BaseResponse<HashMap<Integer, HashMap<Integer, ODSActivityHeaderMapping>>>> getOdsHeaderMapping(HttpServletRequest req) throws AuthenticationException, Exception {
        String requestId = UUID.randomUUID().toString();
        ThreadContext.put("requestId", requestId);
        HashMap<Integer, HashMap<Integer, ODSActivityHeaderMapping>> map = cachedLookups.getOdsHeadersMapping();
        if (map == null || map.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.info("No ods activities headers mapping configured " + map);
            throw new LookupException(ErrorCodes.ERROR.NO_DATA_FOUND);
        }
        ThreadContext.remove("requestId");
        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                map), HttpStatus.OK);
    }

    @GetMapping(value = Defines.ContextPaths.ACTIVITIES_DETAILS_MAPPING + Defines.WEB_ACTIONS.GET_ALL)
    public ResponseEntity<BaseResponse<HashMap<Integer, List<ODSActivityDetailsMapping>>>> getOdsDetailsMapping(HttpServletRequest req) throws AuthenticationException, Exception {
        String requestId = UUID.randomUUID().toString();
        ThreadContext.put("requestId", requestId);
        HashMap<Integer, List<ODSActivityDetailsMapping>> map = cachedLookups.getOdsDetailsMapping();
        if (map == null || map.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.info("No ods activities details mapping configured " + map);
            throw new LookupException(ErrorCodes.ERROR.NO_DATA_FOUND);
        }
        ThreadContext.remove("requestId");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                map), HttpStatus.OK);
    }
}
