package com.asset.ccat.air_service.defines;

public enum OfferTypes {
    ACCOUNT_OFFER(0), MULTI_USER_IDENTIFICATION_OFFER(1),
    TIMER_OFFER(2), PROVIDER_ACCOUNT_OFFER(3);
    Integer typeId;

    OfferTypes(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getTypeId() {
        return typeId;
    }
}
