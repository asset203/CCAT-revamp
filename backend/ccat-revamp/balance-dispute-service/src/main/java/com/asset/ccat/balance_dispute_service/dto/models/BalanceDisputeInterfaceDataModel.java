package com.asset.ccat.balance_dispute_service.dto.models;

public class BalanceDisputeInterfaceDataModel {
    private Integer id;
    private String spName;
    private String inputParameters;
    private Integer maxNoParameters;

    public BalanceDisputeInterfaceDataModel() {
    }

    public BalanceDisputeInterfaceDataModel(Integer id, String spName, String inputParameters, Integer maxNoParameters) {
        this.id = id;
        this.spName = spName;
        this.inputParameters = inputParameters;
        this.maxNoParameters = maxNoParameters;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public String getInputParameters() {
        return inputParameters;
    }

    public void setInputParameters(String inputParameters) {
        this.inputParameters = inputParameters;
    }

    public Integer getMaxNoParameters() {
        return maxNoParameters;
    }

    public void setMaxNoParameters(Integer maxNoParameters) {
        this.maxNoParameters = maxNoParameters;
    }
}
