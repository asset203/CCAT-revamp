/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.history_service.mappers;

import com.asset.ccat.history_service.models.NotePadModel;
import com.asset.ccat.history_service.models.responses.notepad.NotePadModelResponse;
import com.asset.ccat.history_service.models.responses.notepad.NotePadResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author wael.mohamed
 */
@Component
@Qualifier("NotePadModelResponse")
public class NotePadModelResponseMapper implements IMapper<NotePadResponse, List<NotePadModel>> {

    @Autowired
    NotePadResponse notePadResponse; 
    @Override
    public NotePadResponse mapTo(List<NotePadModel> models) {
        List<NotePadModelResponse> list = new ArrayList<>();
        NotePadModelResponse notePadModelResonse;
        for (NotePadModel model : models) {
            notePadModelResonse = new NotePadModelResponse();
            notePadModelResonse.setOperator(model.getUserName());
            notePadModelResonse.setNote(model.getNotepadEntry());
            notePadModelResonse.setDate(model.getEntryDate());
            list.add(notePadModelResonse);
        }
        notePadResponse.setEntries(list);
        return notePadResponse;
    }

}
