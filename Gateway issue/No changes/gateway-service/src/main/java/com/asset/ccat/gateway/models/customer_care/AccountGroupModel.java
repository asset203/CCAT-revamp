package com.asset.ccat.gateway.models.customer_care;

import com.asset.ccat.gateway.models.admin.AccountGroupBitModel;

import java.util.List;

public class AccountGroupModel {
    private Integer id;
    private String name;
    private List<AccountGroupBitModel> bits;


    public AccountGroupModel() {
    }

    public AccountGroupModel(Integer id, String name, List<AccountGroupBitModel> bits) {
        this.id = id;
        this.name = name;
        this.bits = bits;
    }

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

    public List<AccountGroupBitModel> getBits() {
        return bits;
    }

    public void setBits(List<AccountGroupBitModel> bits) {
        this.bits = bits;
    }
}
