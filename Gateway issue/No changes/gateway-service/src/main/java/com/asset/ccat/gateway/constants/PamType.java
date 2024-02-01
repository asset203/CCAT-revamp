/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 *
 * @author wael.mohamed
 */
public enum PamType {
    SERVICE(1),
    CLASS(2),
    SCHEDULE(3),
    PERIOD(4),
    PRIORITY(5);

    private final Integer id;

    private PamType(Integer id) {
        this.id = id;
    }

    @JsonValue
    public Integer getId() {
        return id;
    }

    @JsonCreator
    public static PamType getPamTypeFromId(Integer value) {
        for (PamType dep : PamType.values()) {
            if (dep.getId().equals(value)) {
                return dep;
            }
        }
        return null;
    }

}
