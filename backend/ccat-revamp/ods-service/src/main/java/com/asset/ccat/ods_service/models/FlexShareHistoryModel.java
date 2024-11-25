package com.asset.ccat.ods_service.models;

public class FlexShareHistoryModel {

    private String senderMsisdn;
    private String receieverMsisdn;
    private String flexesAmount;
    private String feesV;
    private String statusV;
    private String flexStartDate;
    private String flexExpiryDate;
    private String channel;

    public String getSenderMsisdn() {
        return senderMsisdn;
    }

    public void setSenderMsisdn(String senderMsisdn) {
        this.senderMsisdn = senderMsisdn;
    }

    public String getReceieverMsisdn() {
        return receieverMsisdn;
    }

    public void setReceieverMsisdn(String receieverMsisdn) {
        this.receieverMsisdn = receieverMsisdn;
    }

    public String getFlexesAmount() {
        return flexesAmount;
    }

    public void setFlexesAmount(String flexesAmount) {
        this.flexesAmount = flexesAmount;
    }

    public String getFeesV() {
        return feesV;
    }

    public void setFeesV(String feesV) {
        this.feesV = feesV;
    }

    public String getStatusV() {
        return statusV;
    }

    public void setStatusV(String statusV) {
        this.statusV = statusV;
    }

    public String getFlexStartDate() {
        return flexStartDate;
    }

    public void setFlexStartDate(String flexStartDate) {
        this.flexStartDate = flexStartDate;
    }

    public String getFlexExpiryDate() {
        return flexExpiryDate;
    }

    public void setFlexExpiryDate(String flexExpiryDate) {
        this.flexExpiryDate = flexExpiryDate;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "FlexShareHistoryModel{" +
                "senderMsisdn='" + senderMsisdn + '\'' +
                ", receieverMsisdn='" + receieverMsisdn + '\'' +
                ", flexesAmount='" + flexesAmount + '\'' +
                ", feesV='" + feesV + '\'' +
                ", statusV='" + statusV + '\'' +
                ", flexStartDate=" + flexStartDate +
                ", flexExpiryDate=" + flexExpiryDate +
                ", channel='" + channel + '\'' +
                '}';
    }
}
