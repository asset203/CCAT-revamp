/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.history_service.controllers;

import com.asset.ccat.history_service.defines.Defines;
import com.asset.ccat.history_service.defines.ErrorCodes;
import com.asset.ccat.history_service.exceptions.HistoryException;
import com.asset.ccat.history_service.logger.CCATLogger;
import com.asset.ccat.history_service.models.requests.notepad.AddNotePadRequest;
import com.asset.ccat.history_service.models.requests.notepad.NotePadRequest;
import com.asset.ccat.history_service.models.responses.BaseResponse;
import com.asset.ccat.history_service.models.responses.notepad.NotePadResponse;
import com.asset.ccat.history_service.services.NotePadService;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author wael.mohamed
 */
@RestController
@RequestMapping(value = Defines.ContextPaths.NOTEPAD)
public class NotepadController {

    @Autowired
    private NotePadService notePadService;

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<NotePadResponse> getAllNotePad(@RequestBody NotePadRequest notePadRequest) throws HistoryException {
        ThreadContext.put("requestId", notePadRequest.getRequestId());
        ThreadContext.put("sessionId", notePadRequest.getSessionId());
        CCATLogger.DEBUG_LOGGER.debug("Get-All Notepad request started: {}", notePadRequest);
        NotePadResponse notePad = notePadService.getAllNotePad(notePadRequest.getMsisdn());
        CCATLogger.DEBUG_LOGGER.debug("Get-All Notepad request ended.");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, notePad);

    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse<String> addNotePad(@RequestBody AddNotePadRequest addNotePadRequest) throws HistoryException {
        ThreadContext.put("requestId", addNotePadRequest.getRequestId());
        ThreadContext.put("sessionId", addNotePadRequest.getSessionId());
        CCATLogger.DEBUG_LOGGER.debug("Add Notepad request Started: {}", addNotePadRequest);
        notePadService.addNotePad(addNotePadRequest);
        CCATLogger.DEBUG_LOGGER.debug("Add Notepad request ended.");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse<String> deleteNotePad(@RequestBody NotePadRequest notePadRequest) throws HistoryException {
        ThreadContext.put("requestId", notePadRequest.getRequestId());
        ThreadContext.put("sessionId", notePadRequest.getSessionId());
        CCATLogger.DEBUG_LOGGER.debug("Delete Notepad entries request Started: {}", notePadRequest);
        notePadService.deleteNotePadEntries(notePadRequest.getMsisdn());
        CCATLogger.DEBUG_LOGGER.debug("Delete Notepad entries request ended.");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }

}
