package com.asset.ccat.user_management.database.extractors;

import com.asset.ccat.user_management.configurations.constants.LkFeatureType;
import com.asset.ccat.user_management.defines.DatabaseStructs;
import com.asset.ccat.user_management.models.users.MenuItem;
import com.asset.ccat.user_management.models.users.MenuModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

/**
 *
 * @author marwa.elshawarby
 */
@Component
public class ProfileMenusExtractor implements ResultSetExtractor<HashMap<Integer, List<MenuModel>>> {

    @Override
    public HashMap<Integer, List<MenuModel>> extractData(ResultSet rs) throws SQLException, DataAccessException {

        HashMap<Integer, List<MenuModel>> profileMenusMap = new HashMap<>();
        LinkedHashMap<Integer, MenuModel> menus = new LinkedHashMap<>();
        menus.put(LkFeatureType.CUSTOMER_CARE.id, new MenuModel(new ArrayList<>())); // customer care menus
        menus.put(LkFeatureType.ADMIN.id, new MenuModel(new ArrayList<>()));// admin menus
        menus.put(LkFeatureType.DSS_REPORTS.id, new MenuModel(new ArrayList<>()));// admin menus
        int previousProfileId = 0;
        int currentProfileId;
        Integer parentId;
        Integer menuId;
        Integer type;
        String pageName;
        String menuLabel;
        String itemRoute;
        String menuIcon;
        MenuModel menu;
        while (rs.next()) {
            currentProfileId = rs.getInt(DatabaseStructs.ADM_PROFILE_FEATURES.PROFILE_ID);

            if (previousProfileId != 0 && previousProfileId != currentProfileId) {
                if (menus.values() != null) {
                    profileMenusMap.put(previousProfileId, new ArrayList<>(menus.values()));
                }
                menus = new LinkedHashMap<>();
                menus.put(LkFeatureType.CUSTOMER_CARE.id, new MenuModel(new ArrayList<>())); // customer care menus
                menus.put(LkFeatureType.ADMIN.id, new MenuModel(new ArrayList<>()));// admin menus
                menus.put(LkFeatureType.DSS_REPORTS.id, new MenuModel(new ArrayList<>()));// admin menus
            }

//            parentId = rs.getInt(DatabaseStructs.LK_MENUS.PARENT_ID);
            menuId = rs.getInt(DatabaseStructs.LK_FEATURES.ID);
            type = rs.getInt(DatabaseStructs.LK_FEATURES.TYPE);
            pageName = rs.getString(DatabaseStructs.LK_FEATURES.PAGE_NAME);
            menuLabel = rs.getString(DatabaseStructs.LK_FEATURES.LABEL);
            menuIcon = rs.getString(DatabaseStructs.LK_FEATURES.ICON);

            menu = menus.get(type);
            if (pageName != null && !pageName.isEmpty() && pageName.equals("PARENT_MENU")) {
                menu.setLabel(menuLabel);
                menu.setIcon(menuIcon);
            } else {
                itemRoute = rs.getString(DatabaseStructs.LK_FEATURES.URL);
                if (menu != null) {
                    menu.getItems().add(new MenuItem(menuLabel, itemRoute, menuIcon));
                }
            }

            previousProfileId = currentProfileId;
        }
        if (menus.values() != null) {
            profileMenusMap.put(previousProfileId, new ArrayList<>(menus.values()));
        }
        return profileMenusMap;
    }

}
