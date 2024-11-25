package com.asset.ccat.user_management.database.extractors;

import com.asset.ccat.user_management.defines.DatabaseStructs;
import com.asset.ccat.user_management.models.users.LkProfileLimit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
public class ProfileLimitsExtractor implements ResultSetExtractor<HashMap<Integer, LkProfileLimit>> {

    @Override
    public HashMap<Integer, LkProfileLimit> extractData(ResultSet resultSet) throws SQLException, DataAccessException {

        HashMap<Integer, LkProfileLimit> profilesMap = new HashMap();
        while (resultSet.next()) {
            Integer limitId = resultSet.getInt(DatabaseStructs.ADM_PROFILE_MONETARY_LIMIT.LIMIT_ID);
            if (profilesMap.get(limitId) == null) {
                LkProfileLimit profileLimit = new LkProfileLimit();
                profileLimit.setLimitId(resultSet.getInt(DatabaseStructs.ADM_PROFILE_MONETARY_LIMIT.LIMIT_ID));
                profileLimit.setValue(resultSet.getFloat(DatabaseStructs.ADM_PROFILE_MONETARY_LIMIT.VALUE));
                profileLimit.setLimitName(resultSet.getString(DatabaseStructs.LK_MONETARY_LIMITS.LIMIT_NAME));
                if (!limitId.equals(0)) {
                    profilesMap.put(limitId, profileLimit);
                }

            }
        }

        return profilesMap;
    }

}
