package com.asset.ccat.air_service.models.shared;

import java.util.List;

public class AccountGroupWithBitsModel {

    private Integer id;
    private String name;
    private List<AccountGroupBitDescModel> bits;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AccountGroupBitDescModel> getBits() {
        return bits;
    }

    public void setBits(List<AccountGroupBitDescModel> bits) {
        this.bits = bits;
    }
}
