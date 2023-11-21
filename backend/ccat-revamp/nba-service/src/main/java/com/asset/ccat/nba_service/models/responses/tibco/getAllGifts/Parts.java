package com.asset.ccat.nba_service.models.responses.tibco.getAllGifts;

public class Parts {
    Channel channel;
    Price price;
    LineItem lineItem;
    Specification specification;
    ActivationPeriod activationPeriod;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public LineItem getLineItem() {
        return lineItem;
    }

    public void setLineItem(LineItem lineItem) {
        this.lineItem = lineItem;
    }

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public ActivationPeriod getActivationPeriod() {
        return activationPeriod;
    }

    public void setActivationPeriod(ActivationPeriod activationPeriod) {
        this.activationPeriod = activationPeriod;
    }
}
