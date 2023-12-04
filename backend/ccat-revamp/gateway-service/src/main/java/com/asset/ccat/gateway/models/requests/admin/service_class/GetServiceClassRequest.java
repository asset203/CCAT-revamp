package com.asset.ccat.gateway.models.requests.admin.service_class;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author wael.mohamed
 */
public class GetServiceClassRequest extends BaseRequest {

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "GetServiceClassRequest{" +
                "code=" + code +
                '}';
    }
}
