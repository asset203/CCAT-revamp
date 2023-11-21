package com.asset.ccat.dynamic_page.constants;

public enum ParameterDataTypes {

    INT(1),
    STRING(2),
    CURSOR(3),
    DATE(4);

    public final int id;

    private ParameterDataTypes(int id) {
        this.id = id;
    }

    public static ParameterDataTypes getType(int id) {
        switch (id) {
            case 1:
                return INT;
            case 2:
                return STRING;
            case 3:
                return CURSOR;
            case 4:
                return DATE;
            default:
                return null;
        }
    }
}
