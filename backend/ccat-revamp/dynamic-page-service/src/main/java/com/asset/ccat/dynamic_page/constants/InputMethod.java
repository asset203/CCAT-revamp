package com.asset.ccat.dynamic_page.constants;

public enum InputMethod {
    FLAT(1),
    MENU(2),
    STATIC(3),
    OUTPUT_FROM_OTHER_STEP(4);

    public final int id;

    private InputMethod(int id) {
        this.id = id;
    }
}
