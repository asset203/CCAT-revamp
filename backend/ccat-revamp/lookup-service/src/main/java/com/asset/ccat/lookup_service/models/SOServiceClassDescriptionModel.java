package com.asset.ccat.lookup_service.models;

public class SOServiceClassDescriptionModel {


    private Integer serviceClassId;

    private String description;


    public SOServiceClassDescriptionModel() {
    }

    public SOServiceClassDescriptionModel(Integer serviceClassId, String desc) {
        this.serviceClassId = serviceClassId;
        description = desc;
    }

    public Integer getServiceClassId() {
        return serviceClassId;
    }

    public void setServiceClassId(Integer serviceClassId) {
        this.serviceClassId = serviceClassId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
