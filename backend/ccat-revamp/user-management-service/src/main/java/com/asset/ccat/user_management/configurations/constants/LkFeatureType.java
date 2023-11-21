package com.asset.ccat.user_management.configurations.constants;

/**
 *
 * @author marwa.elshawarby
 */
public enum LkFeatureType {

    CUSTOMER_CARE(1),
    ADMIN(2),
    DSS_REPORTS(3);

    public final int id;

    private LkFeatureType(int id) {
        this.id = id;
    }

}
