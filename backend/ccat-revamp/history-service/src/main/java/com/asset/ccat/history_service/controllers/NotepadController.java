/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.history_service.controllers;

import com.asset.ccat.history_service.defines.Defines;
import com.asset.ccat.history_service.defines.ErrorCodes;
import com.asset.ccat.history_service.exceptions.HistoryException;
import com.asset.ccat.history_service.models.NotePadModel;
import com.asset.ccat.history_service.models.requests.notepad.AddNotePadRequest;
import com.asset.ccat.history_service.models.requests.notepad.NotePadRequest;
import com.asset.ccat.history_service.models.responses.BaseResponse;
import com.asset.ccat.history_service.models.responses.notepad.NotePadResponse;
import com.asset.ccat.history_service.services.NotePadService;
import java.util.List;
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
        NotePadResponse notePad = notePadService.getAllNotePad(notePadRequest.getMsisdn());
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, notePad);

    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse addNotePad(@RequestBody AddNotePadRequest addNotePadRequest) throws HistoryException {
        notePadService.addNotePad(addNotePadRequest);
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deleteNotePad(@RequestBody NotePadRequest notePadRequest) throws HistoryException {
        notePadService.deleteNotePadEntries(notePadRequest.getMsisdn());
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }

}
