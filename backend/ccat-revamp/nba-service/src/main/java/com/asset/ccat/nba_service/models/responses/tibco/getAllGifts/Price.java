package com.asset.ccat.nba_service.models.responses.tibco.getAllGifts;

public class Price {
    String name;
    PriceDetail priceDetail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PriceDetail getPriceDetail() {
        return priceDetail;
    }

    public void setPriceDetail(PriceDetail priceDetail) {
        this.priceDetail = priceDetail;
    }
}
