/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.history_service.models.responses.notepad;

import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author wael.mohamed
 */
@Component
public class NotePadResponse {

    List<NotePadModelResponse> entries;

    public NotePadResponse() {

    }

    public NotePadResponse(List<NotePadModelResponse> notes) {
        this.entries = notes;
    }

    public List<NotePadModelResponse> getEntries() {
        return entries;
    }

    public void setEntries(List<NotePadModelResponse> entries) {
        this.entries = entries;
    }

}
