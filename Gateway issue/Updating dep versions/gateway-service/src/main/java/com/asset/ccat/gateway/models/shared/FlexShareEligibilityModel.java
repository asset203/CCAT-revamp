package com.asset.ccat.gateway.models.shared;

public class FlexShareEligibilityModel {

    private String transferAmount;
    private String fees;
    private String maxEligAmount;
    private String maxEligAmountFees;
    private String expiryDate;

    public String getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(String transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getMaxEligAmount() {
        return maxEligAmount;
    }

    public void setMaxEligAmount(String maxEligAmount) {
        this.maxEligAmount = maxEligAmount;
    }

    public String getMaxEligAmountFees() {
        return maxEligAmountFees;
    }

    public void setMaxEligAmountFees(String maxEligAmountFees) {
        this.maxEligAmountFees = maxEligAmountFees;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "FlexShareEligibilityModel{" +
                "transferAmount='" + transferAmount + '\'' +
                ", fees='" + fees + '\'' +
                ", maxEligAmount='" + maxEligAmount + '\'' +
                ", maxEligAmountFees='" + maxEligAmountFees + '\'' +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
