package com.asset.ccat.gateway.constants;

public enum HTTPMethodType {

    GET(1),
    POST(2);

    public int id;

    private HTTPMethodType(int id) {
        this.id = id;
    }
}
