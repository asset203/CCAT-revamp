package com.asset.ccat.lookup_service.database.extractors.call_activties;

import com.asset.ccat.lookup_service.constants.ActivityType;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.models.ReasonActivityModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@Component
public class CallActivtiesMapExtractor implements ResultSetExtractor<HashMap<String,ReasonActivityModel>> {
    @Override
    public HashMap<String, ReasonActivityModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
        HashMap<String, ReasonActivityModel> reasonActivityMap = new HashMap<>();
        while (rs.next()) {
            int activityId = rs.getInt(DatabaseStructs.ADM_REASON_ACTIVITY.ACTIVITY_ID);
            String activityType = rs.getString(DatabaseStructs.ADM_REASON_ACTIVITY.ACTIVITY_TYPE);
            int parentId = rs.getInt(DatabaseStructs.ADM_REASON_ACTIVITY.PARENT_ID);
            String activityName = rs.getString(DatabaseStructs.ADM_REASON_ACTIVITY.ACTIVITY_NAME);
            ReasonActivityModel reason = new ReasonActivityModel(activityId, ActivityType.getType(activityType), parentId, activityName);

            String activityKey = activityName + "-" + activityType + "-" + parentId;

            reasonActivityMap.put(activityKey, reason);
        }

        return reasonActivityMap;
    }
}
