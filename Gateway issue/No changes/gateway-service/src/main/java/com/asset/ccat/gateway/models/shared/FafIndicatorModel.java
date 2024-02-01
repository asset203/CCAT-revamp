package com.asset.ccat.gateway.models.shared;

/**
 * @author mohamed.metwaly
 */
public class FafIndicatorModel {
    private Integer indicatorId;
    private String indicatorName;
    private Integer mappedIndicatorId;

    public FafIndicatorModel() {
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

    public Integer getMappedIndicatorId() {
        return mappedIndicatorId;
    }

    public void setMappedIndicatorId(Integer mappedIndicatorId) {
        this.mappedIndicatorId = mappedIndicatorId;
    }
}
