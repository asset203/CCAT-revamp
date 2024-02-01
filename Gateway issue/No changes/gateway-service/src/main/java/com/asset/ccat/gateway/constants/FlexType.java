package com.asset.ccat.gateway.constants;

public enum FlexType {

    FS_PROFITABLE(1, "Profitable"),
    FS_BLACKLIST(2, "Black List"),
    FS_WHITELIST(3, "White List");

    public int id;
    public String name;

    FlexType(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
