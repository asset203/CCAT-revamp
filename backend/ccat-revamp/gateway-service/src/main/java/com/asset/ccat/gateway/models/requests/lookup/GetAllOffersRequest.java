package com.asset.ccat.gateway.models.requests.lookup;

import com.asset.ccat.gateway.models.requests.BaseRequest;
import com.asset.ccat.gateway.models.shared.PaginationModel;

public class GetAllOffersRequest extends BaseRequest {
    private PaginationModel pagination;

    public PaginationModel getPagination() {
        return pagination;
    }

    public void setPagination(PaginationModel pagination) {
        this.pagination = pagination;
    }

    @Override
    public String toString() {
        return "GetAllOffersRequest{" +
                "pagination=" + pagination +
                '}';
    }
}
