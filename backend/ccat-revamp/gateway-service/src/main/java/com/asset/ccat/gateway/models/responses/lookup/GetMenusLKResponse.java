package com.asset.ccat.gateway.models.responses.lookup;

import com.asset.ccat.gateway.models.users.MenuItem;
import java.util.List;

/**
 *
 * @author marwa.elshawarby
 */
public class GetMenusLKResponse {
    private List<MenuItem> menus;

    public GetMenusLKResponse() {
    }

    public GetMenusLKResponse(List<MenuItem> menus) {
        this.menus = menus;
    }

    public List<MenuItem> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuItem> menus) {
        this.menus = menus;
    }
}
