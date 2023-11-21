/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.models;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Mahmoud Shehab
 */
public class PromotionPlanModel implements Serializable {

    private String promotionPlanId;
    private String promotionPlanName;
    private Date startDate;
    private Date endDate;
    private Date oldStartDate;
    private Date oldEndDate;
    private String refillCounter;
    private String refillValue;
    private String progressCounter;
    private String progressValue;

    public String getPromotionPlanId() {
        return promotionPlanId;
    }

    public void setPromotionPlanId(String promotionPlanId) {
        this.promotionPlanId = promotionPlanId;
    }

    public String getPromotionPlanName() {
        return promotionPlanName;
    }

    public void setPromotionPlanName(String promotionPlanName) {
        this.promotionPlanName = promotionPlanName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getOldStartDate() {
        return oldStartDate;
    }

    public void setOldStartDate(Date oldStartDate) {
        this.oldStartDate = oldStartDate;
    }

    public Date getOldEndDate() {
        return oldEndDate;
    }

    public void setOldEndDate(Date oldEndDate) {
        this.oldEndDate = oldEndDate;
    }

    public String getRefillCounter() {
        return refillCounter;
    }

    public void setRefillCounter(String refillCounter) {
        this.refillCounter = refillCounter;
    }

    public String getRefillValue() {
        return refillValue;
    }

    public void setRefillValue(String refillValue) {
        this.refillValue = refillValue;
    }

    public String getProgressCounter() {
        return progressCounter;
    }

    public void setProgressCounter(String progressCounter) {
        this.progressCounter = progressCounter;
    }

    public String getProgressValue() {
        return progressValue;
    }

    public void setProgressValue(String progressValue) {
        this.progressValue = progressValue;
    }

}
