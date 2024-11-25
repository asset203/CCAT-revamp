package com.asset.ccat.nba_service.models.requests.tibco.redeemGift;

import java.util.ArrayList;

public class Parts {
    public ArrayList<Channel> channel;
    public ArrayList<CustomerAccount> customerAccount;
    public Specification specification;

    public Parts(ArrayList<Channel> channel, ArrayList<CustomerAccount> customerAccount, Specification specification) {
        this.channel = channel;
        this.customerAccount = customerAccount;
        this.specification = specification;
    }

    public Parts() {
    }

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

    @Override
    public String toString() {
        return "Parts{" +
                "channel=" + channel +
                ", customerAccount=" + customerAccount +
                ", specification=" + specification +
                '}';
    }
}
