package com.asset.ccat.lookup_service.database.extractors.call_activties;

import com.asset.ccat.lookup_service.constants.ActivityType;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.models.ReasonActivityModel;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@Component
public class ReasonActivityIdMapExtractor implements ResultSetExtractor<HashMap<Integer, ReasonActivityModel>> {
//
//
//    private String type;
//
//    public ReasonActivityIdMapExtractor(String type) {
//        this.type = type;
//    }

    @Override
    public HashMap<Integer, ReasonActivityModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
//        HashMap<Integer, ReasonActivityModel> reasonActivityMap = new HashMap<>();
        HashMap<Integer, ReasonActivityModel> allReasons = new HashMap<>();
        while (rs.next()) {
            int activityId = rs.getInt(DatabaseStructs.ADM_REASON_ACTIVITY.ACTIVITY_ID);
            String activityType = rs.getString(DatabaseStructs.ADM_REASON_ACTIVITY.ACTIVITY_TYPE);
            int parentId = rs.getInt(DatabaseStructs.ADM_REASON_ACTIVITY.PARENT_ID);
            String activityName = rs.getString(DatabaseStructs.ADM_REASON_ACTIVITY.ACTIVITY_NAME);
            ReasonActivityModel reason = new ReasonActivityModel(activityId, ActivityType.valueOf(activityType), parentId, activityName, new ArrayList<>());

            if (parentId != 0) {
                ArrayList<Integer> parent = reason.getParents();
                parent.add(parentId);
                reason.setParents(parent);
            }

            allReasons.put(activityId, reason);
        }
        allReasons.forEach((key, value) -> {
            if (value.getParentId() != null && value.getParentId() != 0) {
                ArrayList<Integer> allParents = value.getParents();
                if (allReasons.get(value.getParentId()) != null) {
                    allParents.addAll(allReasons.get(value.getParentId()).getParents());
                    value.setParents(allParents);
                }

            }
//            if (value.getActivityType().name == type)
//                reasonActivityMap.put(value.getActivityId(), value);
//            if (type == "none")
//                reasonActivityMap.put(value.getActivityId(), value);

//            CCATLogger.DEBUG_LOGGER.debug(key + ": " + value.getParents());
        });

        return allReasons;


    }

}
