package com.asset.ccat.user_management.database.extractors;

import com.asset.ccat.user_management.defines.DatabaseStructs;
import com.asset.ccat.user_management.models.shared.LkFeatureModel;
import com.asset.ccat.user_management.models.users.UserProfileModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
public class ProfilesExtractor implements ResultSetExtractor<HashMap<Integer, UserProfileModel>> {

    @Override
    public HashMap<Integer, UserProfileModel> extractData(ResultSet resultSet) throws SQLException, DataAccessException {

        HashMap<Integer, UserProfileModel> profilesMap = new HashMap();
        while (resultSet.next()) {
            Integer profileId = resultSet.getInt(DatabaseStructs.ADM_PROFILES.PROFILE_ID);
            Integer featureId = resultSet.getInt(DatabaseStructs.LK_FEATURES.ID);
            if (profilesMap.get(profileId) == null) {

                UserProfileModel profileModel = new UserProfileModel();
                profileModel.setProfileId(resultSet.getInt(DatabaseStructs.ADM_PROFILES.PROFILE_ID));
                profileModel.setIsAdjustmentsLimited(resultSet.getInt(DatabaseStructs.ADM_PROFILES.ADJUSTMENTS_LIMITED));
                profileModel.setProfileName(resultSet.getString(DatabaseStructs.ADM_PROFILES.PROFILE_NAME));
                profileModel.setSessionLimit(resultSet.getInt(DatabaseStructs.ADM_PROFILES.SESSION_LIMIT));
                profileModel.setProfileSOB(resultSet.getString(DatabaseStructs.ADM_PROFILE_SOB.SERVICE_OFFERING_BITS));

                ArrayList<LkFeatureModel> featureList = new ArrayList<>();
                ArrayList<String> authorizedUrls = new ArrayList<>();

                if (!featureId.equals(0)) {
                    LkFeatureModel lkFeatureModel = new LkFeatureModel();
                    lkFeatureModel.setId(resultSet.getInt(DatabaseStructs.LK_FEATURES.ID));
                    lkFeatureModel.setName(resultSet.getString(DatabaseStructs.LK_FEATURES.NAME));
                    lkFeatureModel.setType(resultSet.getInt(DatabaseStructs.LK_FEATURES.TYPE));
                    lkFeatureModel.setPageName(resultSet.getString(DatabaseStructs.LK_FEATURES.PAGE_NAME));

                    String url = resultSet.getString(DatabaseStructs.LK_FEATURES.URL);
                    if (url != null) {
                        authorizedUrls.add(url);
                    }

                    featureList.add(lkFeatureModel);
                }

                profileModel.setAuthorizedUrls(authorizedUrls);
                profileModel.setFeatures(featureList);
                profilesMap.put(profileId, profileModel);

            } else if (!featureId.equals(0)) {
                UserProfileModel profileModel = profilesMap.get(profileId);

                LkFeatureModel lkFeatureModel = new LkFeatureModel();
                lkFeatureModel.setId(resultSet.getInt(DatabaseStructs.LK_FEATURES.ID));
                lkFeatureModel.setName(resultSet.getString(DatabaseStructs.LK_FEATURES.NAME));
                lkFeatureModel.setType(resultSet.getInt(DatabaseStructs.LK_FEATURES.TYPE));
                lkFeatureModel.setPageName(resultSet.getString(DatabaseStructs.LK_FEATURES.PAGE_NAME));
                String url = resultSet.getString(DatabaseStructs.LK_FEATURES.URL);
                if (url != null) {
                    profileModel.getAuthorizedUrls().add(url);
                }
                profileModel.getFeatures().add(lkFeatureModel);
            }
        }

        return profilesMap;
    }

}
