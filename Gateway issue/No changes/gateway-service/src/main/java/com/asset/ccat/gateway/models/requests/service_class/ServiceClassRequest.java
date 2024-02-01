package com.asset.ccat.gateway.models.requests.service_class;

import com.asset.ccat.gateway.models.customer_care.ServiceClassModel;
import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author wael.mohamed
 */
public class ServiceClassRequest extends BaseRequest {

    private String msisdn;
    private Float mainBalance;
    private ServiceClassModel currentServiceClass;
    private ServiceClassModel newServiceClass;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Float getMainBalance() {
        return mainBalance;
    }

    public void setMainBalance(Float mainBalance) {
        this.mainBalance = mainBalance;
    }

    public ServiceClassModel getCurrentServiceClass() {
        return currentServiceClass;
    }

    public void setCurrentServiceClass(ServiceClassModel currentServiceClass) {
        this.currentServiceClass = currentServiceClass;
    }

    public ServiceClassModel getNewServiceClass() {
        return newServiceClass;
    }

    public void setNewServiceClass(ServiceClassModel newServiceClass) {
        this.newServiceClass = newServiceClass;
    }

    @Override
    public String toString() {
        return "ServiceClassRequest{" +
                "msisdn='" + msisdn + '\'' +
                ", mainBalance=" + mainBalance +
                ", currentServiceClass=" + currentServiceClass +
                ", newServiceClass=" + newServiceClass +
                '}';
    }
}
