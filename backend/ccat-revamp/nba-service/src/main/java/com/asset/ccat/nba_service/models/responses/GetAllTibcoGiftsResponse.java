package com.asset.ccat.nba_service.models.responses;

import com.asset.ccat.nba_service.models.responses.tibco.getAllGifts.CustomerMarketingProduct;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GetAllTibcoGiftsResponse {
    @JsonProperty("CustomerMarketingProduct")
    public List<CustomerMarketingProduct> customerMarketingProduct;

    public List<CustomerMarketingProduct> getCustomerMarketingProduct() {
        return customerMarketingProduct;
    }

    public void setCustomerMarketingProduct(List<CustomerMarketingProduct> customerMarketingProduct) {
        this.customerMarketingProduct = customerMarketingProduct;
    }

    @Override
    public String toString() {
        return "GetAllTibcoGiftsResponse{" +
                "customerMarketingProduct=" + customerMarketingProduct +
                '}';
    }
}



