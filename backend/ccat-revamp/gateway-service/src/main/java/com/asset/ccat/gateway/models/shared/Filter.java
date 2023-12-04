package com.asset.ccat.gateway.models.shared;

import com.asset.ccat.gateway.constants.FilterType;

public class Filter {
    private String field;
    private String value;
    private FilterType filterType;

    public Filter() {
    }

    public Filter(String field, String value, FilterType filterType) {
        this.field = field;
        this.value = value;
        this.filterType = filterType;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }
}
