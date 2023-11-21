package com.asset.ccat.lookup_service.models;

/**
 * @author mohamed.metwaly
 */
public class AccountGroupModel {
    private Integer accountGroupId;
    private String accountGroupDescription;

    public AccountGroupModel() {
    }


    public Integer getAccountGroupId() {
        return accountGroupId;
    }

    public void setAccountGroupId(Integer accountGroupId) {
        this.accountGroupId = accountGroupId;
    }

    public String getAccountGroupDescription() {
        return accountGroupDescription;
    }

    public void setAccountGroupDescription(String accountGroupDescription) {
        this.accountGroupDescription = accountGroupDescription;
    }

    @Override
    public String toString() {
        return "AccountGroupModel{" +
                "accountGroupId=" + accountGroupId +
                ", accountGroupDescription='" + accountGroupDescription + '\'' +
                '}';
    }
}
