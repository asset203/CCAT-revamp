package com.asset.ccat.air_service.models;

import java.util.HashMap;

/**
 *
 * @author marwa.elshawarby
 */
public class ServiceOfferingPlan {

    private Integer id;
    private Integer planId;
    private String name;
    private HashMap<Integer, Boolean> servicePlanBits;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Integer, Boolean> getServicePlanBits() {
        return servicePlanBits;
    }

    public void setServicePlanBits(HashMap<Integer, Boolean> servicePlanBits) {
        this.servicePlanBits = servicePlanBits;
    }
}
