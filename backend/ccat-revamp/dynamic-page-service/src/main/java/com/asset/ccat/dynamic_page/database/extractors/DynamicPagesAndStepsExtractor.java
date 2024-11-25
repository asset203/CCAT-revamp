package com.asset.ccat.dynamic_page.database.extractors;

import com.asset.ccat.dynamic_page.defines.DatabaseStructs;
import com.asset.ccat.dynamic_page.models.dynamic_page.DynamicPageModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.StepModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

@Component
public class DynamicPagesAndStepsExtractor implements ResultSetExtractor<HashMap<Integer, DynamicPageModel>> {
    @Override
    public HashMap<Integer, DynamicPageModel> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        HashMap<Integer, DynamicPageModel> pages = new HashMap<>();
        Integer pageId;
        Integer privilegeId;
        Integer stepId;
        while (resultSet.next()) {
            pageId = resultSet.getInt(DatabaseStructs.DYN_PAGES.ID);
            privilegeId = resultSet.getInt(DatabaseStructs.DYN_PAGES.PRIVILEGE_ID);
            if (pages.get(privilegeId) == null) {
                DynamicPageModel page = new DynamicPageModel();
                page.setId(pageId);
                page.setPageName(resultSet.getString(DatabaseStructs.DYN_PAGES.PAGE_NAME));
                page.setPrivilegeId(resultSet.getInt(DatabaseStructs.DYN_PAGES.PRIVILEGE_ID));
                page.setPrivilegeName(resultSet.getString(DatabaseStructs.DYN_PAGES.PRIVILEGE_NAME));
                page.setRequireSubscriber(resultSet.getBoolean(DatabaseStructs.DYN_PAGES.REQUIRE_SUBSCRIBER));
                page.setSteps(new ArrayList<>());
                pages.put(privilegeId, page);
            }

            stepId = resultSet.getInt(DatabaseStructs.DYN_PAGES_STEPS.ID);
            if (Objects.nonNull(stepId) && !stepId.equals(0)) {
                StepModel step = new StepModel();
                step.setId(resultSet.getInt(DatabaseStructs.DYN_PAGES_STEPS.ID));
                step.setPageId(pageId);
                step.setStepName(resultSet.getString(DatabaseStructs.DYN_PAGES_STEPS.STEP_NAME));
                step.setStepType(resultSet.getInt(DatabaseStructs.DYN_PAGES_STEPS.STEP_TYPE));
                step.setStepOrder(resultSet.getInt(DatabaseStructs.DYN_PAGES_STEPS.STEP_ORDER));
                step.setIsHidden(resultSet.getBoolean(DatabaseStructs.DYN_PAGES_STEPS.IS_HIDDEN));
                pages.get(privilegeId).getSteps().add(step);
            }
        }
        return pages;
    }
}
