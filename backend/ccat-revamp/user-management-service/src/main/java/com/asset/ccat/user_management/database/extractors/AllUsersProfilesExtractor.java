package com.asset.ccat.user_management.database.extractors;

import com.asset.ccat.user_management.defines.DatabaseStructs;
import com.asset.ccat.user_management.logger.CCATLogger;
import com.asset.ccat.user_management.models.dtoWrappers.ExtractAllUsersProfilesWrapper;
import com.asset.ccat.user_management.models.shared.UsersProfilesModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

@Component
public class AllUsersProfilesExtractor implements ResultSetExtractor<ExtractAllUsersProfilesWrapper> {
    @Override
    public ExtractAllUsersProfilesWrapper extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<UsersProfilesModel> usersProfilesModel = new ArrayList<>();
        HashSet<String> profilesHashSet = new HashSet<>();
        while (resultSet.next()) {
            Integer userId = resultSet.getInt(DatabaseStructs.ADM_USERS.USER_ID);
            String userName = resultSet.getString(DatabaseStructs.ADM_USERS.NT_ACCOUNT);
            Integer profileId = resultSet.getInt(DatabaseStructs.ADM_USERS.PROFILE_ID);
            String profileName = resultSet.getString(DatabaseStructs.ADM_PROFILES.PROFILE_NAME);

            usersProfilesModel.add(new UsersProfilesModel(userName, userId, profileName, profileId));
            profilesHashSet.add(profileName);
        }
        CCATLogger.DEBUG_LOGGER.debug("#Retrieved users = {} || #RetrievedProfiles = {}", usersProfilesModel.size(), profilesHashSet.size());
        return new ExtractAllUsersProfilesWrapper(usersProfilesModel, new LinkedList<>(profilesHashSet));
    }
}