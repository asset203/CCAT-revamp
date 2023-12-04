package com.asset.ccat.dynamic_page.cache;

import com.asset.ccat.dynamic_page.models.dynamic_page.DynamicPageModel;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class DynamicPagesCache {

    private HashMap<Integer, DynamicPageModel> dynamicPages;

    public HashMap<Integer, DynamicPageModel> getDynamicPages() {
        return dynamicPages;
    }

    public void setDynamicPages(HashMap<Integer, DynamicPageModel> dynamicPages) {
        this.dynamicPages = dynamicPages;
    }
}
