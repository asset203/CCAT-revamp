package com.asset.ccat.dynamic_page.database.row_mapper;

import com.asset.ccat.dynamic_page.defines.DatabaseStructs;
import com.asset.ccat.dynamic_page.models.dynamic_page.DynamicPageModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author assem.hassan
 */
@Component
public class DynamicPagesRowMapper implements RowMapper<DynamicPageModel> {

    @Override
    public DynamicPageModel mapRow(ResultSet rs, int rowNum) throws SQLException {

        DynamicPageModel dynamicPage = new DynamicPageModel();
        dynamicPage.setId(rs.getInt(DatabaseStructs.DYN_PAGES.ID));
        dynamicPage.setPageName(rs.getString(DatabaseStructs.DYN_PAGES.PAGE_NAME));
        dynamicPage.setPrivilegeName(rs.getString(DatabaseStructs.DYN_PAGES.PRIVILEGE_NAME));
        dynamicPage.setPrivilegeId(rs.getInt(DatabaseStructs.DYN_PAGES.PRIVILEGE_ID));
        dynamicPage.setRequireSubscriber((rs.getInt(DatabaseStructs.DYN_PAGES.REQUIRE_SUBSCRIBER) == 1));
        return dynamicPage;
    }
}
