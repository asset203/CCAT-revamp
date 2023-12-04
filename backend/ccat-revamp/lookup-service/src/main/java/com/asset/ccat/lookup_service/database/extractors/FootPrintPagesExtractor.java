package com.asset.ccat.lookup_service.database.extractors;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.models.FootPrintActionModel;
import com.asset.ccat.lookup_service.models.FootPrintPageModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author Assem.Hassan
 */
@Component
public class FootPrintPagesExtractor implements ResultSetExtractor<HashMap<String, FootPrintPageModel>> {

    @Override
    public HashMap<String, FootPrintPageModel> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        HashMap<String, FootPrintPageModel> footprintPagesMap = new HashMap<>();
        String controllerName;

        while (resultSet.next()) {
            controllerName = resultSet.getString(DatabaseStructs.LK_FOOTPRINT_PAGES.CONTROLLER_NAME);
            if (Objects.isNull(footprintPagesMap.get(controllerName))) {
                FootPrintPageModel FootPrintPageModel = new FootPrintPageModel();
                FootPrintPageModel.setPageId(resultSet.getInt(DatabaseStructs.LK_FOOTPRINT_PAGES.PAGE_ID));
                FootPrintPageModel.setPageName(resultSet.getString(DatabaseStructs.LK_FOOTPRINT_PAGES.PAGE_NAME));
                FootPrintPageModel.setControllerName(controllerName);
                FootPrintPageModel.setFootprintPageInfoMap(new HashMap<>());
                footprintPagesMap.put(controllerName, FootPrintPageModel);
            }
            FootPrintActionModel footPrintActionModel = new FootPrintActionModel();
            footPrintActionModel.setPageId(resultSet.getInt(DatabaseStructs.LK_FOOTPRINT_PAGE_INFO.PAGE_ID));
            footPrintActionModel.setMethodName(resultSet.getString(DatabaseStructs.LK_FOOTPRINT_PAGE_INFO.METHOD_NAME));
            footPrintActionModel.setActionName(resultSet.getString(DatabaseStructs.LK_FOOTPRINT_PAGE_INFO.ACTION_NAME));
            footPrintActionModel.setActionType(resultSet.getString(DatabaseStructs.LK_FOOTPRINT_PAGE_INFO.ACTION_TYPE));
            footprintPagesMap.get(controllerName)
                    .getFootprintPageInfoMap()
                    .put(footPrintActionModel.getMethodName(), footPrintActionModel);
        }

        return footprintPagesMap;
    }
}
