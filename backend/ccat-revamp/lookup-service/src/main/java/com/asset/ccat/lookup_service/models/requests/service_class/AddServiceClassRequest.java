/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.models.requests.service_class;

import com.asset.ccat.lookup_service.models.requests.BaseRequest;
import com.asset.ccat.lookup_service.models.responses.AdmServiceClass;

/**
 *
 * @author wael.mohamed
 */
public class AddServiceClassRequest extends BaseRequest {

    private AdmServiceClass serviceClass;

    public AdmServiceClass getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(AdmServiceClass serviceClass) {
        this.serviceClass = serviceClass;
    }

}
