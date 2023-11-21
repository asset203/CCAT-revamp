/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.models;

import com.asset.ccat.air_service.models.shared.LookupModel;
import java.io.Serializable;

/**
 *
 * @author Mahmoud Shehab
 */
public class ServiceOfferingBitsModel extends LookupModel implements Serializable {

    private String name;

    public ServiceOfferingBitsModel() {
    }

    public ServiceOfferingBitsModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
