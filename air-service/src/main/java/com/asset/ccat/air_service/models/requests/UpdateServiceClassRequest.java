package com.asset.ccat.air_service.models.requests;

/**
 *
 * @author wael.mohamed
 */
public class UpdateServiceClassRequest extends BaseRequest {

    private String msisdn;
    private Integer newServiceClassId;
    private Integer currentServiceClassId;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Integer getNewServiceClassId() {
        return newServiceClassId;
    }

    public void setNewServiceClassId(Integer newServiceClassId) {
        this.newServiceClassId = newServiceClassId;
    }

    public Integer getCurrentServiceClassId() {
        return currentServiceClassId;
    }

    public void setCurrentServiceClassId(Integer currentServiceClassId) {
        this.currentServiceClassId = currentServiceClassId;
    }

    @Override
    public String toString() {
        return "UpdateServiceClassRequest{" +
                "msisdn='" + msisdn + '\'' +
                ", newServiceClassId=" + newServiceClassId +
                ", currentServiceClassId=" + currentServiceClassId +
                '}';
    }
}
