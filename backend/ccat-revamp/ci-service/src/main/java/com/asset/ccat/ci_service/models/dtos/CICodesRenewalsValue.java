package com.asset.ccat.ci_service.models.dtos;

public class CICodesRenewalsValue {
    private Integer Id;
    private Integer codeId;
    private Integer renewalsValue;

    public CICodesRenewalsValue() {
    }

    public CICodesRenewalsValue(Integer id, Integer codeId, Integer renewalsValue) {
        Id = id;
        this.codeId = codeId;
        this.renewalsValue = renewalsValue;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getCodeId() {
        return codeId;
    }

    public void setCodeId(Integer codeId) {
        this.codeId = codeId;
    }

    public Integer getRenewalsValue() {
        return renewalsValue;
    }

    public void setRenewalsValue(Integer renewalsValue) {
        this.renewalsValue = renewalsValue;
    }

    @Override
    public String toString() {
        return "CICodesRenewalsValue{" +
                "Id=" + Id +
                ", codeId=" + codeId +
                ", renewalValue=" + renewalsValue +
                '}';
    }
}
