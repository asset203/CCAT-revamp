/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.admin;

import java.util.List;

/**
 * @author wael.mohamed
 */
public class ServiceClassModel {

    private String name;
    private Integer code;
    private Boolean preActivationAllowed;
    private Boolean isCiConversion;
    private Boolean isGrandfather;
    private String ciServiceName;
    private String ciPackageName;
    private Boolean isAllowedMigration;
    private List<DedAccount> dedAccounts;
    private List<Accumlator> accumlators;
    private List<ServiceOfferingPlanDescModel> serviceOfferingPlanDescs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getPreActivationAllowed() {
        return preActivationAllowed;
    }

    public void setPreActivationAllowed(Boolean preActivationAllowed) {
        this.preActivationAllowed = preActivationAllowed;
    }

    public Boolean getIsCiConversion() {
        return isCiConversion;
    }

    public void setIsCiConversion(Boolean isCiConversion) {
        this.isCiConversion = isCiConversion;
    }

    public String getCiServiceName() {
        return ciServiceName;
    }

    public void setCiServiceName(String ciServiceName) {
        this.ciServiceName = ciServiceName;
    }

    public String getCiPackageName() {
        return ciPackageName;
    }

    public void setCiPackageName(String ciPackageName) {
        this.ciPackageName = ciPackageName;
    }

    public Boolean getIsAllowedMigration() {
        return isAllowedMigration;
    }

    public void setIsAllowedMigration(Boolean isAllowedMigration) {
        this.isAllowedMigration = isAllowedMigration;
    }

    public List<DedAccount> getDedAccounts() {
        return dedAccounts;
    }

    public void setDedAccounts(List<DedAccount> dedAccounts) {
        this.dedAccounts = dedAccounts;
    }

    public List<Accumlator> getAccumlators() {
        return accumlators;
    }

    public void setAccumlators(List<Accumlator> accumlators) {
        this.accumlators = accumlators;
    }

    public Boolean getIsGrandfather() {
        return isGrandfather;
    }

    public void setIsGrandfather(Boolean isGrandfather) {
        this.isGrandfather = isGrandfather;
    }

    public List<ServiceOfferingPlanDescModel> getServiceOfferingPlanDescs() {
        return serviceOfferingPlanDescs;
    }

    public void setServiceOfferingPlanDescs(List<ServiceOfferingPlanDescModel> serviceOfferingPlanDescs) {
        this.serviceOfferingPlanDescs = serviceOfferingPlanDescs;
    }

    @Override
    public String toString() {
        return "ServiceClassModel{" +
                "name='" + name + '\'' +
                ", code=" + code +
                ", preActivationAllowed=" + preActivationAllowed +
                ", isCiConversion=" + isCiConversion +
                ", isGrandfather=" + isGrandfather +
                ", ciServiceName='" + ciServiceName + '\'' +
                ", ciPackageName='" + ciPackageName + '\'' +
                ", isAllowedMigration=" + isAllowedMigration +
                ", dedAccounts=" + dedAccounts +
                ", accumlators=" + accumlators +
                ", serviceOfferingPlanDescs=" + serviceOfferingPlanDescs +
                '}';
    }
}
