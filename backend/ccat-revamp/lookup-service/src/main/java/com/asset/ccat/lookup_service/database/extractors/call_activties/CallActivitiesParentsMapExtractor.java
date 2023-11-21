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
import java.util.List;

@Component
public class CallActivitiesParentsMapExtractor implements ResultSetExtractor<HashMap<String, List<ReasonActivityModel>>> {

    @Override
    public HashMap<String, List<ReasonActivityModel>> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        HashMap<String, List<ReasonActivityModel>> activitiesMap = new HashMap<>();
        String key;
        String type;
        Integer parentId;

        while (resultSet.next()) {

            type = resultSet.getString(DatabaseStructs.ADM_REASON_ACTIVITY.ACTIVITY_TYPE);
            parentId = resultSet.getInt(DatabaseStructs.ADM_REASON_ACTIVITY.PARENT_ID);
            key = type + "-" + parentId;

            ReasonActivityModel activityModel = new ReasonActivityModel();
            activityModel.setActivityId(resultSet.getInt(DatabaseStructs.ADM_REASON_ACTIVITY.ACTIVITY_ID));
            activityModel.setActivityType(ActivityType.valueOf(type));
            activityModel.setParentId(parentId);
            activityModel.setActivityName(resultSet.getString(DatabaseStructs.ADM_REASON_ACTIVITY.ACTIVITY_NAME));

            activitiesMap.putIfAbsent(key, new ArrayList<ReasonActivityModel>());
            List<ReasonActivityModel> activitiesList = activitiesMap.get(key);
            activitiesList.add(activityModel);
        }
        return activitiesMap;
    }
}
