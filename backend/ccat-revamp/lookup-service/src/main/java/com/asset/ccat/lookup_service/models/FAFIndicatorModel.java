package com.asset.ccat.lookup_service.models;

public class FAFIndicatorModel {
    private Integer id;
    private Integer indicatorId;
    private String indicatorName;
    private Integer isDeleted;
    private Integer mappedIndicatorId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(Integer indicatorId) {
        this.indicatorId = indicatorId;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getMappedIndicatorId() {
        return mappedIndicatorId;
    }

    public void setMappedIndicatorId(Integer mappedIndicatorId) {
        this.mappedIndicatorId = mappedIndicatorId;
    }
}
