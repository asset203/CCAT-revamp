/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.customer_care;

import java.util.List;

/**
 *
 * @author Mahmoud Shehab
 */
public class SubscriberMainProductModel {

    private Integer productId;
    private String productName;
    private String productStatus;
    private String productType;
    private String productRecurrence;
    private String productStartDate;
    private String productExpiryDate;
    private String productRenewalDate;
    
    private List<ProductQuotaModel> quotas;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductRecurrence() {
        return productRecurrence;
    }

    public void setProductRecurrence(String productRecurrence) {
        this.productRecurrence = productRecurrence;
    }

    public String getProductStartDate() {
        return productStartDate;
    }

    public void setProductStartDate(String productStartDate) {
        this.productStartDate = productStartDate;
    }

    public String getProductExpiryDate() {
        return productExpiryDate;
    }

    public void setProductExpiryDate(String productExpiryDate) {
        this.productExpiryDate = productExpiryDate;
    }

    public String getProductRenewalDate() {
        return productRenewalDate;
    }

    public void setProductRenewalDate(String productRenewalDate) {
        this.productRenewalDate = productRenewalDate;
    }

    public List<ProductQuotaModel> getQuotas() {
        return quotas;
    }

    public void setQuotas(List<ProductQuotaModel> quotas) {
        this.quotas = quotas;
    }
    
    

}
