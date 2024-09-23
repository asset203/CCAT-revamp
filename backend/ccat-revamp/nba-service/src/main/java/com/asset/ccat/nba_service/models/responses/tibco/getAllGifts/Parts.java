package com.asset.ccat.nba_service.models.responses.tibco.getAllGifts;

import java.util.List;

public class Parts {
    private List<Channel> channel;
    private List<Price> price;
    private List<LineItem> lineItem;
    private Specification specification;
    private List<ActivationPeriod> activationPeriod;

    public List<Channel> getChannel() {
        return channel;
    }

    public void setChannel(List<Channel> channel) {
        this.channel = channel;
    }

    public List<Price> getPrice() {
        return price;
    }

    public void setPrice(List<Price> price) {
        this.price = price;
    }

    public List<LineItem> getLineItem() {
        return lineItem;
    }

    public void setLineItem(List<LineItem> lineItem) {
        this.lineItem = lineItem;
    }

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public List<ActivationPeriod> getActivationPeriod() {
        return activationPeriod;
    }

    public void setActivationPeriod(List<ActivationPeriod> activationPeriod) {
        this.activationPeriod = activationPeriod;
    }
}
