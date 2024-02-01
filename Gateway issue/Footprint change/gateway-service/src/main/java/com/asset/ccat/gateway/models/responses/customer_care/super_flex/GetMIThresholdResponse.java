package com.asset.ccat.gateway.models.responses.customer_care.super_flex;

import com.asset.ccat.gateway.models.customer_care.GetPrepaidProfileResponse;

import java.util.List;

public class GetMIThresholdResponse {

    private String thresholdName;
    private Integer thresholdAmount;
    private List<GetPrepaidProfileResponse.Products.Product> productList;

    public GetMIThresholdResponse() {
    }

    public GetMIThresholdResponse(String thresholdName, Integer thresholdAmount, List<GetPrepaidProfileResponse.Products.Product> productList) {
        this.thresholdName = thresholdName;
        this.thresholdAmount = thresholdAmount;
        this.productList = productList;
    }

    public Integer getThresholdAmount() {
        return thresholdAmount;
    }

    public void setThresholdAmount(Integer thresholdAmount) {
        this.thresholdAmount = thresholdAmount;
    }

    public String getThresholdName() {
        return thresholdName;
    }

    public void setThresholdName(String thresholdName) {
        this.thresholdName = thresholdName;
    }

    public List<GetPrepaidProfileResponse.Products.Product> getProductList() {
        return productList;
    }

    public void setProductList(List<GetPrepaidProfileResponse.Products.Product> productList) {
        this.productList = productList;
    }
}
