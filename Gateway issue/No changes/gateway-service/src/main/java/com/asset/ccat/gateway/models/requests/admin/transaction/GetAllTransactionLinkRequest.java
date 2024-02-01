package com.asset.ccat.gateway.models.requests.admin.transaction;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author wael.mohamed
 */
public class GetAllTransactionLinkRequest extends BaseRequest {

    private Integer typeId;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "GetAllTransactionLinkRequest{" +
                "typeId=" + typeId +
                '}';
    }
}
