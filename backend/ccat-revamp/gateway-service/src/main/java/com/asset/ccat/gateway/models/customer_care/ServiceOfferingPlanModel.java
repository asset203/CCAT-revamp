package com.asset.ccat.gateway.models.customer_care;

import com.asset.ccat.gateway.models.admin.ServiceOfferingPlanBitModel;

import java.util.List;

public class ServiceOfferingPlanModel {
    private Integer id;
    private String name;
    private List<ServiceOfferingPlanBitModel> bits;

    public ServiceOfferingPlanModel() {
    }

    public ServiceOfferingPlanModel(Integer id, String name, List<ServiceOfferingPlanBitModel> bits) {
        this.id = id;
        this.name = name;
        this.bits = bits;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ServiceOfferingPlanBitModel> getBits() {
        return bits;
    }

    public void setBits(List<ServiceOfferingPlanBitModel> bits) {
        this.bits = bits;
    }

    @Override
    public String toString() {
        return "ServiceOfferingPlanModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bits=" + bits +
                '}';
    }
}
