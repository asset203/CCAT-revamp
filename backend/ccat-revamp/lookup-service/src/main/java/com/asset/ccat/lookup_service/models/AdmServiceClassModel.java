/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author wael.mohamed
 */
public class AdmServiceClassModel {

    private String name;
    private Integer code;
    private Boolean preActivationAllowed;
    private Boolean isGrandfather;
    private Boolean isCiConversion;
    private String ciServiceName;
    private String ciPackageName;
    private Boolean isAllowedMigration;
    @JsonIgnore
    private HashMap<Integer, ServiceOfferingPlanDescModel> serviceOfferingPlanDescsMap;
    @JsonIgnore
    private HashMap<Integer, DedicatedAccount> dedAccountsMap;
    @JsonIgnore
    private HashMap<Integer, Accumlator> accumlatorsMap;

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

    public Boolean getIsGrandfather() {
        return isGrandfather;
    }

    public void setIsGrandfather(Boolean isGrandfather) {
        this.isGrandfather = isGrandfather;
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

    public List<DedicatedAccount> getDedAccounts() {
        return new ArrayList<>(dedAccountsMap.values());
    }

    public List<Accumlator> getAccumlators() {
        return new ArrayList<>(accumlatorsMap.values());
    }

    public HashMap<Integer, DedicatedAccount> getDedAccountsMap() {
        return dedAccountsMap;
    }

    public void setDedAccountsMap(HashMap<Integer, DedicatedAccount> dedAccountsMap) {
        this.dedAccountsMap = dedAccountsMap;
    }

    public HashMap<Integer, Accumlator> getAccumlatorsMap() {
        return accumlatorsMap;
    }

    public void setAccumlatorsMap(HashMap<Integer, Accumlator> accumlatorsMap) {
        this.accumlatorsMap = accumlatorsMap;
    }

    public List<ServiceOfferingPlanDescModel> getServiceOfferingPlanDescs() {
        return new ArrayList<>(serviceOfferingPlanDescsMap.values());
    }

    public HashMap<Integer, ServiceOfferingPlanDescModel> getServiceOfferingPlanDescsMap() {
        return serviceOfferingPlanDescsMap;
    }

    public void setServiceOfferingPlanDescsMap(HashMap<Integer, ServiceOfferingPlanDescModel> serviceOfferingPlanDescsMap) {
        this.serviceOfferingPlanDescsMap = serviceOfferingPlanDescsMap;
    }
}
