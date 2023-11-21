package com.asset.ccat.gateway.constants;

public enum FilterType {

    START_WITH(1),
    CONTAINS(2),
    EQUALS(3);

    public final int id;

    private FilterType(int id) {
        this.id = id;
    }

    public static FilterType get(String id) {
        switch (id) {
            case "1":
                return START_WITH;
            case "2":
                return CONTAINS;
            case "3":
                return EQUALS;
            default:
                return null;
        }
    }
}
