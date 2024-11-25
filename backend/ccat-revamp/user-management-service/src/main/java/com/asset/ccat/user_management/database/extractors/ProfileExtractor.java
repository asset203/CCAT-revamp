package com.asset.ccat.user_management.database.extractors;

import com.asset.ccat.user_management.configurations.constants.LkFeatureType;
import com.asset.ccat.user_management.defines.DatabaseStructs;
import com.asset.ccat.user_management.models.shared.LkFeatureModel;
import com.asset.ccat.user_management.models.users.ProfileModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

/**
 * @author marwa.elshawarby
 */
@Component
public class ProfileExtractor implements ResultSetExtractor<ProfileModel> {

    @Override
    public ProfileModel extractData(ResultSet resultSet) throws SQLException, DataAccessException {

        ProfileModel profile = null;
        LkFeatureModel feature;
        List<LkFeatureModel> sysFeatures = null;
        List<LkFeatureModel> ccFeatures = null;
        List<LkFeatureModel> dssReportsFeatures = null;

        while (resultSet.next()) {

            if (profile == null) {
                profile = new ProfileModel();
                sysFeatures = new ArrayList<>();
                ccFeatures = new ArrayList<>();
                dssReportsFeatures = new ArrayList<>();

                profile.setProfileId(resultSet.getInt(DatabaseStructs.ADM_PROFILES.PROFILE_ID));
                profile.setProfileName(resultSet.getString(DatabaseStructs.ADM_PROFILES.PROFILE_NAME));
                profile.setSessionLimit(resultSet.getInt(DatabaseStructs.ADM_PROFILES.SESSION_LIMIT));

                profile.setCcFeatures(ccFeatures);
                profile.setSysFeatures(sysFeatures);
                profile.setDssReportsFeatures(dssReportsFeatures);
            }

            Integer featureId = resultSet.getInt(DatabaseStructs.LK_FEATURES.ID);

            if (!featureId.equals(0)) {
                feature = new LkFeatureModel();
                feature.setId(featureId);
                feature.setName(resultSet.getString(DatabaseStructs.LK_FEATURES.NAME));
                feature.setType(resultSet.getInt(DatabaseStructs.LK_FEATURES.TYPE));
                feature.setPageName(resultSet.getString(DatabaseStructs.LK_FEATURES.PAGE_NAME));
                feature.setOrder(resultSet.getInt(DatabaseStructs.ADM_PROFILE_FEATURES.ORDERING));

                if (feature.getType().equals(LkFeatureType.CUSTOMER_CARE.id)) {
                    ccFeatures.add(feature);
                } else if (feature.getType().equals(LkFeatureType.ADMIN.id)) {
                    sysFeatures.add(feature);
                } else {
                    dssReportsFeatures.add(feature);
                }
            }
        }
        return profile;
    }
}
