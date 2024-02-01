/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.services.admin;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.models.admin.PamAdminstirationModel;
import com.asset.ccat.gateway.models.requests.admin.pam_admin.AddPamRequest;
import com.asset.ccat.gateway.models.requests.admin.pam_admin.DeletePamRequest;
import com.asset.ccat.gateway.models.requests.admin.pam_admin.GetAllPamsRequest;
import com.asset.ccat.gateway.models.requests.admin.pam_admin.UpdatePamRequest;
import com.asset.ccat.gateway.models.responses.admin.pam_admin.GetAllPamAdminstirationResponse;
import com.asset.ccat.gateway.proxy.admin.PamProxy;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author wael.mohamed
 */
@Service
public class PamService {

    @Autowired
    private PamProxy pamProxy;

    public GetAllPamAdminstirationResponse getAllPam(GetAllPamsRequest request) throws GatewayException {
        List<PamAdminstirationModel> pams = new ArrayList<>();
        List<PamAdminstirationModel> pamServices = pamProxy.getAllPamServices(request);        //1-PamServices
        pamServices.forEach(pamService -> {
            pams.add(pamService);
        });

        List<PamAdminstirationModel> pamClasses = pamProxy.getAllPamClasses(request);          //2-PamClasses
        pamClasses.forEach(pamService -> {
            pams.add(pamService);
        });

        List<PamAdminstirationModel> pamSchedules = pamProxy.getAllPamSchedules(request);      //3-PamSchedules
        pamSchedules.forEach(pamService -> {
            pams.add(pamService);
        });

        List<PamAdminstirationModel> pamPeriods = pamProxy.getAllPamPeriods(request);          //4-PamPeriods
        pamPeriods.forEach(pamService -> {
            pams.add(pamService);
        });

        List<PamAdminstirationModel> pamPriorities = pamProxy.getAllPamPriorites(request);     //5-PamPriorites
        pamPriorities.forEach(pamService -> {
            pams.add(pamService);
        });

        return new GetAllPamAdminstirationResponse(pams);
    }

    public void addPam(AddPamRequest request) throws GatewayException {
        switch (request.getPam().getPamTypeId()) {
            case SERVICE:
                pamProxy.addPamService(request);
                break;
            case CLASS:
                pamProxy.addPamClass(request);
                break;
            case SCHEDULE:
                pamProxy.addPamSchedule(request);
                break;
            case PERIOD:
                pamProxy.addPamPeriod(request);
                break;
            case PRIORITY:
                pamProxy.addPamPriority(request);
                break;
        }
    }

    public void updatePam(UpdatePamRequest request) throws GatewayException {
        switch (request.getPamTypeId()) {
            case SERVICE:
                pamProxy.updatePamService(request);
                break;
            case CLASS:
                pamProxy.updatePamClass(request);
                break;
            case SCHEDULE:
                pamProxy.updatePamSchedule(request);
                break;
            case PERIOD:
                pamProxy.updatePamPeriod(request);
                break;
            case PRIORITY:
                pamProxy.updatePamPriority(request);
                break;
        }
    }

    public void deletePam(DeletePamRequest request) throws GatewayException {
        switch (request.getPamTypeId()) {
            case SERVICE:
                pamProxy.deletePamService(request);
                break;
            case CLASS:
                pamProxy.deletePamClass(request);
                break;
            case SCHEDULE:
                pamProxy.deletePamSchedule(request);
                break;
            case PERIOD:
                pamProxy.deletePamPeriod(request);
                break;
            case PRIORITY:
                pamProxy.deletePamPriority(request);
                break;
        }
    }

}
