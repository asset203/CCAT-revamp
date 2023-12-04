package com.asset.ccat.lookup_service.models.requests.account_groups;


import com.asset.ccat.lookup_service.models.requests.BaseRequest;

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
}
