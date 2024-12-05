package com.asset.ccat.lookup_service.constants;

public enum FeatureType {
    CUSTOMER_CARE(1),
    ADMINISTRATOR(2),
    DSS_REPORTS(3);

    final int id;

    FeatureType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "FeatureType{" +
                "id=" + id +
                '}';
    }
}
