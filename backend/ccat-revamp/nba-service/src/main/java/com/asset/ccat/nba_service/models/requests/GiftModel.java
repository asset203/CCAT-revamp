/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.nba_service.models.requests;

/**
 *
 * @author Mahmoud Shehab
 */
public class GiftModel {

    private String giftId;
    private String giftSeqId;
    private String giftDescription;
    private String giftStatus;
    private Boolean isRejectedGift;
    private String timeOfAssignment;
    private String timeOfAssignmentExpiry;
    private String timeOfRdemption;
    private String giftNoDays;
    private String giftShortCode;
    private String giftUnits;
    private String giftFees;
    private String totalIncentive;
    private String totalPendingInsentive;
    private String incentive;
    private String salesScript1;
    private String salesScript3;

    private String wList;

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public String getGiftSeqId() {
        return giftSeqId;
    }

    public void setGiftSeqId(String giftSeqId) {
        this.giftSeqId = giftSeqId;
    }

    public String getGiftDescription() {
        return giftDescription;
    }

    public void setGiftDescription(String giftDescription) {
        this.giftDescription = giftDescription;
    }

    public String getGiftStatus() {
        return giftStatus;
    }

    public void setGiftStatus(String giftStatus) {
        this.giftStatus = giftStatus;
    }

    public String getTimeOfAssignment() {
        return timeOfAssignment;
    }

    public void setTimeOfAssignment(String timeOfAssignment) {
        this.timeOfAssignment = timeOfAssignment;
    }

    public String getTimeOfAssignmentExpiry() {
        return timeOfAssignmentExpiry;
    }

    public void setTimeOfAssignmentExpiry(String timeOfAssignmentExpiry) {
        this.timeOfAssignmentExpiry = timeOfAssignmentExpiry;
    }

    public String getTimeOfRdemption() {
        return timeOfRdemption;
    }

    public void setTimeOfRdemption(String timeOfRdemption) {
        this.timeOfRdemption = timeOfRdemption;
    }

    public String getGiftNoDays() {
        return giftNoDays;
    }

    public void setGiftNoDays(String giftNoDays) {
        this.giftNoDays = giftNoDays;
    }

    public String getGiftShortCode() {
        return giftShortCode;
    }

    public void setGiftShortCode(String giftShortCode) {
        this.giftShortCode = giftShortCode;
    }

    public String getGiftUnits() {
        return giftUnits;
    }

    public void setGiftUnits(String giftUnits) {
        this.giftUnits = giftUnits;
    }

    public String getGiftFees() {
        return giftFees;
    }

    public void setGiftFees(String giftFees) {
        this.giftFees = giftFees;
    }

    public String getTotalIncentive() {
        return totalIncentive;
    }

    public void setTotalIncentive(String totalIncentive) {
        this.totalIncentive = totalIncentive;
    }

    public String getTotalPendingInsentive() {
        return totalPendingInsentive;
    }

    public void setTotalPendingInsentive(String totalPendingInsentive) {
        this.totalPendingInsentive = totalPendingInsentive;
    }

    public String getIncentive() {
        return incentive;
    }

    public void setIncentive(String incentive) {
        this.incentive = incentive;
    }

    public String getSalesScript1() {
        return salesScript1;
    }

    public void setSalesScript1(String salesScript1) {
        this.salesScript1 = salesScript1;
    }

    public String getSalesScript3() {
        return salesScript3;
    }

    public void setSalesScript3(String salesScript3) {
        this.salesScript3 = salesScript3;
    }

    public Boolean getIsRejectedGift() {
        return isRejectedGift;
    }

    public void setIsRejectedGift(Boolean isRejectedGift) {
        this.isRejectedGift = isRejectedGift;
    }

    public String getwList() {
        return wList;
    }

    public void setwList(String wList) {
        this.wList = wList;
    }
}
