package com.asset.ccat.nba_service.models.requests.tibco.sendSMS;

import java.util.ArrayList;

public class Parts {
    public ArrayList<Channel> channel;
    public ArrayList<CustomerAccount> customerAccount;
    public Specification specification;

    public ArrayList<Channel> getChannel() {
        return channel;
    }

    public void setChannel(ArrayList<Channel> channel) {
        this.channel = channel;
    }

    public ArrayList<CustomerAccount> getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(ArrayList<CustomerAccount> customerAccount) {
        this.customerAccount = customerAccount;
    }

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }
}
