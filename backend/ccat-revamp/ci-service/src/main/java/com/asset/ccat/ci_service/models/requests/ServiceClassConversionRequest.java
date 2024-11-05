/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.ci_service.models.requests;

/**
 *
 * @author wael.mohamed
 */
public class ServiceClassConversionRequest extends BaseRequest {

    private Integer id;
    private String msisdn;
    private String ciPackageName;
    private String ciServiceName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getCiPackageName() {
        return ciPackageName;
    }

    public void setCiPackageName(String ciPackageName) {
        this.ciPackageName = ciPackageName;
    }

    public String getCiServiceName() {
        return ciServiceName;
    }

    public void setCiServiceName(String ciServiceName) {
        this.ciServiceName = ciServiceName;
    }

    @Override
    public String toString() {
        return "ServiceClassConversionRequest{" +
                "id=" + id +
                ", msisdn='" + msisdn + '\'' +
                ", ciPackageName='" + ciPackageName + '\'' +
                ", ciServiceName='" + ciServiceName + '\'' +
                '}';
    }
}
