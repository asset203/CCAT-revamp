package com.asset.ccat.gateway.constants;

public enum ParametersResponseType {
    JSON(1),
    XML(2),
    XSD(3);

    public final Integer id;

    private ParametersResponseType(int id) {
        this.id = id;
    }
}
