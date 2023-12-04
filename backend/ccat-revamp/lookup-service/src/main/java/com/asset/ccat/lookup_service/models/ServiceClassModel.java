/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.models;

import java.util.HashMap;

/**
 *
 * @author Mahmoud Shehab
 */
public class ServiceClassModel {

    private String name;
    private Integer code;
    private Boolean preActivationAllowed;
    private Boolean isGrandfather;
    private Boolean isCiConversion;
    private String ciServiceName;
    private String ciPackageName;
    private Boolean isAllowedMigration;
    private HashMap<Integer, DedicatedAccount> dedAccounts;
    private HashMap<Integer, String> accumlators;

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

    public HashMap<Integer, DedicatedAccount> getDedAccounts() {
        return dedAccounts;
    }

    public void setDedAccounts(HashMap<Integer, DedicatedAccount> dedAccounts) {
        this.dedAccounts = dedAccounts;
    }

    public HashMap<Integer, String> getAccumlators() {
        return accumlators;
    }

    public void setAccumlators(HashMap<Integer, String> accumlators) {
        this.accumlators = accumlators;
    }

    public Boolean getIsGrandfather() {
        return isGrandfather;
    }

    public void setIsGrandfather(Boolean isGrandfather) {
        this.isGrandfather = isGrandfather;
    }

}
