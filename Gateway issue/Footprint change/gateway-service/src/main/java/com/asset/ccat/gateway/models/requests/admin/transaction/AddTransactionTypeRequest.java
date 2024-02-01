package com.asset.ccat.gateway.models.requests.admin.transaction;

import com.asset.ccat.gateway.models.requests.BaseRequest;

import java.util.List;

/**
 * @author wael.mohamed
 */
public class AddTransactionTypeRequest extends BaseRequest {

    List<Integer> ccFeatures;
    private String name;
    private String value;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getCcFeatures() {
        return ccFeatures;
    }

    public void setCcFeatures(List<Integer> ccFeatures) {
        this.ccFeatures = ccFeatures;
    }

    @Override
    public String toString() {
        return "AddTransactionTypeRequest{" +
                "ccFeatures=" + ccFeatures +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
