package com.asset.ccat.gateway.constants;

public enum HTTPResponseFormType {

    KEY_VALUE_PAIRS(1),
    MAIN_DELIMITER_VALUES(2),
    END_OF_LINE_DELIMITER_VALUES(3);

    public int id;

    private HTTPResponseFormType(int id) {
        this.id = id;
    }
}
