package com.asset.ccat.dynamic_page.models.dynamic_page;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * @author assem.hassan
 */
public class ProcedureConfigurationModel extends StepConfigurationModel {

    private Integer id;
    private Integer stepId;
    private String procedureName;
    private String databaseURL;
    private String databaseUsername;
    private String databasePassword;
    private Integer maxRecords;
    private String successCode;
    private String extraConfigurations;
    private List<ProcedureParameterModel> parameters;

    public ProcedureConfigurationModel() {
    }

    public ProcedureConfigurationModel(Integer id, Integer stepId, String procedureName, Integer maxRecords, String successCode) {
        this.id = id;
        this.stepId = stepId;
        this.procedureName = procedureName;
        this.maxRecords = maxRecords;
        this.successCode = successCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStepId() {
        return stepId;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public String getDatabaseURL() {
        return databaseURL;
    }

    public void setDatabaseURL(String databaseURL) {
        this.databaseURL = databaseURL;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public void setDatabaseUsername(String databaseUsername) {
        this.databaseUsername = databaseUsername;
    }
    
    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public Integer getMaxRecords() {
        return maxRecords;
    }

    public void setMaxRecords(Integer maxRecords) {
        this.maxRecords = maxRecords;
    }

    public String getSuccessCode() {
        return successCode;
    }

    public void setSuccessCode(String successCode) {
        this.successCode = successCode;
    }

    public List<ProcedureParameterModel> getParameters() {
        return parameters;
    }

    public void setParameters(List<ProcedureParameterModel> parameters) {
        this.parameters = parameters;
    }

    public String getExtraConfigurations() {
        return extraConfigurations;
    }

    public void setExtraConfigurations(String extraConfigurations) {
        this.extraConfigurations = extraConfigurations;
    }

    @Override
    public String toString() {
        return "ProcedureConfigurationModel{" +
                "id=" + id +
                ", stepId=" + stepId +
                ", procedureName='" + procedureName + '\'' +
                ", databaseURL='" + databaseURL + '\'' +
                ", databaseUsername='" + databaseUsername + '\'' +
                ", databasePassword='" + databasePassword + '\'' +
                ", maxRecords=" + maxRecords +
                ", successCode=" + successCode +
                ", extraConfigurations='" + extraConfigurations + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
