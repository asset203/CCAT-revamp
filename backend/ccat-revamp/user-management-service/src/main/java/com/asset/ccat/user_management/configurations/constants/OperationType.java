package com.asset.ccat.user_management.configurations.constants;

/**
 *
 * @author marwa.elshawarby
 */
public enum OperationType {

    ADD_OR_UPDATE("1"),
    DELETE("2");

    public final String id;

    private OperationType(String id) {
        this.id = id;
    }

}
