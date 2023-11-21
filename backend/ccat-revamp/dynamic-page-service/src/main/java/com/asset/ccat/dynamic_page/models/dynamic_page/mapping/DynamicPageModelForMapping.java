package com.asset.ccat.dynamic_page.models.dynamic_page.mapping;

import com.asset.ccat.dynamic_page.models.dynamic_page.StepModel;

import java.util.List;

public class DynamicPageModelForMapping {
    private Integer id;
    private String pageName;
    private Integer privilegeId;
    private String privilegeName;
    private Boolean requireSubscriber;
    private List<StepModelForMapping> steps;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public Integer getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Integer privilegeId) {
        this.privilegeId = privilegeId;
    }

    public Boolean getRequireSubscriber() {
        return requireSubscriber;
    }

    public void setRequireSubscriber(Boolean requireSubscriber) {
        this.requireSubscriber = requireSubscriber;
    }

    public List<StepModelForMapping> getSteps() {
        return steps;
    }

    public void setSteps(List<StepModelForMapping> steps) {
        this.steps = steps;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }
}
