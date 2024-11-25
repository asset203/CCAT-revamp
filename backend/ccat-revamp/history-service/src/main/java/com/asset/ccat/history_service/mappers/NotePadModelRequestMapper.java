/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.history_service.mappers;

import com.asset.ccat.history_service.models.NotePadModel;
import com.asset.ccat.history_service.models.requests.notepad.AddNotePadRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author wael.mohamed
 */
@Component
@Qualifier("addNotePadRequest")
public class NotePadModelRequestMapper implements IMapper<NotePadModel, AddNotePadRequest> {

    @Override
    public NotePadModel mapTo(AddNotePadRequest request) {
        NotePadModel notePad = new NotePadModel();
        notePad.setMsisdn(request.getMsisdn());
        notePad.setNotepadEntry(request.getEntry());
        notePad.setUserId(request.getUserId());
        notePad.setUserName(request.getUsername());
        return notePad;
    }

}
