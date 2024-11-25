package com.asset.ccat.gateway.models.requests.admin.air_servers;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author Assem.Hassan
 */
public class DeleteAirServerRequest extends BaseRequest {

    private Integer airServerId;

    public DeleteAirServerRequest() {
    }

    public DeleteAirServerRequest(Integer airServerId) {
        this.airServerId = airServerId;
    }

    public Integer getAirServerId() {
        return airServerId;
    }

    public void setAirServerId(Integer airServerId) {
        this.airServerId = airServerId;
    }

    @Override
    public String toString() {
        return "DeleteAirServerRequest{" +
                "airServerId=" + airServerId +
                '}';
    }
}
