/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.models.shared;

/**
 *
 * @author Mahmoud Shehab
 */
public class LkFeatureModel {

    private Integer id;
    private String name;
    private String pageName;
    private Integer type;
    private String label;
    private String icon;
    private Integer order;

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

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "LkFeatureModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pageName='" + pageName + '\'' +
                ", type=" + type +
                ", label='" + label + '\'' +
                ", icon='" + icon + '\'' +
                ", order=" + order +
                '}';
    }
}
