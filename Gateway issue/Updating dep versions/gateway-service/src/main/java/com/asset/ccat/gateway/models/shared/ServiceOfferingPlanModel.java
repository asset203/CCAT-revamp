package com.asset.ccat.gateway.models.shared;

/**
 *
 * @author marwa.elshawarby
 */
public class ServiceOfferingPlanModel {

    private Integer planId;
    private String name;

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

}
