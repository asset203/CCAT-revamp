package com.asset.ccat.gateway.constants;

public enum HTTPContentType {

    JSON(1),
    TEXT(2),
    XML(3);

    public int id;

    private HTTPContentType(int id) {
        this.id = id;
    }
}
