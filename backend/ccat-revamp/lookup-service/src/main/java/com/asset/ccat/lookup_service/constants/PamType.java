/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.constants;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 *
 * @author wael.mohamed
 */
public enum PamType {
    PAM_SERVICE(1),
    PAM_CLASS(2),
    PAM_SCHEDULE(3),
    PAM_PERIOD(4),
    PAM_PRIORITY(5);

    private final Integer id;

    private PamType(Integer id) {
        this.id = id;
    }

    @JsonValue
    public Integer getId() {
        return id;
    }

}
