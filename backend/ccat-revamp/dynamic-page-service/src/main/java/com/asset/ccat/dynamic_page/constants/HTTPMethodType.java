package com.asset.ccat.dynamic_page.constants;

public enum HTTPMethodType {

    GET(1),
    POST(2);

    public int id;

    private HTTPMethodType(int id) {
        this.id = id;
    }
}
