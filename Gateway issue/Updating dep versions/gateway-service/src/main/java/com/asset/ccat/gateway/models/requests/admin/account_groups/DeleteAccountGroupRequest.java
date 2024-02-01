package com.asset.ccat.gateway.models.requests.admin.account_groups;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author mohamed.metwaly
 */
public class DeleteAccountGroupRequest extends BaseRequest {
    private Integer accountGroupId;

    public DeleteAccountGroupRequest() {
    }

    public Integer getAccountGroupId() {
        return accountGroupId;
    }

    public void setAccountGroupId(Integer accountGroupId) {
        this.accountGroupId = accountGroupId;
    }

    @Override
    public String toString() {
        return "DeleteAccountGroupRequest{" +
                "accountGroupId=" + accountGroupId +
                '}';
    }
}
