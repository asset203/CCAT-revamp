package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.models.requests.customer_care.notepad.CreateNotePadRequest;
import com.asset.ccat.gateway.models.requests.customer_care.notepad.DeleteNotePadRequest;
import com.asset.ccat.gateway.models.requests.customer_care.notepad.GetAllNotePadRequest;
import com.asset.ccat.gateway.models.responses.customer_care.GetAllNotePadResponse;
import com.asset.ccat.gateway.proxy.NotePadProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nour.ihab
 */
@Service
public class NotePadService {

    @Autowired
    NotePadProxy notePadProxy;

    public void addNotePad(CreateNotePadRequest createNotePadRequest) throws GatewayException {
        notePadProxy.addNotePad(createNotePadRequest);
    }

    public GetAllNotePadResponse getAllNotePad(GetAllNotePadRequest getAllNotePadRequest) throws GatewayException {
        GetAllNotePadResponse getAllNotePadResponse = notePadProxy.getAllNotePad(getAllNotePadRequest);
        return getAllNotePadResponse;
    }

    public void deleteNotePad(DeleteNotePadRequest deleteNotePadRequest) throws GatewayException {
        notePadProxy.deleteNotePad(deleteNotePadRequest);
    }
}
