package com.asset.ccat.gateway.models.customer_care;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 *
 * @author nour.ihab
 */
public class PamInformationModel {

    private String pamServiceID;
    private Integer pamClassID;
    private String pamClassDesc;
    private Integer pamScheduleID;
    private String pamScheduleDesc;
    private String currentPamPeriod;
    private Date deferredToDate;
    private Date lastEvaluationDate;
    private String pamServicePriority;

    public String getPamServiceID() {
        return pamServiceID;
    }

    public void setPamServiceID(String pamServiceID) {
        this.pamServiceID = pamServiceID;
    }

    public Integer getPamClassID() {
        return pamClassID;
    }

    public void setPamClassID(Integer pamClassID) {
        this.pamClassID = pamClassID;
    }

    public String getPamClassDesc() {
        return pamClassDesc;
    }

    public void setPamClassDesc(String pamClassDesc) {
        this.pamClassDesc = pamClassDesc;
    }

    public Integer getPamScheduleID() {
        return pamScheduleID;
    }

    public void setPamScheduleID(Integer pamScheduleID) {
        this.pamScheduleID = pamScheduleID;
    }

    public String getPamScheduleDesc() {
        return pamScheduleDesc;
    }

    public void setPamScheduleDesc(String pamScheduleDesc) {
        this.pamScheduleDesc = pamScheduleDesc;
    }

    public String getCurrentPamPeriod() {
        return currentPamPeriod;
    }

    public void setCurrentPamPeriod(String currentPamPeriod) {
        this.currentPamPeriod = currentPamPeriod;
    }

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    public Date getDeferredToDate() {
        return deferredToDate;
    }

    public void setDeferredToDate(Date deferredToDate) {
        this.deferredToDate = deferredToDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    public Date getLastEvaluationDate() {
        return lastEvaluationDate;
    }

    public void setLastEvaluationDate(Date lastEvaluationDate) {
        this.lastEvaluationDate = lastEvaluationDate;
    }

    public String getPamServicePriority() {
        return pamServicePriority;
    }

    public void setPamServicePriority(String pamServicePriority) {
        this.pamServicePriority = pamServicePriority;
    }

}
