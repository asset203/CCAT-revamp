package com.asset.ccat.gateway.constants;

/**
 *
 * @author marwa.elshawarby
 */
public enum UsageType {

    COUNTER_VALUE(1),
    MONETARY_VALUE(2);

    public int id;

    private UsageType(int id) {
        this.id = id;
    }
}
