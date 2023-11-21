/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.models.responses;

import java.util.List;

/**
 *
 * @author wael.mohamed
 */
public class GetAllServiceClassResponse {

    private List<AdmServiceClassResponse> serviceClasses;

    public GetAllServiceClassResponse() {
    }

    public GetAllServiceClassResponse(List<AdmServiceClassResponse> serviceClasses) {
        this.serviceClasses = serviceClasses;
    }

    public List<AdmServiceClassResponse> getServiceClasses() {
        return serviceClasses;
    }

    public void setServiceClasses(List<AdmServiceClassResponse> serviceClasses) {
        this.serviceClasses = serviceClasses;
    }

}
