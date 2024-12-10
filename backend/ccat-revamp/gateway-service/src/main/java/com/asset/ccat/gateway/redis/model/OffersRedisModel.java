package com.asset.ccat.gateway.redis.model;

import com.asset.ccat.gateway.models.customer_care.LkOfferModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.TimeToLive;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class OffersRedisModel {
    @TimeToLive(unit = TimeUnit.MINUTES)
    private Long timeToLive;

    @Id
    private String msisdn;
    private List<LkOfferModel> lkOffers;

    public List<LkOfferModel> getLkOffers() {
        return lkOffers;
    }

    public void setLkOffers(List<LkOfferModel> lkOffers) {
        this.lkOffers = lkOffers;
    }
}
