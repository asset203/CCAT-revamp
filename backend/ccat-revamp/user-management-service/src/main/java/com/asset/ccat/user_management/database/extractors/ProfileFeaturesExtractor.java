package com.asset.ccat.user_management.database.extractors;

import com.asset.ccat.user_management.defines.DatabaseStructs;
import com.asset.ccat.user_management.models.requests.profile.ProfileFeaturesModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class ProfileFeaturesExtractor  implements ResultSetExtractor<List<ProfileFeaturesModel>> {
    @Override
    public List<ProfileFeaturesModel> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
       List<ProfileFeaturesModel> profileFeaturesModelList = new ArrayList<>();
       HashMap<Integer,ProfileFeaturesModel > map = new HashMap<>();
       Integer currentProfileId = -1;
       String currentProfileName = "";
       List<Integer> currentFeatures =  new ArrayList<>();
       while (resultSet.next()){
           Integer profileId = resultSet.getInt(DatabaseStructs.ADM_PROFILES.PROFILE_ID);
           Integer featureId = resultSet.getInt(DatabaseStructs.ADM_PROFILE_FEATURES.FEATURE_ID);
           String profileName = resultSet.getString(DatabaseStructs.ADM_PROFILES.PROFILE_NAME);
           if(!featureId.equals(0)){
               if(currentProfileId.equals(-1)){
                   currentProfileName = profileName;
                   currentProfileId = profileId;
                   currentFeatures.add(featureId);
               }
               else if(profileId.equals(currentProfileId)){
                   currentFeatures.add(featureId);
               }else {
                   if(!currentFeatures.isEmpty()){
                       profileFeaturesModelList.add(new ProfileFeaturesModel(currentProfileId,currentProfileName,currentFeatures));
                   }
                   currentFeatures = new ArrayList<>();
                   currentFeatures.add(featureId);
                   currentProfileId = profileId;
                   currentProfileName =  profileName;

               }
           }

       }
       if(!currentFeatures.isEmpty()){
            profileFeaturesModelList.add(new ProfileFeaturesModel(currentProfileId,currentProfileName,currentFeatures));
        }
        return profileFeaturesModelList;
    }
}
