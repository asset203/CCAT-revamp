package com.asset.ccat.gateway.models.requests.admin.transaction;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author wael.mohamed
 */
public class DeleteTransactionRequest extends BaseRequest {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DeleteTransactionRequest{" +
                "id=" + id +
                '}';
    }
}
