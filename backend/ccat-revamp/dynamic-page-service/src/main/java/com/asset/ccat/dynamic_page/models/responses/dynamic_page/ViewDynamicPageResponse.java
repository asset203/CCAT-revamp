package com.asset.ccat.dynamic_page.models.responses.dynamic_page;

import com.asset.ccat.dynamic_page.models.dynamic_page.StepViewModel;

import java.util.List;

public class ViewDynamicPageResponse {

    private Integer pageId;
    private String pageName;
    private Boolean requireSearch;
    private List<StepViewModel> steps;

    public ViewDynamicPageResponse() {
    }

    public ViewDynamicPageResponse(Integer pageId, String pageName, Boolean requireSearch, List<StepViewModel> steps) {
        this.pageId = pageId;
        this.pageName = pageName;
        this.requireSearch = requireSearch;
        this.steps = steps;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public Boolean getRequireSearch() {
        return requireSearch;
    }

    public void setRequireSearch(Boolean requireSearch) {
        this.requireSearch = requireSearch;
    }

    public List<StepViewModel> getSteps() {
        return steps;
    }

    public void setSteps(List<StepViewModel> steps) {
        this.steps = steps;
    }
}
