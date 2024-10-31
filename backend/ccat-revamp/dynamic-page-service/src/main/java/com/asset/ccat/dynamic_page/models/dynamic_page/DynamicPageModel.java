package com.asset.ccat.dynamic_page.models.dynamic_page;

import java.util.List;

/**
 * @author assem.hassan
 */
public class DynamicPageModel {
    private Integer id;
    private String pageName;
    private Integer privilegeId;
    private String privilegeName;
    private Boolean requireSubscriber;
    private List<StepModel> steps;

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

    public List<StepModel> getSteps() {
        return steps;
    }

    public void setSteps(List<StepModel> steps) {
        this.steps = steps;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    @Override
    public String toString() {
        return "DynamicPageModel{" +
                "id=" + id +
                ", pageName='" + pageName + '\'' +
                ", privilegeId=" + privilegeId +
                ", privilegeName='" + privilegeName + '\'' +
                ", requireSubscriber=" + requireSubscriber +
                ", steps=" + steps +
                '}';
    }
}
