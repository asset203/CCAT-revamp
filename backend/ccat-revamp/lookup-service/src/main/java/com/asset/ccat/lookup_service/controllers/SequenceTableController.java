/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.controllers;

import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.models.BaseResponse;
import com.asset.ccat.lookup_service.services.SequenceTableService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author wael.mohamed
 */
@RestController
@RequestMapping(value = Defines.SEQUENCE.CONTEXT_PATH)
public class SequenceTableController {

    @Autowired
    private SequenceTableService sequenceService;

    @GetMapping(value = Defines.SEQUENCE.DED_ACCOUNT + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<Map<String, List<Integer>>> getDedAccountSequence() throws LookupException {
        String requestId = UUID.randomUUID().toString();
        ThreadContext.put("requestId", requestId);
        List<Integer> seqList = sequenceService.getDedAccountSequence();
        Map<String, List<Integer>> seq = new HashMap<>();
        seq.put("Ids", seqList);
        ThreadContext.remove("requestId");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, (seq));
    }

    @GetMapping(value = Defines.SEQUENCE.ACCUMULATOR + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<Map<String, List<Integer>>> getAccumulatorSequence() throws LookupException {
        String requestId = UUID.randomUUID().toString();
        ThreadContext.put("requestId", requestId);
        List<Integer> seqList = sequenceService.getAccumulatorSequence();
        Map<String, List<Integer>> seq = new HashMap<>();
        seq.put("Ids", seqList);
        ThreadContext.remove("requestId");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, (seq));
    }

}
