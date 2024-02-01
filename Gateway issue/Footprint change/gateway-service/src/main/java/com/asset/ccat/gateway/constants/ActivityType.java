package com.asset.ccat.gateway.constants;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ActivityType {

    DIRECTION(1,"DIRECTION"),
    FAMILY(2, "FAMILY"),
    TYPE(3, "TYPE"),
    REASON(4, "REASON");


    public int id;
    public String name;



    private ActivityType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ActivityType get(int id) {
        switch (id) {
            case 1:
                return DIRECTION;
            case 2:
                return FAMILY;
            case 3:
                return TYPE;
            case 4:
                return REASON;
            default:
                return null;


        }
    }
    @JsonValue
    public int toValue() {
        return ordinal();
    }
}
