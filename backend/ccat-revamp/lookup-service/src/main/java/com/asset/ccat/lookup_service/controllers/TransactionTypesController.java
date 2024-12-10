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
import com.asset.ccat.lookup_service.models.LkTransactionCode;
import com.asset.ccat.lookup_service.models.LkTransactionType;
import com.asset.ccat.lookup_service.models.responses.BaseResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Mahmoud Shehab
 */
@RestController
@RequestMapping(value = Defines.ContextPaths.CACHED_LOOKUPS + Defines.ContextPaths.TRANSACTIONS)
public class TransactionTypesController {

    @Autowired
    private CachedLookups cachedLookups;

    @GetMapping(value = Defines.ContextPaths.TYPE + Defines.WEB_ACTIONS.GET)
    public ResponseEntity<BaseResponse<List<LkTransactionType>>> getTransactionType(HttpServletRequest req,
            @RequestParam("featureId") Integer featureId) throws LookupException {
        String requestId = UUID.randomUUID().toString();
        ThreadContext.put("requestId", requestId);
        List<LkTransactionType> list = cachedLookups.getTransactiontypes(featureId);

        if (list == null || list.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.info("No types for feature " + featureId);
            throw new LookupException(ErrorCodes.ERROR.NO_DATA_FOUND);
        }
        ThreadContext.remove("requestId");
        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                list), HttpStatus.OK);
    }

    @GetMapping(value = Defines.ContextPaths.CODE + Defines.WEB_ACTIONS.GET)
    public ResponseEntity<BaseResponse<List<LkTransactionCode>>> getTransactionCodes(HttpServletRequest req,
            @RequestParam("typeId") Integer typeId) throws LookupException {
        String requestId = UUID.randomUUID().toString();
        ThreadContext.put("requestId", requestId);
        List<LkTransactionCode> list = cachedLookups.getTransactionCodes(typeId);

        if (list == null || list.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.info("No codes for type " + typeId);
            throw new LookupException(ErrorCodes.ERROR.NO_DATA_FOUND);
        }
        ThreadContext.remove("requestId");
        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                list), HttpStatus.OK);
    }
}
