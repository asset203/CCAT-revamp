package com.asset.ccat.dynamic_page.constants;

public enum StepTypes {

    PROCEDURE(1),
    HTTP(2);

    public final int type;

    private StepTypes(int type) {
        this.type = type;
    }

    public static StepTypes getStepType(int id) {
        switch (id) {
            case 1:
                return PROCEDURE;
            case 2:
                return HTTP;
            default:
                return null;
        }
    }
}
