package com.asset.ccat.user_management.models.responses.lookup;

import com.asset.ccat.user_management.models.users.MenuItem;
import java.util.List;

/**
 *
 * @author marwa.elshawarby
 */
public class GetAllMenusResponse {

    List<MenuItem> menus;

    public GetAllMenusResponse() {
    }

    public GetAllMenusResponse(List<MenuItem> menus) {
        this.menus = menus;
    }

    public List<MenuItem> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuItem> menus) {
        this.menus = menus;
    }

}
